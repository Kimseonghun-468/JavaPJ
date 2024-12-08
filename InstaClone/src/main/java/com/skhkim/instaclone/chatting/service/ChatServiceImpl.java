package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.event.ChatRoomSessionManager;
import com.skhkim.instaclone.chatting.repository.ChatRoomRepository;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import com.skhkim.instaclone.chatting.request.InviteRequest;
import com.skhkim.instaclone.chatting.request.MessageRequest;
import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.service.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomService roomService;
    private final ChatMessageService messageService;
    private final ChatUserService userService;


    private final ChatUserRepository userRepository;
    private final ChatRoomRepository roomRepository;
    private final ClubMemberRepository memberRepository;
    private final ChatRoomSessionManager sessionManager;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public void sendMessage(MessageRequest request){

        String loginEmail = LoginContext.getClubMember().getEmail();
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
                .inviterName(LoginContext.getClubMember().getName())
                .regDate(LocalDateTime.now())
                .build();
        messageService.register(chatMessageDTO);
        redisTemplate.convertAndSend("/invite/"+ request.getRoomId(), request);
    }

    @Override
    @Transactional
    public List<ChatUserDTO> joinChatRoom(Long roomId){
        List<ChatUser> result = userRepository.selectChatUsers(roomId);
        messageService.updateReadStatus(roomId);
        // 여기 redis join 처리 해야함.
        return result.stream().map(EntityMapper::entityToDTO).toList();
    }

    @Override
    @Transactional
    public Long createChatRoom(String userName){
        Optional<Long> roomId = userRepository.checkChatRoom(LoginContext.getClubMember().getName(), userName);

        if(roomId.isPresent()){
            this.joinChatRoom(roomId.get());
            return roomId.get();
        }
        else{
            ChatRoom chatRoom = ChatRoom.builder()
                    .userNum(2L)
                    .build();
            roomRepository.save(chatRoom);
            userService.register(LoginContext.getClubMember().getEmail(), chatRoom.getRoomId());
            userService.register(memberRepository.findByName(userName).getEmail(), chatRoom.getRoomId());
            this.joinChatRoom(chatRoom.getRoomId());
            return chatRoom.getRoomId();
        }
    }
}

