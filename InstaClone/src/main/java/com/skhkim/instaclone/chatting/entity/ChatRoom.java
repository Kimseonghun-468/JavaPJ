package com.skhkim.instaclone.chatting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatRoom {

    @Id
    private String id;

    @Column(nullable = false)
    private String userName1;

    @Column(nullable = false)
    private String userName2;

    @Column(nullable = false)
    private LocalDateTime lastDisConnect1;
    @Column(nullable = false)
    private LocalDateTime lastDisConnect2;
    private LocalDateTime lastChatTime;
    private String lastChat;





}
