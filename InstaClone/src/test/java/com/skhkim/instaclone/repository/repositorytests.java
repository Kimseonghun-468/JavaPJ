package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendShip;
import com.skhkim.instaclone.entity.FriendShipStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
public class repositorytests {

    @Autowired
    private PostRepository postRepository;
    private ProfileImageRepository profileImageRepository;
    @Autowired
    private FriendShipRepository friendShipRepository;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Commit
    @Transactional
    @Test
    public void testtt() {
        System.out.println("What@@@@@@@@@@@@@@@");
        ClubMember member1 = clubMemberRepository.findByName("Seognhun");
        ClubMember faw = clubMemberRepository.findByName("faw");
        String testEmail = "testEmail";
        String testName = "testName";

        for (int i = 0; i < 100; i++) {
            FriendShip friendShipRequester = FriendShip.builder()
                    .clubMember(member1)
                    .userEmail(member1.getEmail())
                    .friendEmail(testEmail+i)
                    .userName(member1.getName())
                    .friendName(testName+i)
                    .status(FriendShipStatus.WAITING)
                    .isFrom(false)
                    .build();
            // 받는 요청
            FriendShip friendShipAccepter = FriendShip.builder()
                    .clubMember(faw)
                    .userEmail(testEmail+i)
                    .userName(testName+i)
                    .friendEmail(member1.getEmail())
                    .friendName(member1.getName())
                    .status(FriendShipStatus.WAITING)
                    .isFrom(true)
                    .build();

            friendShipRepository.save(friendShipRequester);
            friendShipRepository.save(friendShipAccepter);
        }

    }
//    public void testListPage(){
//        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "pno"));
//        Page<Object[]> result = postRepository.getListPage(pageRequest, "seonghun@naver.com");
////        Long count = postRepository.getPostCount("seonghun@naver.com");
////        System.out.println(count);
//        for(Object[] objects : result.getContent()){
//            System.out.println(Arrays.toString(objects));
//        }
//    }
//    @Test
//    public void testProfileImage(){
//        ProfileImageDTO =
//    }
}
