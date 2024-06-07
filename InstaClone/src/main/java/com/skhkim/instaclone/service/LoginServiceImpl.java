package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.*;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
@Log4j2
public class LoginServiceImpl implements LoginService{

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkDuplication(ClubMemberDTO memberDTO){
        log.info("Club member DTO in Duplication : " + memberDTO);
        boolean checkResult = clubMemberRepository.existsByNameAndEmail(memberDTO.getName(),memberDTO.getEmail());
        return checkResult;
    }
    @Override
    public void updateUserName(String changeName, String originalName){
        clubMemberRepository.updateByName(changeName, originalName);
    }
    @Override
    public boolean checkPassword(ClubMemberDTO memberDTO){
        ClubMember member = clubMemberRepository.findByName(memberDTO.getName());
        return passwordEncoder.matches(memberDTO.getPassword(), member.getPassword());
    }

    @Override
    public void updatePassword(String memberName, String newPassword){
        String discriptedPassword = passwordEncoder.encode(newPassword);
        clubMemberRepository.updateByPassword(discriptedPassword, memberName);
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
    public ProfilePageResultDTO<Map<String, Object>, Object[]> getClubMemberSearchbyNameAll(ProfilePageRequestDTO profilePageRequestDTO, String name, String userName){
        Pageable pageable = profilePageRequestDTO.getPageable();
        Page<Object[]> result = clubMemberRepository.findByNamePage(pageable, name, userName);

        Function<Object[], Map<String,Object>> fn = (arr ->{
            Map<String, Object> profileAndSearchMap = new HashMap<>();
            profileAndSearchMap.put("searchName",((ClubMember) arr[0]).getName());
            if (arr[1] == null)
                profileAndSearchMap.put("profileImage",null);
            else
                profileAndSearchMap.put("profileImage",entityToDTOByProfileImage((ProfileImage) arr[1]));

            return profileAndSearchMap;
        });
        return new ProfilePageResultDTO<>(result, fn);


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

    @Override
    public boolean checkName(String name){
        return clubMemberRepository.existsByName(name);
    }

    @Override
    public boolean checkEmail(String email){
        return clubMemberRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public ClubMemberDTO getClubMemberByName(String name){
        ClubMember clubMember = clubMemberRepository.findByName(name);
        ClubMemberDTO clubMemberDTO = entityToDTO(clubMember);

        return clubMemberDTO;
    }
}
