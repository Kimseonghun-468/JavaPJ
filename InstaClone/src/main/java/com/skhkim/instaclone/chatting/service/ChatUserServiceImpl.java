package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatRoomDTO;
import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import com.skhkim.instaclone.chatting.response.ChatRoomResponse;

import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.response.UserInfoResponse;
import com.skhkim.instaclone.service.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatUserServiceImpl implements ChatUserService {

    private final ChatUserRepository chatUserRepository;
    @Override
    public void register(String userEmail, Long roomId){
        ChatUser chatUser = ChatUser.builder()
                .chatRoom(ChatRoom.builder().roomId(roomId).build())
                .member(ClubMember.builder().email(userEmail).build())
                .disConnect(LocalDateTime.now())
                .build();
        chatUserRepository.save(chatUser);
    }
    @Override
    public void updateDisConnect(Long roomId, String loginEmail){
        ChatUser chatUser = chatUserRepository.getChatUsersByRoomIdAndEmail(roomId, loginEmail);
        chatUser.setDisConnect(LocalDateTime.now());
        chatUserRepository.save(chatUser);
    }

    @Override
    public List<Object[]> getEmailAndName(Long roomId){
        return chatUserRepository.getEmailAndNmaeByRoomId(roomId);
    }

    @Override
    public UserInfoResponse selectChatRoomUsers(Long roomId){
        List<ClubMember> result = chatUserRepository.selectChatRoomUsers(roomId);
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, false);
    }

    @Override
    public ChatUserDTO selectChatUser(Long roomId, String loginName){
        ChatUser result = chatUserRepository.selectChatUser(roomId, loginName);
        ChatUserDTO chatUserDTO = EntityMapper.entityToDTO(result);

        return chatUserDTO;
    }

    @Override
    public List<UserInfoDTO> selectChatUserList(Long roomId, List<String> userNameList){
        List<ClubMember> result = chatUserRepository.selectChatUserList(roomId, userNameList);
        List<UserInfoDTO> userInfoDTOS = result.stream().map(user -> EntityMapper.entityToDTO(user)).toList();

        return userInfoDTOS;
    }

    @Override
    public ChatRoomResponse
    getProfileAndUseByLoginNamePage(UserInfoPageRequest userInfoPageRequest, String loginEmail){
        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<ChatRoom> result = chatUserRepository.getTest(pageable ,loginEmail);
        List<ChatRoomDTO> chatRoomDTOS = result.stream().map(chatRoom -> EntityMapper.entityToDTO(chatRoom)).toList();

        return new ChatRoomResponse(chatRoomDTOS, result.hasNext());
    }
    @Override
    public void insertChatUser(List<String> userEmails, Long roomId){
        userEmails.forEach(userEmail -> {
            ChatUser chatUser = ChatUser.builder()
                    .member(ClubMember.builder().email(userEmail).build())
                    .chatRoom(ChatRoom.builder().roomId(roomId).build())
                    .disConnect(LocalDateTime.now())
                    .build();
            chatUserRepository.save(chatUser);
        });
    }
}
