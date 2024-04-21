package com.example.chatting.DTO;

import com.example.chatting.Entity.ChatRoom;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private Long cid;
    private String name;
    private String content;
    private LocalDateTime regDate;
}
