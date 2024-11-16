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

    }
}
