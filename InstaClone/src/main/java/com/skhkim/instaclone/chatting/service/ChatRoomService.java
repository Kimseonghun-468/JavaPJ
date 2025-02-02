package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;

public interface ChatRoomService {
    void updateLastCmId(ChatMessageDTO chatMessageDTO);
    void updateUserNum(Long roomId, Long addNum);
}
