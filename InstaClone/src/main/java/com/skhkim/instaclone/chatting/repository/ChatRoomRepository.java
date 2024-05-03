package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.id = :namesToId")
    Optional<ChatRoom> getChatIdbyEmails(@Param("namesToId") String namesToId);

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.userName =:loginName" +
            " or cr.friendName =:loginName")
    List<ChatRoom> getChatRoomsListByName(@Param("loginName") String loginName);

    @Query("SELECT cr ,pi FROM ChatRoom cr left join ProfileImage pi on cr.userName = pi.userName " +
            "WHERE cr.userName != :loginName and cr.friendName = :loginName")
    List<Object[]> getChatroomAndProfileImageByUserName(String loginName);

    @Query("SELECT cr ,pi FROM ChatRoom cr left join ProfileImage pi on cr.friendName = pi.userName " +
            "WHERE cr.userName = :loginName and cr.friendName != :loginName")
    List<Object[]> getChatroomAndProfileImageByFriendName(String loginName);
}
