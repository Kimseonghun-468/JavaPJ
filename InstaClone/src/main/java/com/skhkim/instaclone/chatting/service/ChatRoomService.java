package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;

import java.util.Map;

public interface ChatRoomService {

    Map<String, Object> getORCreateChatRoomID(String loginEmail, String friendEmail);
    void updateLastChatTime(ChatMessageDTO chatMessageDTO);

    Long getUserNum(Long roomId);

    void updateUserNum(Long roomId, Integer addNum);
}
