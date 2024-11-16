package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;

public interface ChatMessageService {

    Long getNotReadNum(String loginEmail, Long roomId);
    void register(ChatMessageDTO chatMessageDTO, Long roomID);

    void updateChatMessagesReadStatus(Long roomID, String userEmail);
    ChatMessageResponse selectChatMessageUp(PageRequestDTO pageRequestDTO, Long roomId, String loginEmail);

    ChatMessageResponse selectChatMessageDown(PageRequestDTO pageRequestDTO, Long roomId, String loginEmail);



}
