package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import com.skhkim.instaclone.entity.ClubMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
public class repositorytests {

    @Autowired
    private PostRepository postRepository;
    private ProfileImageRepository profileImageRepository;
    @Autowired
    private FriendShipRepository friendShipRepository;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private ChatUserRepository chatUserRepository;
    @Commit
    @Transactional
    @Test
    public void testtt() {
        ChatUser chatUser = ChatUser.builder()
                .chatRoom(ChatRoom.builder().roomId(1L).build())
                .disConnect(LocalDateTime.now())
                .member(ClubMember.builder().email("profile5@naver.com").build())
                .build();
        chatUserRepository.save(chatUser);
//        System.out.println("What@@@@@@@@@@@@@@@");
//        ClubMember member1 = clubMemberRepository.findByName("Seognhun");
//        ClubMember faw = clubMemberRepository.findByName("faw");
//        String testEmail = "testEmail";
//        String testName = "testName";
//
//        for (int i = 0; i < 100; i++) {
//            FriendShip friendShipRequester = FriendShip.builder()
//                    .clubMember(member1)
//                    .userEmail(member1.getEmail())
//                    .friendEmail(testEmail+i)
//                    .userName(member1.getName())
//                    .friendName(testName+i)
//                    .status(FriendShipStatus.WAITING)
//                    .isFrom(false)
//                    .build();
//            // 받는 요청
//            FriendShip friendShipAccepter = FriendShip.builder()
//                    .clubMember(faw)
//                    .userEmail(testEmail+i)
//                    .userName(testName+i)
//                    .friendEmail(member1.getEmail())
//                    .friendName(member1.getName())
//                    .status(FriendShipStatus.WAITING)
//                    .isFrom(true)
//                    .build();
//
//            friendShipRepository.save(friendShipRequester);
//            friendShipRepository.save(friendShipAccepter);
        }
}
