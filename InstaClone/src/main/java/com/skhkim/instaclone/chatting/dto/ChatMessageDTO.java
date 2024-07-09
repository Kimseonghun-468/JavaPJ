package com.skhkim.instaclone.chatting.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private String cid;
    private String senderEmail;
    private String content;
    private Long readStatus;
    private LocalDateTime regDate;
    private String profileImageUrl;
}
