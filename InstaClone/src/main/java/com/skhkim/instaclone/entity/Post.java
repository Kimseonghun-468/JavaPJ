package com.skhkim.instaclone.entity;

import com.skhkim.instaclone.chatting.entity.ChatUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "clubMember")
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;
    private String title;
    private String comment;

    @OneToOne(fetch = FetchType.LAZY) // 이거 필요하나..?
    private ClubMember clubMember;

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImageList = new ArrayList<>();
}
