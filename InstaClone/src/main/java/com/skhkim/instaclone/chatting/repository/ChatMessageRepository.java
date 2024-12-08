package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatMessage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    @Query(value = "{ 'roomId' : ?0, 'regDate' : { $lte : ?1 } }", sort = "{ 'regdate' : -1 }")
    Slice<ChatMessage> selectChatMessageUp(Pageable pageable, Long roomID, LocalDateTime disConnectTime);

    @Query(value = "{ 'roomId' : ?0, 'regDate' : { $gt : ?1 } }", sort = "{ 'regdate' :  1 }")
    Slice<ChatMessage> selectChatMessageDown(Pageable pageable, Long roomID, LocalDateTime disConnectTime);

    @Query("{ 'roomId' : ?0, 'regDate' : { $gte : ?2 }, 'senderEmail' : { $ne : ?1 } }")
    List<ChatMessage> selectChatMessage(Long roomId, String loginEmail, LocalDateTime disConnect);

    @Query(value = "{ 'roomId' : ?0, 'senderEmail' : { $ne : ?1 }, 'regDate' : { $gte : ?2 } }", count = true)
    Long getNotReadNum(Long roomID, String loginEmail, LocalDateTime disConnectTime);
}

