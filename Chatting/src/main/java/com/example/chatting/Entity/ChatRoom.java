package com.example.chatting.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> messages = new ArrayList<>();
    @Column(nullable = false)
    private String userId1;

    @Column(nullable = false)
    private String userId2;
}
