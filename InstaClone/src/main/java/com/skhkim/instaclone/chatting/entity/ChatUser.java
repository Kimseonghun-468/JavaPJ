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
@ToString(exclude = {"member", "chatRoom"})
public class ChatUser extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClubMember member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @Column(nullable = false)
    private LocalDateTime disConnect;
}
