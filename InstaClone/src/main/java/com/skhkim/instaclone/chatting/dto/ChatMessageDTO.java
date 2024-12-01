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
    private String cid;
    private Long roomId;
    private String senderEmail;
    private String content;
    private Long readStatus;
    private LocalDateTime regDate;
    private String profileImageUrl;
}
