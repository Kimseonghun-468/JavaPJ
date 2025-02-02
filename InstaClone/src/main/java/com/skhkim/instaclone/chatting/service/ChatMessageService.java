package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.request.MessageRequest;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;
import com.skhkim.instaclone.request.MessagePageRequest;

public interface ChatMessageService {

    Long getNotReadNum(MessageRequest request);
    Long register(ChatMessageDTO chatMessageDTO);

    void updateReadStatus(MessageRequest request);

    ChatMessageResponse selectChatMessages(MessagePageRequest request);






}
