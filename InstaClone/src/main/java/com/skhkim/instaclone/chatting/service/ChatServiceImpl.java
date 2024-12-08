package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.event.ChatRoomSessionManager;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import com.skhkim.instaclone.chatting.request.InviteRequest;
import com.skhkim.instaclone.chatting.request.MessageRequest;
import com.skhkim.instaclone.context.LoginContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomService roomService;
    private final ChatMessageService messageService;
    private final ChatUserService userService;

    private final ChatUserRepository userRepository;
    private final ChatRoomSessionManager sessionManager;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public void sendMessage(MessageRequest request){

        String loginEmail = LoginContext.getUserInfo().getUserEmail();
        ChatUser chatUser = userRepository.selectChatUser(request.getRoomId(), loginEmail);

        Long userNum = chatUser.getChatRoom().getUserNum();
        Long readStatus = userNum - sessionManager.getRoomJoinNum(request.getRoomId().toString());

        request.getChatMessageDTO().setSenderEmail(loginEmail);
        request.getChatMessageDTO().setReadStatus(readStatus);
        request.getChatMessageDTO().setRoomId(request.getRoomId());

        messageService.register(request.getChatMessageDTO());
        roomService.updateLastChatTime(request.getChatMessageDTO());
        redisTemplate.convertAndSend("/chat/"+ request.getRoomId(), request.getChatMessageDTO());
    }

    @Override
    @Transactional
    public void inviteChatUsers(InviteRequest request){
        userService.insertChatUser(request.getUserEmails(), request.getRoomId());
        roomService.updateUserNum(request.getRoomId(), request.getAddNum());

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .roomId(request.getRoomId())
                .inviteNames(request.getUserNames().toString())
                .inviterName(LoginContext.getUserInfo().getUserName())
                .regDate(LocalDateTime.now())
                .build();
        messageService.register(chatMessageDTO);
        redisTemplate.convertAndSend("/invite/"+ request.getRoomId(), request);
    }
}
