package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.request.MessagePageRequest;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;

public interface ChatMessageService {

    Long getNotReadNum(Long roomId);
    void register(ChatMessageDTO chatMessageDTO, Long roomID);

    void updateChatMessagesReadStatus(Long roomID);
    ChatMessageResponse selectChatMessageUp(MessagePageRequest messagePageRequest, Long roomId);

    ChatMessageResponse selectChatMessageDown(MessagePageRequest messagePageRequest, Long roomId);



}
