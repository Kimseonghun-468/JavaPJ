package com.skhkim.instaclone.service;

import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.Reply;
import com.skhkim.instaclone.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {
    @Autowired
    private ReplyRepository replyRepository;


//    @Test
//    public void registerTests(){
//
//        String email = "seonghun@naver.com";
//        Reply postReply = Reply.builder()
//                .text("Reply Test3")
//                .post(Post.builder().pno(32L).build())
//                .clubMember(ClubMember.builder().email(email).build())
//                .build();
//        replyRepository.save(postReply);
//
//    }
//    @Test
//    public void testGetPostReply() {
//        Post post = Post.builder().pno(32L).build();
//
//        List<Reply> result = replyRepository.findByPost(post);
//
//        result.forEach(postReview -> {
//            System.out.print(postReview.getRno());
//            System.out.print("  " + postReview.getPost().getPno());
//            System.out.print("  " + postReview.getText());
//            System.out.print("  " + postReview.getClubMember().getEmail());
//            System.out.println("------------------------");
//
//        });
//    }
}
