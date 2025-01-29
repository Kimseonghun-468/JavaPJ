package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;
import com.skhkim.instaclone.request.MessagePageRequest;

public interface ChatMessageService {

    Long getNotReadNum(Long roomId);
    Long register(ChatMessageDTO chatMessageDTO);

    void updateReadStatus(Long roomID);

    ChatMessageResponse selectChatMessages(MessagePageRequest request);






}
