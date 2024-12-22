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
    private String senderEmail;
    private String content;
    private Long readStatus;
    private String inviterEmail;
    private String inviteEmails;
    private LocalDateTime regDate;
    private String profileImageUrl;
}
