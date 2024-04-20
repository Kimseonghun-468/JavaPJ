package com.example.chatting.Repository;

import com.example.chatting.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<List<ChatMessage>> findByChatRoomId(String roomID);
}
