package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.FriendShip;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.PostImage;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
    public Map<String, Object> getWaitingFriendList(String loginName){
        Map<String, Object> profileAndFriendMap = new HashMap<>();
        List<String> friendNameList = new ArrayList<>();
        List<Object[]> result = profileImageRepository.getByWaitingList(loginName);

        result.forEach(arr->{
            friendNameList.add(((FriendShip) arr[0]).getUserName());
        });
        List<ProfileImageDTO> profileImageDTOList = result.stream().map(arr -> {
            if (arr[1] == null)
                return null;
            else
                return entityToDTO((ProfileImage) arr[1]);
        }
        ).collect(Collectors.toList());

        profileAndFriendMap.put("friendNameList", friendNameList);
        profileAndFriendMap.put("profileImageList", profileImageDTOList);
        return profileAndFriendMap;
    }

    @Override
    public Map<String, Object> getAcceptFriendList(String loginName){
        Map<String, Object> profileAndFriendMap = new HashMap<>();
        List<String> friendNameList = new ArrayList<>();
        List<Object[]> result = profileImageRepository.getByAcceptList(loginName);

        result.forEach(arr->{
            friendNameList.add(((FriendShip) arr[0]).getUserName());
        });
        List<ProfileImageDTO> profileImageDTOList = result.stream().map(arr -> {
                    if (arr[1] == null)
                        return null;
                    else
                        return entityToDTO((ProfileImage) arr[1]);
                }
        ).collect(Collectors.toList());
        profileAndFriendMap.put("friendNameList", friendNameList);
        profileAndFriendMap.put("profileImageList", profileImageDTOList);

        return profileAndFriendMap;
    }

}
