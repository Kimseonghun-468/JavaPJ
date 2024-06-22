package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.roomId = :roomId")
    Optional<ChatRoom> getChatRoombyRoomId(Long roomId);

    @Query("SELECT cr.userNum FROM ChatRoom cr WHERE cr.roomId =:roomId")
    Long getUserNum(Long roomId);

    @Modifying
    @Transactional
    @Query("UPDATE ChatRoom cr SET cr.userNum = cr.userNum + :addNum WHERE cr.roomId = :roomId")
    void updateUserNumByRoomId(Long roomId, Long addNum);
}
