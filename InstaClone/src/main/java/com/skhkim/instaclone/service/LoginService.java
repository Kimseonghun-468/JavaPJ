package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.entity.ClubMember;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface LoginService {

    boolean checkDuplication(ClubMemberDTO memberDTO);
    String register(ClubMemberDTO memberDTO);
    ClubMemberDTO getClubMemberSearch(String name);

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
}
