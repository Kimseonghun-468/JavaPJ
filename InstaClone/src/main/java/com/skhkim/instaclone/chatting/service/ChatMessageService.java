package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.request.MessagePageRequest;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;

public interface ChatMessageService {

    Long getNotReadNum(String loginEmail, Long roomId);
    void register(ChatMessageDTO chatMessageDTO, Long roomID);

    void updateChatMessagesReadStatus(Long roomID, String userEmail);
    ChatMessageResponse selectChatMessageUp(MessagePageRequest messagePageRequest, Long roomId, String loginEmail);

    ChatMessageResponse selectChatMessageDown(MessagePageRequest messagePageRequest, Long roomId, String loginEmail);



}
