package com.skhkim.instaclone.chatting.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserDTO {
    private Long userId;
    private String userEmail;
    private Long roomId;
    private LocalDateTime disConnect;

}
