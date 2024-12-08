package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;

public interface ChatRoomService {
    void updateLastChatTime(ChatMessageDTO chatMessageDTO);
    void updateUserNum(Long roomId, Integer addNum);
}
