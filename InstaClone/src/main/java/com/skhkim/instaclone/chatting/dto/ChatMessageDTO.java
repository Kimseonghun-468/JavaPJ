package com.skhkim.instaclone.chatting.dto;

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

    private boolean readStatus;
}
