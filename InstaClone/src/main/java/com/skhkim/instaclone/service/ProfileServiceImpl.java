package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.FriendShip;
import com.skhkim.instaclone.entity.FriendShipStatus;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProfileServiceImpl implements ProfileService{

    private final ProfileImageRepository profileImageRepository;
    @Value("/Users/gimseonghun/JavaPJ/InstaClone/data/")
    private String uploadPath;

    @Override
    @Transactional
    public Long register(ProfileImageDTO profileImageDTO){

        ProfileImage profileImage = dtoToEntity(profileImageDTO);
        ProfileImage beforeImage = profileImageRepository.findByUserEmail(profileImage.getClubMember().getEmail());
        profileImageRepository.deleteByUserEmail(profileImage.getClubMember().getEmail());
        deleteImage(beforeImage);
        profileImageRepository.save(profileImage);

        return profileImage.getPfino();
    }

    public void deleteImage(ProfileImage imageEntity){
        if (imageEntity != null) {
            try {
                Path path = Paths.get(uploadPath + imageEntity.getPath() + "/" + imageEntity.getUuid() + "_" + imageEntity.getImgName());
                Path path_s = Paths.get(uploadPath + imageEntity.getPath() + "/s_" + imageEntity.getUuid() + "_" + imageEntity.getImgName());
                Files.deleteIfExists(path);
                Files.deleteIfExists(path_s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    @Transactional
    public void deleteByName(String name){
        ProfileImage beforeImage = profileImageRepository.findByUserName(name);
        profileImageRepository.deleteByUserName(name);
        deleteImage(beforeImage);
    }

    @Override
    public ProfilePageResultDTO<Map<String, Object>, Object[]>
    getWaitingFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        Pageable pageable = profilePageRequestDTO.getPageable();

        Page<Object[]> result = profileImageRepository.getByWaitingListPage(pageable, loginName);

        Function<Object[], Map<String,Object>> fn = (arr ->{
            Map<String, Object> profileAndFriendMap = new HashMap<>();
            profileAndFriendMap.put("friendName",((FriendShip) arr[0]).getClubMemberUser().getName());
            if (arr[1] == null)
                profileAndFriendMap.put("profileImage",null);
            else
                profileAndFriendMap.put("profileImage",entityToDTO((ProfileImage) arr[1]));

            return profileAndFriendMap;
        });

        return new ProfilePageResultDTO<>(result, fn);
    }

    @Override
    public List<UserInfoDTO>
    getAcceptFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        Pageable pageable = profilePageRequestDTO.getPageable();
        Slice<UserInfoProjection> result = profileImageRepository.getByAcceptListPage(pageable, loginName);

        List<UserInfoDTO> userInfoDTOS = result.stream()
                .map(projection -> UserInfoDTO.builder()
                        .imgName(projection.getProfileImage() != null ? projection.getProfileImage().getImgName() : null)
                        .uuid(projection.getProfileImage() != null ? projection.getProfileImage().getUuid() : null)
                        .path(projection.getProfileImage() != null ? projection.getProfileImage().getPath() : null)
                        .userName(projection.getClubMember().getName())
                        .userEmail(projection.getClubMember().getEmail())
                        .hasNext(result.hasNext())
                        .build())
                .collect(Collectors.toList());

        return userInfoDTOS;
    }

    @Override
    public ProfilePageResultDTO<Map<String, Object>, Object[]> getInviteSearchListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName, String inviteSearchTerm, List<String> roomUsers){
        Pageable pageable = profilePageRequestDTO.getPageable();
        Page<Object[]> result = profileImageRepository.getInviteListByNamePage(pageable, loginName, inviteSearchTerm, roomUsers);

        Function<Object[], Map<String,Object>> fn = (arr ->{
            Map<String, Object> profileAndFriendMap = new HashMap<>();
            profileAndFriendMap.put("friendName",((FriendShip) arr[0]).getClubMemberUser().getName());
            profileAndFriendMap.put("friendEmail",((FriendShip) arr[0]).getClubMemberUser().getEmail());
            if (arr[1] == null)
                profileAndFriendMap.put("profileImage",null);
            else
                profileAndFriendMap.put("profileImage",entityToDTO((ProfileImage) arr[1]));

            return profileAndFriendMap;
        });
        return new ProfilePageResultDTO<>(result, fn);
    }

    @Override
    public ProfilePageResultDTO<Map<String, Object>, Object[]>
    getFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String userName, String loginName){
        Pageable pageable = profilePageRequestDTO.getPageable();
        Page<Object[]> result = profileImageRepository.getFriendListPage(pageable, userName, loginName);

        Function<Object[], Map<String,Object>> fn = (arr ->{
            Map<String, Object> profileAndFriendMap = new HashMap<>();
            profileAndFriendMap.put("friendName",((FriendShip) arr[0]).getClubMemberUser().getName());
            profileAndFriendMap.put("friendEmail",((FriendShip) arr[0]).getClubMemberUser().getEmail());

            if (arr[1] == null)
                profileAndFriendMap.put("profileImage",null);
            else
                profileAndFriendMap.put("profileImage",entityToDTO((ProfileImage) arr[1]));

            if (arr[2] == null)
                profileAndFriendMap.put("status", "NOTTING");
            else if (arr[2] == FriendShipStatus.ACCEPT)
                profileAndFriendMap.put("status", "ACCEPT");
            else if (arr[2] == FriendShipStatus.WAITING && (!(boolean) arr[3]))
                profileAndFriendMap.put("status", "WAITING");
            else if (arr[2] == FriendShipStatus.WAITING && ((boolean) arr[3]))
                profileAndFriendMap.put("status", "WAITACCEPT");

            return profileAndFriendMap;
        });
        return new ProfilePageResultDTO<>(result, fn);
    }

    @Override
    public ProfilePageResultDTO<Map<String, Object>, Object[]>
    getInviteListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName, List<String> roomUsers){
        Pageable pageable = profilePageRequestDTO.getPageable();
        Page<Object[]> result = profileImageRepository.getInviteListPage(pageable, loginName, roomUsers);

        Function<Object[], Map<String,Object>> fn = (arr ->{
            Map<String, Object> profileAndFriendMap = new HashMap<>();
            profileAndFriendMap.put("friendName",((FriendShip) arr[0]).getClubMemberUser().getName());
            profileAndFriendMap.put("friendEmail",((FriendShip) arr[0]).getClubMemberUser().getEmail());

            if (arr[1] == null)
                profileAndFriendMap.put("profileImage",null);
            else
                profileAndFriendMap.put("profileImage",entityToDTO((ProfileImage) arr[1]));

            return profileAndFriendMap;
        });
        return new ProfilePageResultDTO<>(result, fn);
    }
    @Override
    public Map<String, Object> getFirstUser(String userName, String loginName){
        List<Object[]> result = profileImageRepository.getFriendFirst(userName, loginName);
        Map<String , Object> resultMap = new HashMap<>();
//        FriendShip friendShip = (FriendShip) result.get(0)[0];

        if (result.size() == 0){
            resultMap.put("name", null);
            resultMap.put("image",null);
        }
        else{
            if (result.get(0)[1] != null)
                resultMap.put("image", ((ProfileImage) result.get(0)[1]).getImageURL());
            else
                resultMap.put("image", null);
            resultMap.put("name", ((FriendShip) result.get(0)[0]).getClubMemberUser().getName());
        }
        return resultMap;
    }
}
