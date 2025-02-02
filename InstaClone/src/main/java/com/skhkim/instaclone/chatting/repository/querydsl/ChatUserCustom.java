package com.skhkim.instaclone.chatting.repository.querydsl;

import com.skhkim.instaclone.chatting.entity.ChatUser;

import java.util.List;

public interface ChatUserCustom {

    ChatUser select(Long roomId, Long userId);

    List<ChatUser> selectList(Long roomId);
}
