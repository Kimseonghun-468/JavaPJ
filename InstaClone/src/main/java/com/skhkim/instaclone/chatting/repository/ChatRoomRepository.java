package com.skhkim.instaclone.chatting.repository;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.roomId = :roomId")
    Optional<ChatRoom> getChatRoombyRoomId(Long roomId);

    @Query("SELECT cr.userNum FROM ChatRoom cr WHERE cr.roomId =:roomId")
    Long getUserNum(Long roomId);
}
