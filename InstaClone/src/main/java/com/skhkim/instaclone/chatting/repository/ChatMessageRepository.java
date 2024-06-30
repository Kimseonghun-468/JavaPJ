package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;


public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    @Query(value = "{ 'roomId' : ?0, 'regDate' : { $lte : ?1 } }", sort = "{ 'regDate' : -1 }")
    Page<ChatMessage> getChatMessageByRoomIdAndTimeBefore(Pageable pageable, Long roomID, LocalDateTime disConnectTime);

    @Query("{ 'roomId' : ?0, 'regDate' : { $gt : ?1 } }")
    Page<ChatMessage> getChatMessageByRoomIdAndTimeAfter(Pageable pageable, Long roomID, LocalDateTime disConnectTime);

    @Query("{ 'roomId' : ?0, 'regDate' : { $gte : ?2 }, 'senderEmail' : { $ne : ?1 } }")
    List<ChatMessage> updateByRoomIdAndSenderEmailAndTime(Long roomId, String userEmail, LocalDateTime disConnect);

    @Query(value = "{ 'roomId' : ?0, 'senderEmail' : { $ne : ?1 }, 'regDate' : { $gte : ?2 } }", count = true)
    Long getNotReadNum(Long roomID, String loginEmail, LocalDateTime disConnectTime);
}
//public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
//    @Query("SELECT cm, cm.cid FROM ChatMessage cm WHERE cm.roomId =:roomID AND :disConnectTime >= cm.regDate ORDER BY cm.regDate DESC")
//    Page<Object[]> getChatMessageByRoomIdAndTimeBefore(Pageable pageable, Long roomID, LocalDateTime disConnectTime);
//
//    @Query("SELECT cm, cm.cid FROM ChatMessage cm WHERE cm.roomId =:roomID AND :disConnectTime < cm.regDate ORDER BY cm.cid")
//    Page<Object[]> getChatMessageByRoomIdAndTimeAfter(Pageable pageable, Long roomID, LocalDateTime disConnectTime);
//
//
//    @Modifying
//    @Transactional
//    @Query("UPDATE ChatMessage cm SET cm.readStatus = cm.readStatus -1 WHERE cm.regDate >= :disConnect AND cm.roomId =:roomId AND cm.senderEmail != :userEmail")
//    int updateByRoomIdAndSenderEmailAndTime(Long roomId, String userEmail, LocalDateTime disConnect);
//
//    @Query("SELECT count(cm) FROM ChatMessage cm WHERE cm.roomId =:roomID AND cm.senderEmail != :loginEmail AND cm.regDate >= :disConnectTime")
//    Long getNotReadNum(Long roomID ,String loginEmail, LocalDateTime disConnectTime);
//}
