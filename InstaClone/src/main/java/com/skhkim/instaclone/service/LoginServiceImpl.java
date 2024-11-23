package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.*;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;



@Service
@RequiredArgsConstructor
@Log4j2
public class LoginServiceImpl implements LoginService{

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkDuplication(ClubMemberDTO memberDTO){
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

        ClubMember clubMember = EntityMapper.dtoToEntity(memberDTO);
        clubMember.addMemberRole(ClubMemberRole.USER);
        String discriptedPassword = passwordEncoder.encode(clubMember.getPassword());

        clubMember.changePassword(discriptedPassword);
        clubMemberRepository.save(clubMember);
        return clubMember.getEmail();
    }
    @Override
    @Transactional
    public UserInfoResponse getClubMemberSearchbyNameAll(ProfilePageRequestDTO profilePageRequestDTO, String searchName, String loginName){
        Pageable pageable = profilePageRequestDTO.getPageable();
        Slice<ClubMember> result = clubMemberRepository.selectSearchUserInfo(pageable, searchName, loginName);

        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());

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
