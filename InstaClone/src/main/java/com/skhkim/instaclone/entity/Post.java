package com.skhkim.instaclone.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private ClubMember clubMember;

    @Builder.Default
    @OneToMany(mappedBy = "post")
    private List<PostImage> postImageList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post")
    private List<Reply> postReplyList = new ArrayList<>();

    public int getLikeNum() {
        return postLikeList.size();
    }

    public int getReplyNum(){
        return postReplyList.size();
    }

}
