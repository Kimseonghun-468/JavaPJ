package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.ClubMemberRole;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoginServiceImpl implements LoginService{

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkDuplication(ClubMemberDTO memberDTO){
        log.info("Club member DTO in Duplication : " + memberDTO);

        boolean checkResult = clubMemberRepository.existsByEmail(memberDTO.getEmail());
        return checkResult;
    }

    @Override
    public String register(ClubMemberDTO memberDTO){

        ClubMember clubMember = dtoToEntity(memberDTO);
        clubMember.addMemberRole(ClubMemberRole.USER);
        String discriptedPassword = passwordEncoder.encode(clubMember.getPassword());

        clubMember.changePassword(discriptedPassword);
        clubMemberRepository.save(clubMember);
        //dtotoentity
        //save entity
        //return email.
        return clubMember.getEmail();
    }
}
