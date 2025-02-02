package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.repository.ChatRoomRepository;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import com.skhkim.instaclone.chatting.response.ChatRoomResponse;
import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.service.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatUserServiceImpl implements ChatUserService {

    private final ChatUserRepository chatUserRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ClubMemberRepository memberRepository;
    @Override
    public void register(Long userId, Long roomId){

        ChatUser chatUser = ChatUser.builder()
                .chatRoom(ChatRoom.builder().roomId(roomId).build())
                .member(ClubMember.builder().id(userId).build())
                .lastCid(0L)
                .joinCid(0L)
                .build();
        chatUserRepository.save(chatUser);
    }
    @Override
    public void updateDisConnectCid(Long roomId){
        Long userId = LoginContext.getClubMember().getUserId();
        ChatUser chatUser = chatUserRepository.select(roomId, userId);
        chatUser.setLastCid(chatUser.getChatRoom().getLastCid());
        chatUserRepository.save(chatUser);
    }

    @Override
    public ChatRoomResponse
    selectChatRooms(UserInfoPageRequest userInfoPageRequest){
        Long userId = LoginContext.getClubMember().getUserId();
        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<ChatRoom> result = chatRoomRepository.selectList(pageable, userId);
        List<ChatRoomDTO> chatRoomDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new ChatRoomResponse(chatRoomDTOS, result.hasNext());
    }
    @Override
    public void insertChatUser(List<String> userNames, Long roomId){

        ChatRoom chatRoom = chatRoomRepository.select(roomId);
        List<ClubMember> clubMembers = memberRepository.selectByNames(userNames);
        clubMembers.forEach(member -> {
            ChatUser chatUser = ChatUser.builder()
                    .member(member)
                    .chatRoom(ChatRoom.builder().roomId(roomId).build())
                    .lastCid(chatRoom.getLastCid())
                    .joinCid(chatRoom.getLastCid())
                    .build();
            chatUserRepository.save(chatUser);
        });
    }
}
