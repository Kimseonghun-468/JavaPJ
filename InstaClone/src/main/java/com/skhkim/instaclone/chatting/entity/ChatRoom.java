package com.skhkim.instaclone.chatting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChatRoom {

    @Id
    private String id;

    @Column(nullable = false)
    private String userName1;

    @Column(nullable = false)
    private String userName2;


}
