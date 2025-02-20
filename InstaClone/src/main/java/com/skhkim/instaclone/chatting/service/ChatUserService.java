package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.response.ChatRoomResponse;
import com.skhkim.instaclone.request.UserInfoPageRequest;

import java.util.List;


public interface ChatUserService {

    void register(Long userId, Long roomId);
    void updateDisConnectCid(Long roomId);

    ChatRoomResponse selectChatRooms(UserInfoPageRequest userInfoPageRequest);

    void insertChatUser(List<String> userNames, Long roomId);

}

