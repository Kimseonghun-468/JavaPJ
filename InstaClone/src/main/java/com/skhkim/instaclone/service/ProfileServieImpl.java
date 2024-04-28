package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.PostImage;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProfileServieImpl implements ProfileService{

    private final ProfileImageRepository profileImageRepository;

    @Override
    @Transactional
    public Long register(ProfileImageDTO profileImageDTO){

        ProfileImage profileImage = dtoToEntity(profileImageDTO);
        profileImageRepository.save(profileImage);

        return profileImage.getPfino();
    }
    @Override
    public ProfileImageDTO getProfileImage(String name){

        ProfileImage profileImage = profileImageRepository.findByUserName(name);
        if(profileImage != null){
            return entityToDTO(profileImage);
        }
        else{
            return ProfileImageDTO.builder().build();
        }
    }

    @Override
    public List<FriendShipProfileDTO> getProfileImageWaitingList(String loginEmail){
        List<FriendShipProfileDTO> friendShipProfileDTOS = profileImageRepository.findByExistWaitingProfile(loginEmail);
        List<FriendShipProfileDTO> friendShipProfileDTOSNotIn = profileImageRepository.findByNotExistWaitingProfile(loginEmail);
        friendShipProfileDTOS.addAll(friendShipProfileDTOSNotIn);
        return friendShipProfileDTOS;
    }

    @Override
    public List<FriendShipProfileDTO> getProfileImageAcceptedList(String loginEmail){
        List<FriendShipProfileDTO> friendShipProfileDTOS = profileImageRepository.findByExistAcceptedProfile(loginEmail);
        List<FriendShipProfileDTO> friendShipProfileDTOSNotIn = profileImageRepository.findByNotExistAcceptedProfile(loginEmail);
        friendShipProfileDTOS.addAll(friendShipProfileDTOSNotIn);
        return friendShipProfileDTOS;
    }

}
