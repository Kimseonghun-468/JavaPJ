package com.example.chatting.Repository;

import com.example.chatting.Entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<List<ChatMessage>> findByChatRoomId(String roomID);

    @Query("SELECT cm, cm.cid FROM ChatMessage cm WHERE cm.chatRoom.id =:roomID")
    Page<Object[]> findByChatRoomId(Pageable pageable, @Param("roomID") String roomID);
}
