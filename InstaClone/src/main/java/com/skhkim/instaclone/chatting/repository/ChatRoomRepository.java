package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.roomId = :roomId")
    ChatRoom selectChatRoom(Long roomId);

    @Modifying
    @Transactional
    @Query("UPDATE ChatRoom cr SET cr.userNum = cr.userNum + :addNum WHERE cr.roomId = :roomId")
    void updateUserNumByRoomId(Long roomId, Integer addNum);
}
