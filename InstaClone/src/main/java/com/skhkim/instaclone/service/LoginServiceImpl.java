package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.*;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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
        boolean checkResult = clubMemberRepository.existsByNameAndEmail(memberDTO.getName(),memberDTO.getEmail());
        return checkResult;
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
    public ProfilePageResultDTO<Map<String, Object>, Object[]> getClubMemberSearchbyNameAll(ProfilePageRequestDTO profilePageRequestDTO, String name){
        Pageable pageable = profilePageRequestDTO.getPageable();
        Page<Object[]> result = clubMemberRepository.findByNamePage(pageable, name);

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
}
