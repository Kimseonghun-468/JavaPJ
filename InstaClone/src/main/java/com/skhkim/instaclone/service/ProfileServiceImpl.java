package com.skhkim.instaclone.service;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendAccept;
import com.skhkim.instaclone.entity.FriendWait;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.entity.type.FriendStatus;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProfileServiceImpl implements ProfileService{

    private final ProfileImageRepository profileImageRepository;
    private final ClubMemberRepository memberRepository;
    @Value("${instaclone.upload.path")
    private String uploadPath;

    @Override
    @Transactional
    public void register(ProfileImageDTO profileImageDTO){

        ProfileImage profileImage = EntityMapper.dtoToEntity(profileImageDTO);
        ProfileImage beforeImage = profileImageRepository.findByUserEmail(profileImage.getClubMember().getEmail());
        profileImageRepository.deleteByUserEmail(profileImage.getClubMember().getEmail());
        deleteImage(beforeImage);
        profileImageRepository.save(profileImage);
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
            return EntityMapper.entityToDTO(profileImage);
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
    getWaitingFriendListPage(UserInfoPageRequest userInfoPageRequest){

        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<UserInfoProjection> result = profileImageRepository.getByWaitingListPage(pageable, LoginContext.getUserInfo().getUserName());
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());
    }

    @Override
    public UserInfoResponse
    getAcceptFriendListPage(UserInfoPageRequest userInfoPageRequest){
        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<UserInfoProjection> result = profileImageRepository.getByAcceptListPage(pageable, LoginContext.getUserInfo().getUserName());
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());
    }

    @Override
    public UserInfoResponse getInviteSearchListPage(UserInfoPageRequest userInfoPageRequest, String loginName, String inviteSearchTerm, List<String> roomUsers){
        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<UserInfoProjection> result = profileImageRepository.selectInviteListByName(pageable, loginName, inviteSearchTerm, roomUsers);
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());
    }

    @Override
    public UserInfoResponse
    getFriendListPage(UserInfoPageRequest userInfoPageRequest, String userName){
        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<UserInfoProjection> result = profileImageRepository.getFriendListPage(pageable, userName, LoginContext.getUserInfo().getUserName());
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());
    }

    @Override
    public UserInfoResponse
    getInviteListPage(UserInfoPageRequest userInfoPageRequest, List<String> roomUsers){
        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<UserInfoProjection> result = profileImageRepository.selectInviteList(pageable, LoginContext.getUserInfo().getUserName(), roomUsers);
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());
    }
    @Override
    public UserInfoDTO getFirstUser(String userName){

        String loginName = LoginContext.getUserInfo().getUserName();
        ClubMember loginMember = memberRepository.findByName(loginName);
        UserInfoDTO userInfoDTO = EntityMapper.entityToDTO(loginMember);

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
