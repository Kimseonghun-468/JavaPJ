package com.skhkim.instaclone.chatting.entity;

import com.skhkim.instaclone.entity.ClubMember;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"clubMemberUser1", "clubMemberUser2"})
public class ChatRoom {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClubMember clubMemberUser1;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClubMember clubMemberUser2;

    @Column(nullable = false)
    private LocalDateTime lastDisConnect1;
    @Column(nullable = false)
    private LocalDateTime lastDisConnect2;
    private LocalDateTime lastChatTime;
    private String lastChat;





}
