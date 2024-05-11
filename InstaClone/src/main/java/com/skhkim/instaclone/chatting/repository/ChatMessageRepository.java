package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<List<ChatMessage>> findByChatRoomId(String roomID);

    @Query("SELECT cm, cm.cid FROM ChatMessage cm WHERE cm.chatRoom.id =:roomID ORDER BY cm.cid DESC")
    Page<Object[]> findByChatRoomId(Pageable pageable, @Param("roomID") String roomID);

    // 시간 이후의 것들 전부 조회
    @Modifying
    @Transactional
    @Query("UPDATE ChatMessage cm SET cm.readStatus = TRUE WHERE cm.regDate >= :disConnectTime AND cm.chatRoom.id = :roomID AND cm.name = :userName")
    int updateByChatRoomIdAndDisConnectTime(String roomID, String userName, LocalDateTime disConnectTime);


}
