package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.request.MessagePageRequest;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;

public interface ChatMessageService {

    Long getNotReadNum(Long roomId);
    void register(ChatMessageDTO chatMessageDTO);

    void updateChatMessagesReadStatus(Long roomID);
    ChatMessageResponse selectChatMessageUp(MessagePageRequest messagePageRequest, Long roomId);

    ChatMessageResponse selectChatMessageDown(MessagePageRequest messagePageRequest, Long roomId);



}
