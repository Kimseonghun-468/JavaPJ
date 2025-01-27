package com.skhkim.instaclone.chatting.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessageDTO {
    private Long cid;
    private Long roomId;
    private String senderName;
    private String content;
    private Long readStatus;
    private String inviterName;
    private String inviteNames;
    private LocalDateTime regDate;
    private String profileImageUrl;
}
