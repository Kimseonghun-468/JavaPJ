package com.example.chatting.DTO;

import com.example.chatting.Entity.ChatRoom;
import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private String name;
    private String senderId;
    private String content;
}
