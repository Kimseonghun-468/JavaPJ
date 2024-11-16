package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.response.ChatRoomResponse;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;

import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.response.UserInfoResponse;

import java.util.List;


public interface ChatUserService {

    void register(String userEmail, Long roomId);
    void updateDisConnect(Long roomId, String loginEmail);

    List<Object[]> getEmailAndName(Long roomId);

    UserInfoResponse selectChatRoomUsers(Long roomId);

    ChatUserDTO selectChatUser(Long roomId, String loginName);

    List<UserInfoDTO> selectChatUserList(Long roomId, List<String> invitNameList);

    ChatRoomResponse getProfileAndUseByLoginNamePage(ProfilePageRequestDTO profilePageRequestDTO, String loginEmail);

    void insertChatUser(List<String> userEmails, Long roomId);

}

