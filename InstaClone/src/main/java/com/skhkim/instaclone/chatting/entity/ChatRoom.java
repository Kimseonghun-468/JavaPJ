package com.skhkim.instaclone.chatting.entity;
import com.skhkim.instaclone.entity.FriendShip;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private String lastChat;
    private Long userNum;
    private LocalDateTime lastChatTime;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatUser> chatUserList = new ArrayList<>();






}
