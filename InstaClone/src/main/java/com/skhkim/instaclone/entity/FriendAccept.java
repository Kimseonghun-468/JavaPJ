package com.skhkim.instaclone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FriendAccept extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1")
    private ClubMember user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2")
    private ClubMember user2;

}
