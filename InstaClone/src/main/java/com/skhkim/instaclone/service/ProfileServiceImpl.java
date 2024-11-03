package com.skhkim.instaclone.service;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendAccept;
import com.skhkim.instaclone.entity.FriendWait;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.entity.type.FriendStatus;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import com.skhkim.instaclone.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
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
    private final ClubMemberRepository memberRepository;
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
    public UserInfoResponse
    getWaitingFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        Pageable pageable = profilePageRequestDTO.getPageable();

        Slice<UserInfoProjection> result = profileImageRepository.getByWaitingListPage(pageable, loginName);

        List<UserInfoDTO> userInfoDTOS = result.stream()
                .map(projection -> UserInfoDTO.builder()
                        .imgName(projection.getProfileImage() != null ? projection.getProfileImage().getImgName() : null)
                        .uuid(projection.getProfileImage() != null ? projection.getProfileImage().getUuid() : null)
                        .path(projection.getProfileImage() != null ? projection.getProfileImage().getPath() : null)
                        .userName(projection.getClubMember().getName())
                        .userEmail(projection.getClubMember().getEmail())
                        .build())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userInfoDTOS, result.hasNext());
        return response;
    }

    @Override
    public UserInfoResponse
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
                        .build())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userInfoDTOS, result.hasNext());

        return response;
    }

    @Override
    public UserInfoResponse getInviteSearchListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName, String inviteSearchTerm, List<String> roomUsers){
        Pageable pageable = profilePageRequestDTO.getPageable();

        Slice<UserInfoProjection> result = profileImageRepository.selectInviteListByName(pageable, loginName, inviteSearchTerm, roomUsers);

        List<UserInfoDTO> userInfoDTOS = result.stream()
                .map(projection -> UserInfoDTO.builder()
                        .imgName(projection.getProfileImage() != null ? projection.getProfileImage().getImgName() : null)
                        .uuid(projection.getProfileImage() != null ? projection.getProfileImage().getUuid() : null)
                        .path(projection.getProfileImage() != null ? projection.getProfileImage().getPath() : null)
                        .userName(projection.getClubMember().getName())
                        .userEmail(projection.getClubMember().getEmail())
                        .build())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userInfoDTOS, result.hasNext());

        return response;
    }

    @Override
    public UserInfoResponse
    getFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String userName, String loginName){
        Pageable pageable = profilePageRequestDTO.getPageable();
        Slice<UserInfoProjection> result = profileImageRepository.getFriendListPage(pageable, userName, loginName);
        List<UserInfoDTO> userInfoDTOS = result.stream()
                .map(projection -> UserInfoDTO.builder()
                        .imgName(projection.getClubMember().getProfileImage() != null ? projection.getClubMember().getProfileImage().getImgName() : null)
                        .uuid(projection.getClubMember().getProfileImage() != null ? projection.getClubMember().getProfileImage().getUuid() : null)
                        .path(projection.getClubMember().getProfileImage() != null ? projection.getClubMember().getProfileImage().getPath() : null)
                        .userName(projection.getClubMember().getName())
                        .userEmail(projection.getClubMember().getEmail())
                        .status(projection.getStatus())
                        .build())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userInfoDTOS, result.hasNext());

        return response;
    }

    @Override
    public UserInfoResponse
    getInviteListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName, List<String> roomUsers){

        Pageable pageable = profilePageRequestDTO.getPageable();
        Slice<UserInfoProjection> result = profileImageRepository.selectInviteList(pageable, loginName, roomUsers);

        List<UserInfoDTO> userInfoDTOS = result.stream()
                .map(projection -> UserInfoDTO.builder()
                        .imgName(projection.getProfileImage() != null ? projection.getProfileImage().getImgName() : null)
                        .uuid(projection.getProfileImage() != null ? projection.getProfileImage().getUuid() : null)
                        .path(projection.getProfileImage() != null ? projection.getProfileImage().getPath() : null)
                        .userName(projection.getClubMember().getName())
                        .userEmail(projection.getClubMember().getEmail())
                        .build())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userInfoDTOS, result.hasNext());

        return response;
    }
    @Override
    public UserInfoDTO getFirstUser(String loginName, String userName){

        ClubMember loginMember = memberRepository.findByName(loginName);
        ProfileImage profileImage = loginMember.getProfileImage();

        // Default로 Status는 NONE 처리
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .userName(loginName)
                .uuid(profileImage != null ? profileImage.getUuid() : null)
                .path(profileImage != null ? profileImage.getPath() : null)
                .imgName(profileImage != null ? profileImage.getImgName() : null)
                .status(FriendStatus.NONE)
                .build();

        Optional<FriendWait> friendWait = memberRepository.getWaitByName(loginName, userName);
        Optional<FriendAccept> friendAccept = memberRepository.getAcceptFriend(loginName, userName);

        // Wait Requester, Receiver 처리
        friendWait.ifPresent(wait -> userInfoDTO.setStatus(wait.getRequester().getName().equals(loginName)
                ? FriendStatus.REQUESTER : FriendStatus.RECEIVER));

        // Accept 처리
        friendAccept.ifPresent(accept -> userInfoDTO.setStatus(FriendStatus.ACCEPTED));


        return userInfoDTO;
    }
}
