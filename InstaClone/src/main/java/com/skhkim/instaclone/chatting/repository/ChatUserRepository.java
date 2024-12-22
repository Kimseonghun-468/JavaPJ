package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

    @Query("SELECT CU_LOGIN.chatRoom.roomId FROM ChatUser CU_LOGIN " +
            "JOIN ChatUser CU_USER ON CU_LOGIN.chatRoom.roomId = CU_USER.chatRoom.roomId " +
            "WHERE CU_LOGIN.member.name =:loginName AND CU_USER.member.name =:userName " +
            "AND CU_LOGIN.chatRoom.userNum = 2")
    Optional<Long> checkChatRoom(String loginName, String userName);


    @Query("SELECT cu FROM ChatUser cu WHERE cu.chatRoom.roomId =:roomId AND cu.member.email =:loginEmail")
    ChatUser selectChatUser(Long roomId, String loginEmail);



    @Query("SELECT CU.chatRoom FROM ChatUser CU " +
            "WHERE CU.member.email = :loginEmail " +
            "ORDER BY CU.chatRoom.lastCid DESC")
    Slice<ChatRoom> selectChatRoom(Pageable pageable, String loginEmail);

    @Query("SELECT cu FROM ChatUser cu " +
            "WHERE cu.chatRoom.roomId =:roomId")
    List<ChatUser> selectChatUsers(Long roomId);

}
