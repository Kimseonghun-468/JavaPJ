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
import com.skhkim.instaclone.chatting.response.ChatUserResponse;
import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.service.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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

        Long userId = LoginContext.getClubMember().getUserId();
        ChatUser chatUser = userRepository.select(request.getRoomId(), userId);

        Long userNum = chatUser.getChatRoom().getUserNum();
        Long readStatus = userNum - sessionManager.getRoomJoinNum(request.getRoomId().toString());

        request.getChatMessageDTO().setReadStatus(readStatus);
        request.getChatMessageDTO().setRoomId(request.getRoomId());
        Long cid = messageService.register(request.getChatMessageDTO());
        request.getChatMessageDTO().setCid(cid);
        roomService.updateLastCmId(request.getChatMessageDTO());
        redisTemplate.convertAndSend("/chat/"+ request.getRoomId(), request.getChatMessageDTO());
    }

    @Override
    public void inviteChatUsers(InviteRequest request){
        userService.insertChatUser(request.getUserNames(), request.getRoomId());
        roomService.updateUserNum(request.getRoomId(), request.getAddNum());

        // 여기서 names를 Id로 수정후 넣어줘야함.
        List<ClubMember> clubMembers = memberRepository.selectByNames(request.getUserNames());
        String userIds = clubMembers.stream()
                .map(member -> String.valueOf(member.getId()))
                .collect(Collectors.joining(", "));

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .roomId(request.getRoomId())
                .inviteNames(userIds)
                .inviterName(LoginContext.getClubMember().getName())
                .regDate(LocalDateTime.now())
                .build();

        messageService.register(chatMessageDTO);
        redisTemplate.convertAndSend("/invite/"+ request.getRoomId(), request);
    }

    @Override
    public List<ChatUserDTO> joinChatRoom(MessageRequest request){
        List<ChatUser> result = userRepository.selectList(request.getRoomId());
        messageService.updateReadStatus(request);

        ChatUserResponse chatUser = ChatUserResponse.builder()
                .roomId(request.getRoomId())
                .userName(LoginContext.getClubMember().getName())
                .build();
        redisTemplate.convertAndSend("/access/" +request.getRoomId(), chatUser);
        return result.stream().map(EntityMapper::entityToDTO).toList();
    }

    @Override
    public Long createChatRoom(String userName){
        Optional<Long> roomId = roomRepository.validate(LoginContext.getClubMember().getName(), userName);

        if(roomId.isPresent()){
            return roomId.get();
        }
        else{
            ChatRoom chatRoom = ChatRoom.builder()
                    .userNum(2L)
                    .lastCid(0L)
                    .lastChat("")
                    .build();
            roomRepository.save(chatRoom);
            userService.register(LoginContext.getClubMember().getUserId(), chatRoom.getRoomId());
            userService.register(memberRepository.findByName(userName).getId(), chatRoom.getRoomId());
            return chatRoom.getRoomId();
        }
    }
}

