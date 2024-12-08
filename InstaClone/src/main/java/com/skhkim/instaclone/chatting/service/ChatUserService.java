package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.response.ChatRoomResponse;
import com.skhkim.instaclone.request.UserInfoPageRequest;

import java.util.List;


public interface ChatUserService {

    void register(String userEmail, Long roomId);
    void updateDisConnect(Long roomId);

    List<Object[]> getEmailAndName(Long roomId);

    List<ChatUserDTO> selectChatRoomUsers(Long roomId);

    ChatUserDTO selectChatUser(Long roomId);

    ChatRoomResponse getProfileAndUseByLoginNamePage(UserInfoPageRequest userInfoPageRequest);

    void insertChatUser(List<String> userEmails, Long roomId);

}

