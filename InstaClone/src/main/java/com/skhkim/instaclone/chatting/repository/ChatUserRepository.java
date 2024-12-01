package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    @Query("SELECT cu1.chatRoom.roomId FROM ChatUser cu1 " +
            "LEFT JOIN ChatUser cu2 ON cu1.chatRoom = cu2.chatRoom " +
            "WHERE cu1.member.email =:loginEmail AND cu2.member.email =:friendEmail AND cu1.chatRoom.userNum = 2")
    Optional<Long> getRoomIdByEmails(String loginEmail, String friendEmail);

    @Query("SELECT cu FROM ChatUser cu WHERE cu.chatRoom.roomId =:roomId AND cu.member.email =:loginEmail")
    ChatUser getChatUsersByRoomIdAndEmail(Long roomId, String loginEmail);

    @Query("SELECT cu.disConnect FROM ChatUser cu WHERE cu.member.email =:loginEmail AND cu.chatRoom.roomId =:roomId")
    LocalDateTime getDisConnectTimeByEmail(String loginEmail, Long roomId);

    @Query("SELECT cu FROM ChatUser cu WHERE cu.member.name =:loginName AND cu.chatRoom.roomId = :roomId")
    ChatUser selectChatUser(Long roomId, String loginName);

    @Query("SELECT cu FROM ChatUser cu WHERE cu.member.name in :userNameList AND cu.chatRoom.roomId = :roomId")
    List<ChatUser> selectChatUserList(Long roomId, List<String> userNameList);

    @Query("SELECT cu.member.email, cu.member.name FROM ChatUser cu WHERE cu.chatRoom.roomId =:roomId")
    List<Object[]> getEmailAndNmaeByRoomId(Long roomId);

    @Query("SELECT CU.chatRoom FROM ChatUser CU " +
            "WHERE CU.member.email = :loginEmail " +
            "ORDER BY CU.chatRoom.lastChatTime DESC")
    Slice<ChatRoom> getTest(Pageable pageable, String loginEmail);

    @Query("SELECT cu FROM ChatUser cu " +
            "WHERE cu.chatRoom.roomId =:roomId")
    List<ChatUser> selectChatRoomUsers(Long roomId);

}
