package com.skhkim.instaclone.chatting.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private String id;
    private String userName1;
    private String userName2;
    private String lastChat;
    private LocalDateTime lastDisConnect1;
    private LocalDateTime lastDisConnect2;
    private LocalDateTime lastChatTime;
}
