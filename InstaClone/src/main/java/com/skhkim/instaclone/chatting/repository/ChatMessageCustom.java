package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.request.MessageRequest;
import com.skhkim.instaclone.request.MessagePageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ChatMessageCustom {
    Slice<ChatMessage> selectChatMessagesPage(MessagePageRequest request);

    List<ChatMessage> selectChatMessages(MessageRequest request);
    Long getNotReadNum(MessageRequest request);

    void updateReadNum(MessageRequest request);

}
