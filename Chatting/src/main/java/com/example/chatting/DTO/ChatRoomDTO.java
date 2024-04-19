package com.example.chatting.DTO;

import com.example.chatting.Entity.ChatMessage;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private Long id;
    private List<ChatMessage> messages = new ArrayList<>();
    private String userId1;
    private String userId2;
}
