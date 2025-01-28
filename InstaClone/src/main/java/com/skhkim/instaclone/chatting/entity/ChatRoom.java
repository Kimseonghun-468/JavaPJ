package com.skhkim.instaclone.chatting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "chatUserList")
public class ChatRoom extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private String lastChat;
    private Long userNum;
    private Long lastCid;

    @OneToMany(mappedBy = "chatRoom")
    @Builder.Default
    private List<ChatUser> chatUserList = new ArrayList<>();






}
