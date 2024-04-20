package com.example.chatting.Repository;

import com.example.chatting.Entity.ChatMessage;
import com.example.chatting.Entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.id = :emailsToId")
    Optional<ChatRoom> getChatIdbyEmails(@Param("emailsToId") String emailsToId);
}
