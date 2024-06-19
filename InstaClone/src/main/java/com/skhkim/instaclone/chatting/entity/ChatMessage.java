package com.skhkim.instaclone.chatting.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChatMessage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    private Long roomId;
    private String senderEmail;
    private String content;
    private Long readStatus;
    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;


}