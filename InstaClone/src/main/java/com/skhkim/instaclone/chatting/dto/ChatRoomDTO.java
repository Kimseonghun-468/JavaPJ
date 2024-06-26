package com.skhkim.instaclone.chatting.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private Long roomId;
    private String lastChat;
    private Long userNum;
    private LocalDateTime lastChatTime;

}
