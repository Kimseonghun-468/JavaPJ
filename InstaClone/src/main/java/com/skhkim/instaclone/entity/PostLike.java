package com.skhkim.instaclone.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "clubMember")
public class PostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lno;

    @OneToOne(fetch = FetchType.LAZY)
    private ClubMember clubMember;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

}
