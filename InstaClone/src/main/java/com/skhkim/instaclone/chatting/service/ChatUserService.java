package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
import com.skhkim.instaclone.dto.ProfilePageResultDTO;
import com.skhkim.instaclone.entity.ProfileImage;
import java.util.List;
import java.util.Map;

public interface ChatUserService {

    void register(String userEmail, Long roomId);
    void updateDisConnect(Long roomId, String loginEmail);

    List<Object[]> getEmailAndName(Long roomId);

    ProfilePageResultDTO<Map<String, Object>, Object[]> getProfileAndUseByLoginNamePage(ProfilePageRequestDTO profilePageRequestDTO, String loginEmail);

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

