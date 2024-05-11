package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.id = :namesToId")
    Optional<ChatRoom> getChatIdbyNames(@Param("namesToId") String namesToId);

    @Query("SELECT CASE WHEN cr.userName1 = :loginName THEN cr.userName2 ELSE cr.userName1 END as userName, cr.lastChat, cr.lastChatTime, pi " +
            "FROM ChatRoom cr LEFT JOIN ProfileImage pi ON (CASE WHEN cr.userName1 = :loginName THEN cr.userName2 ELSE cr.userName1 END) = pi.userName " +
            "WHERE (cr.userName1 = :loginName OR cr.userName2 = :loginName) AND (cr.userName1 != cr.userName2) ORDER BY cr.lastChatTime DESC ")
    Page<Object[]> getChatroomAndProfileImage(Pageable pageable, String loginName);
}
