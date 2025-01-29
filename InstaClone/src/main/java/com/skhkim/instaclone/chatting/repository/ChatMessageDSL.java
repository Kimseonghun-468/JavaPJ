package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.request.MessagePageRequest;
import org.springframework.data.domain.Slice;

public interface ChatMessageDSL {
    Slice<ChatMessage> selectChatMessages(MessagePageRequest request);

}
