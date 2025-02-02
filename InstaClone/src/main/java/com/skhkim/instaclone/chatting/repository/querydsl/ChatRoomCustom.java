package com.skhkim.instaclone.chatting.repository.querydsl;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ChatRoomCustom {
    ChatRoom select(Long roomId);
    void updateUserNum(Long roomId, Long addNum);
    Slice<ChatRoom> selectList(Pageable pageable, Long userId);
    Optional<Long> validate(String loginName, String userName);
}
