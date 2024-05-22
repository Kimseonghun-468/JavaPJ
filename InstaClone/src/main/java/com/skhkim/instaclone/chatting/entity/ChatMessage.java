package com.skhkim.instaclone.chatting.entity;

import com.skhkim.instaclone.entity.ClubMember;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"chatRoom", "clubMember"})
public class ChatMessage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClubMember clubMember;
    private String content;

    private boolean readStatus;

    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;


}