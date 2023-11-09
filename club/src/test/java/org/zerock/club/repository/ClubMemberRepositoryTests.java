package org.zerock.club.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.entity.ClubMemberRole;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberRepositoryTests {
    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Test
//    public void insertDummies(){
//        IntStream.rangeClosed(1, 100).forEach(i -> {
//            ClubMember clubMember = ClubMember.builder()
//                    .email("user"+i+"zerock.org")
//                    .name("사용자+"+i)
//                    .fromSocial(false)
//                    .password(passwordEncoder.encode("1111"))
//                    .build();
//            clubMember.addMemberRole(ClubMemberRole.USER);
//
//            if(i>80){
//                clubMember.addMemberRole(ClubMemberRole.MANAGER);
//            }
//            if(i>90){
//                clubMember.addMemberRole(ClubMemberRole.ADMIN);
//            }
//            clubMemberRepository.save(clubMember);
//        });
//    }
    @Test
    public void testRead(){
        Optional<ClubMember> result = clubMemberRepository.findByEmail("user95zerock.org", false);
        ClubMember clubMember = result.get();
        System.out.println(clubMember);
//        List<Object[]> result = clubMemberRepository.findByEmail("user95zerock.org", false);
//        Object[] arr = result.get(0);

    }
}
