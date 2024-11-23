package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.response.UserInfoResponse;

public interface LoginService {
    boolean checkName(String name);
    boolean checkEmail(String email);
    boolean checkDuplication(ClubMemberDTO memberDTO);

    boolean checkPassword(ClubMemberDTO memberDTO);

    void updatePassword(String memberName, String newPassword);
    void updateUserName(String changeName, String originalName);
    String register(ClubMemberDTO memberDTO);
    UserInfoResponse getClubMemberSearchbyNameAll(ProfilePageRequestDTO profilePageRequestDTO, String searchName, String loginName);
    ClubMember getClubMemberSearchbyEmail(String Email);
    boolean getUserExist(String name);

    default ClubMemberDTO entityToDTO(ClubMember clubMember){

        ClubMemberDTO clubMemberDTO = ClubMemberDTO.builder()
                .email(clubMember.getEmail())
                .password(clubMember.getPassword())
                .name(clubMember.getName())
                .fromSocial(clubMember.isFromSocial())
                .roleSet(clubMember.getRoleSet())
                .regDate(clubMember.getRegDate())
                .modDate(clubMember.getModDate())
                .build();

        return clubMemberDTO;
    }
}
