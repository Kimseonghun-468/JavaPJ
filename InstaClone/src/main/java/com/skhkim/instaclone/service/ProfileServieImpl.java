package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.FriendShip;
import com.skhkim.instaclone.entity.FriendShipStatus;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
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
        profileImageRepository.deleteByUserName(profileImage.getClubMember().getName());
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
            friendNameList.add(((FriendShip) arr[0]).getClubMemberUser().getName());
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
    @Transactional
    public void deleteByName(String name){
        profileImageRepository.deleteByUserName(name);
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
    public ProfilePageResultDTO<Map<String, Object>, Object[]>
    getAcceptFriendListPage(ProfilePageRequestDTO profilePageRequestDTO, String loginName){
        Pageable pageable = profilePageRequestDTO.getPageable();
        Page<Object[]> result = profileImageRepository.getByAcceptListPage(pageable, loginName);

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
                resultMap.put("image", ((ProfileImage) result.get(0)[1]));
            else
                resultMap.put("image", null);
            resultMap.put("name", ((FriendShip) result.get(0)[0]).getClubMemberUser().getName());
        }
        return resultMap;
    }


    @Override
    public Map<String, Object> getAcceptFriendList(String loginName){
        Map<String, Object> profileAndFriendMap = new HashMap<>();
        List<String> friendNameList = new ArrayList<>();
        List<Object[]> result = profileImageRepository.getByAcceptList(loginName);

        result.forEach(arr->{
            friendNameList.add(((FriendShip) arr[0]).getClubMemberUser().getName());
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
