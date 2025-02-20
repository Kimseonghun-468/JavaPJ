package com.skhkim.instaclone.service;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.*;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.request.UserInfoPageRequest;
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
public class MemberServiceImpl implements MemberService {

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean checkDuplication(ClubMemberDTO memberDTO){
        return clubMemberRepository.signValidation(memberDTO.getName(),memberDTO.getEmail());
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
        clubMemberRepository.updateByPassward(discriptedPassword, memberName);
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
    public UserInfoResponse selectSearchUsers(UserInfoPageRequest userInfoPageRequest, String searchName){
        Pageable pageable = userInfoPageRequest.getPageable();
        Long userId = LoginContext.getClubMember().getUserId();
        Slice<ClubMember> result = clubMemberRepository.selectSearchUserList(pageable, searchName, userId);
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());

    }
    @Override
    public ClubMember selectClubMember(String Email) {
        Optional<ClubMember> result = clubMemberRepository.selectByEmail(Email);
        return result.orElseGet(() -> ClubMember.builder().build());
    }
    @Override
    public boolean getUserExist(String name){
        ClubMember result = clubMemberRepository.findByName(name);
        return result != null;
    }

    @Override
    public UserInfoDTO selectUserInfo(String userName){
        ClubMember clubMember = clubMemberRepository.selectByName(userName);
        return EntityMapper.entityToDTO(clubMember);
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
