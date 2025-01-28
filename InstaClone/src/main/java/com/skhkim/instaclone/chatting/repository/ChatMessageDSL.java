package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatMessageDSL {
    Slice<ChatMessage> selectChatMessages(Pageable pageable, Long roomId, Long lastCid, Long joinCid, String loginEmail, String dType);

}
