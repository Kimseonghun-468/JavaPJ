package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.id = :namesToId")
    Optional<ChatRoom> getChatIdbyNames(@Param("namesToId") String namesToId);

    @Query("SELECT " +
            "CASE WHEN cr.clubMemberUser1.name = :loginName THEN cr.clubMemberUser2.name ELSE cr.clubMemberUser1.name END as userName, " +
            "CASE WHEN cr.clubMemberUser1.name = :loginName THEN cr.clubMemberUser2.email ELSE cr.clubMemberUser1.email END as userEmail, " +
            "cr.lastChat, cr.lastChatTime, pi " +
            "FROM ChatRoom cr " +
            "LEFT JOIN ProfileImage pi ON (CASE WHEN cr.clubMemberUser1.name = :loginName THEN cr.clubMemberUser2.name ELSE cr.clubMemberUser1.name END) = pi.clubMember.name " +
            "WHERE (cr.clubMemberUser1.name = :loginName OR cr.clubMemberUser2.name = :loginName) " +
            "AND cr.clubMemberUser1.name != cr.clubMemberUser2.name " +
            "ORDER BY cr.lastChatTime DESC")
    Page<Object[]> getChatroomAndProfileImage(Pageable pageable, String loginName);
}
