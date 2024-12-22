package com.skhkim.instaclone.chatting.entity;

import com.skhkim.instaclone.entity.ClubMember;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"sendUser"})
public class ChatMessage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClubMember sendUser;

    private String invitedUser;

    private String inviteUser;

    private String content;

    private Long readStatus;

}