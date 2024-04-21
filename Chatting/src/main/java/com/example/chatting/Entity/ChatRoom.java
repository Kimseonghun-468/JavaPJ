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
    private String id;

    @Column(nullable = false)
    private String userName1;

    @Column(nullable = false)
    private String userName2;


}
