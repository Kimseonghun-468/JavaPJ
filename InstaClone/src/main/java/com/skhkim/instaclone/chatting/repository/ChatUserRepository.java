package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    @Query("SELECT cu1.chatRoom.roomId FROM ChatUser cu1 " +
            "LEFT JOIN ChatUser cu2 ON cu1.chatRoom = cu2.chatRoom " +
            "WHERE cu1.member.email =:loginEmail AND cu2.member.email =:friendEmail")
    Optional<Long> getRoomIdByEmails(String loginEmail, String friendEmail);

    @Query("SELECT cu FROM ChatUser cu WHERE cu.chatRoom.roomId =:roomId AND cu.member.email =:loginEmail")
    ChatUser getChatUsersByRoomIdAndEmail(Long roomId, String loginEmail);

    @Query("SELECT cu.disConnect FROM ChatUser cu WHERE cu.member.email =:loginEmail AND cu.chatRoom.roomId =:roomId")
    LocalDateTime getDisConnectTimeByEmail(String loginEmail, Long roomId);

    @Query("SELECT cu.member.email, cu.member.name FROM ChatUser cu WHERE cu.chatRoom.roomId =:roomId")
    List<Object[]> getEmailAndNmaeByRoomId(Long roomId);

    @Query("SELECT cu2.member.name, cu2.member.email, cu1.chatRoom.lastChat, cu1.chatRoom.lastChatTime, cu1.chatRoom.roomId, pi " +
            "FROM ChatUser cu1 " +
            "JOIN ChatUser cu2 ON cu1.chatRoom = cu2.chatRoom AND cu1.member.email != cu2.member.email " +
            "LEFT JOIN ProfileImage pi ON pi.clubMember.email = cu2.member.email " +
            "WHERE cu1.member.email = :loginEmail " +
//            "GROUP BY cu1.chatRoom " +
            "ORDER BY cu1.chatRoom.lastChatTime")
    Page<Object[]> getChatroomAndProfileImage(Pageable pageable, String loginEmail);

}
