package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT cm, cm.cid FROM ChatMessage cm WHERE cm.roomId =:roomID AND :disConnectTime >= cm.regDate ORDER BY cm.cid DESC")
    Page<Object[]> getChatMessageByRoomIdAndTimeBefore(Pageable pageable, Long roomID, LocalDateTime disConnectTime);

    @Query("SELECT cm, cm.cid FROM ChatMessage cm WHERE cm.roomId =:roomID AND :disConnectTime < cm.regDate ORDER BY cm.cid")
    Page<Object[]> getChatMessageByRoomIdAndTimeAfter(Pageable pageable, Long roomID, LocalDateTime disConnectTime);


    @Modifying
    @Transactional
    @Query("UPDATE ChatMessage cm SET cm.readStatus = cm.readStatus -1 WHERE cm.regDate >= :disConnect AND cm.roomId =:roomId AND cm.senderEmail != :userEmail")
    int updateByRoomIdAndSenderEmailAndTime(Long roomId, String userEmail, LocalDateTime disConnect);

    @Query("SELECT count(cm) FROM ChatMessage cm WHERE cm.roomId =:roomID AND cm.senderEmail != :loginEmail AND cm.regDate >= :disConnectTime")
    Long getNotReadNum(Long roomID ,String loginEmail, LocalDateTime disConnectTime);
}
