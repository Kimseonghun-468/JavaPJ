package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.ProfileImage;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

public interface LoginService {

    ClubMemberDTO getClubMemberByName(String name);

    boolean checkName(String name);
    boolean checkEmail(String email);
    boolean checkDuplication(ClubMemberDTO memberDTO);
    String register(ClubMemberDTO memberDTO);
    ClubMemberDTO getClubMemberSearchbyName(String name);
    ProfilePageResultDTO<Map<String, Object>, Object[]> getClubMemberSearchbyNameAll(ProfilePageRequestDTO profilePageRequestDTO, String name, String userName);
    ClubMemberDTO getClubMemberSearchbyEmail(String Email);
    boolean getUserExist(String name);
    default ClubMember dtoToEntity(ClubMemberDTO dto){

        ClubMember clubMember = ClubMember.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .fromSocial(dto.isFromSocial())
                .roleSet(dto.getRoleSet())
                .build();

        return clubMember;
    }

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

    default ProfileImageDTO entityToDTOByProfileImage(ProfileImage profileImage){
        ProfileImageDTO profileImageDTO = ProfileImageDTO.builder()
                .pfino(profileImage.getPfino())
                .userName(profileImage.getClubMember().getName())
                .imgName(profileImage.getImgName())
                .userEmail(profileImage.getClubMember().getEmail())
                .path(profileImage.getPath())
                .uuid(profileImage.getUuid())
                .build();
        return profileImageDTO;
    }
}
