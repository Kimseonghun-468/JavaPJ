package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {


    @Query("SELECT CM FROM ChatMessage CM WHERE CM.roomId = :roomId AND CM.cid <= :lastCid " +
            "AND CM.cid > :joinCid " +
            "ORDER BY CM.cid DESC")
    Slice<ChatMessage> selectChatMessageUp(Pageable pageable, Long roomId, Long lastCid, Long joinCid);

    @Query("SELECT CM FROM ChatMessage CM WHERE CM.roomId =:roomId AND CM.cid > :lastCid AND CM.cid > :joinCid " +
            "ORDER BY CM.cid ASC")
    Slice<ChatMessage> selectChatMessageDown(Pageable pageable, Long roomId, Long lastCid, Long joinCid);

    @Query("SELECT CM FROM ChatMessage CM WHERE CM.roomId =:roomId " +
            "AND CM.sendUser.email != :loginEmail AND CM.cid > :lastCid AND CM.cid > :joinCid")
    List<ChatMessage> selectChatMessage(Long roomId, String loginEmail, Long lastCid, Long joinCid);

    @Query("SELECT count(*) FROM ChatMessage CM WHERE CM.roomId =:roomId " +
            "AND CM.sendUser.email != :loginEmail AND CM.cid > :lastCid AND CM.cid > :joinCid")
    Long getNotReadNum(Long roomId, String loginEmail, Long lastCid, Long joinCid);
}

