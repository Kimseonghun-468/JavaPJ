package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String>, ChatMessageDSL {
    @Query("SELECT CM FROM ChatMessage CM WHERE CM.roomId =:roomId " +
            "AND CM.sendUser.email != :loginEmail AND CM.cid > :lastCid AND CM.cid > :joinCid")
    List<ChatMessage> selectChatMessage(@Param("roomId") Long roomId,
                                        @Param("loginEmail") String loginEmail,
                                        @Param("lastCid") Long lastCid,
                                        @Param("joinCid") Long joinCid);

    @Query("SELECT count(*) FROM ChatMessage CM WHERE CM.roomId =:roomId " +
            "AND CM.sendUser.email != :loginEmail AND CM.cid > :lastCid AND CM.cid > :joinCid")
    Long getNotReadNum(@Param("roomId") Long roomId,
                       @Param("loginEmail") String loginEmail,
                       @Param("lastCid") Long lastCid,
                       @Param("joinCid") Long joinCid);
}
