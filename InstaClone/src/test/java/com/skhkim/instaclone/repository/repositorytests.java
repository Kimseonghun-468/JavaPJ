package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.repository.ChatMessageRepository;
import com.skhkim.instaclone.chatting.repository.ChatRoomRepository;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import com.skhkim.instaclone.chatting.service.ChatUserService;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.ClubMemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
public class repositorytests {

    @Autowired
    private PostRepository postRepository;
    private ProfileImageRepository profileImageRepository;
//    @Autowired
//    private FriendShipRepository friendShipRepository;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private ChatUserRepository chatUserRepository;
    @Autowired
    private ChatUserService chatUserService;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Commit
//    @Transactional
    @Test
    public void testtt() {
//        ClubMember user1 =   clubMemberRepository.findByName("profile2");
//        ClubMember user2 = clubMemberRepository.findByName("profile3");
//        ChatRoom chatRoom = chatRoomRepository.getChatRoombyRoomId(2L).get();
//                for(int k= 1; k<=500000; k++){
//                    ChatMessage chatMessageUser1 = ChatMessage.builder()
//                            .content("Test Comment : " + ((k*2)-1))
//                            .readStatus(0L)
//                            .roomId(chatRoom.getRoomId())
//                            .senderEmail(user1.getEmail())
//                            .regDate(LocalDateTime.now())
//                            .build();
//                    ChatMessage chatMessageUser2 = ChatMessage.builder()
//                            .content("Test Comment : " + k*2)
//                            .readStatus(0L)
//                            .roomId(chatRoom.getRoomId())
//                            .senderEmail(user2.getEmail())
//                            .regDate(LocalDateTime.now())
//                            .build();
//                    chatMessageRepository.save(chatMessageUser1);
//                    chatMessageRepository.save(chatMessageUser2);
//
//                    if (k==5){
//                        chatRoom.setLastChat(chatMessageUser2.getContent());
//                        chatRoom.setLastChatTime(LocalDateTime.now());
//                        chatRoomRepository.save(chatRoom);
//                    }
//                }



//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        for (int i =1; i<=100; i++){
//            String formattedNumber = String.format("%03d", i);
//
//            ClubMember clubMember = ClubMember.builder()
//                    .email("TestUser"+formattedNumber+"@testEmail.com")
//                    .name("TestUset"+formattedNumber)
//                    .password(passwordEncoder.encode("1111"))
//                    .fromSocial(false)
//                    .build();
//            clubMember.addMemberRole(ClubMemberRole.USER);
//            clubMemberRepository.save(clubMember);
//        }

//        for (int i=1; i<=100; i++){
//            String formattedNumber_I = String.format("%03d", i);
//            for (int j=i+1; j<=100; j++){
//                if (i==j)
//                    continue;
//                String formattedNumber_J = String.format("%03d", j);
//
//                String user1 = "TestUser"+formattedNumber_J+"@testEmail.com";
//                String user2 = "TestUser"+formattedNumber_I+"@testEmail.com";
//
//                FriendShip friendShipRequester = FriendShip.builder()
//                        .clubMemberUser(ClubMember.builder().email(user1).build())
//                        .clubMemberFriend(ClubMember.builder().email(user2).build())
//                        .status(FriendShipStatus.ACCEPT)
//                        .isFrom(false)
//                        .build();
                // 받는 요청
//                FriendShip friendShipAccepter = FriendShip.builder()
//                        .clubMemberUser(ClubMember.builder().email(user2).build())
//                        .clubMemberFriend(ClubMember.builder().email(user1).build())
//                        .status(FriendShipStatus.ACCEPT)
//                        .isFrom(true)
//                        .build();
//
//                friendShipRepository.save(friendShipRequester);
//                friendShipRepository.save(friendShipAccepter);
//
//                ChatRoom chatRoom = ChatRoom.builder()
//                        .userNum(2L)
//                        .build();
//                chatRoomRepository.save(chatRoom);
//
//                chatUserService.register(user1, chatRoom.getRoomId());
//                chatUserService.register(user2, chatRoom.getRoomId());
////
//                // 이 부분 만들었던 10개로 , roomid는 위에꺼, sender Email은 I, J, readStatus는 0로
//                // content(안녕 등등)
//                for(int k= 1; k<=5; k++){
//                    ChatMessage chatMessageUser1 = ChatMessage.builder()
//                            .content("Test Comment : " + ((k*2)-1))
//                            .readStatus(0L)
//                            .roomId(chatRoom.getRoomId())
//                            .senderEmail(user1)
//                            .regDate(LocalDateTime.now())
//                            .build();
//                    ChatMessage chatMessageUser2 = ChatMessage.builder()
//                            .content("Test Comment : " + k*2)
//                            .readStatus(0L)
//                            .roomId(chatRoom.getRoomId())
//                            .senderEmail(user2)
//                            .regDate(LocalDateTime.now())
//                            .build();
//                    chatMessageRepository.save(chatMessageUser1);
//                    chatMessageRepository.save(chatMessageUser2);
//
//                    if (k==5){
//                        chatRoom.setLastChat(chatMessageUser2.getContent());
//                        chatRoom.setLastChatTime(LocalDateTime.now());
//                        chatRoomRepository.save(chatRoom);
//                    }
//                }
//            }
//        }

//        System.out.println("What@@@@@@@@@@@@@@@");
//        ClubMember member1 = clubMemberRepository.findByName("Seognhun");
//        ClubMember faw = clubMemberRepository.findByName("faw");
//        String testEmail = "testEmail";
//        String testName = "testName";
//
//        for (int i = 0; i < 100; i++) {

    }
}
