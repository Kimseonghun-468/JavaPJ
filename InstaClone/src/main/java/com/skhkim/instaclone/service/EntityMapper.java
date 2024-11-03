package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.dto.UserInfoProjection;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.response.UserInfoResponse;
import lombok.Data;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Data
class EntityMapper {
    static UserInfoDTO entityToDTO(UserInfoProjection projection){
        ProfileImage profileImage = projection.getClubMember().getProfileImage();

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .imgName(profileImage != null ? profileImage.getImgName() : null)
                .uuid(profileImage != null ? profileImage.getUuid() : null)
                .path(profileImage != null ? profileImage.getPath() : null)
                .userName(projection.getClubMember().getName())
                .userEmail(projection.getClubMember().getEmail())
                .status(projection.getStatus())
                .build();


        return userInfoDTO;
    }

    static UserInfoDTO entityToDTO(ClubMember clubMember){
        ProfileImage profileImage = clubMember.getProfileImage();

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .imgName(profileImage != null ? profileImage.getImgName() : null)
                .uuid(profileImage != null ? profileImage.getUuid() : null)
                .path(profileImage != null ? profileImage.getPath() : null)
                .userName(clubMember.getName())
                .userEmail(clubMember.getEmail())
                .build();


        return userInfoDTO;
    }
}

