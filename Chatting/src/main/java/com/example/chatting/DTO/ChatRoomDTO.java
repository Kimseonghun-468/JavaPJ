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
    private String id;
    private String userName1;
    private String userName2;
    private List<ChatMessageDTO> messages = new ArrayList<>();
}
