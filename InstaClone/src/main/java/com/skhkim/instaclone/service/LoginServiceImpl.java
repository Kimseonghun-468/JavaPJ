package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.ClubMemberRole;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
//        여기서 Name도 중복 처리를 해주도록 하자..
    }

    @Override
    public String register(ClubMemberDTO memberDTO){

        ClubMember clubMember = dtoToEntity(memberDTO);
        clubMember.addMemberRole(ClubMemberRole.USER);
        String discriptedPassword = passwordEncoder.encode(clubMember.getPassword());

        clubMember.changePassword(discriptedPassword);
        clubMemberRepository.save(clubMember);
        return clubMember.getEmail();
    }
    @Override
    public ClubMemberDTO getClubMemberSearchbyName(String name) {
        ClubMember result = clubMemberRepository.findByName(name);
        return result != null ? entityToDTO(result) : ClubMemberDTO.builder().build();
    }
    @Override
    public ClubMemberDTO getClubMemberSearchbyEmail(String Email) {
        Optional<ClubMember> result = clubMemberRepository.findByEmail(Email);
        return result.isPresent() ? entityToDTO(result.get()) : ClubMemberDTO.builder().build();
    }
    @Override
    public boolean getUserExist(String name){
        ClubMember result = clubMemberRepository.findByName(name);
        return result != null;
    }
}
