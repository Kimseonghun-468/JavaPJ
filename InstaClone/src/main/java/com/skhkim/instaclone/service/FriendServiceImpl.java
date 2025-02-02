package com.skhkim.instaclone.service;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.dto.UserInfoProjection;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendAccept;
import com.skhkim.instaclone.entity.FriendWait;
import com.skhkim.instaclone.entity.type.FriendStatus;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.repository.FriendAcceptRepository;
import com.skhkim.instaclone.repository.FriendWaitRepository;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Log4j2
public class FriendServiceImpl implements FriendService {

    private final ClubMemberRepository memberRepository;
    private final FriendWaitRepository waitRepository;
    private final FriendAcceptRepository acceptRepository;
    @Override
    @Transactional
    public boolean createFriendShip(String userName){
        String loginName = LoginContext.getClubMember().getName();

        ClubMember loginMember = memberRepository.findByName(loginName);
        ClubMember userMember = memberRepository.findByName(userName);

        FriendWait friendWait = FriendWait.builder()
                .requester(loginMember)
                .receiver(userMember)
                .build();

        if(checkDuplication(loginName, userName)) {
            waitRepository.save(friendWait);
            return true;
        }
        else return false;

    }

    @Override
    /* FriendWait Duplication Check */
    public boolean checkDuplication(String loginName, String userName){
        Optional<FriendWait> resultWait = waitRepository.selectByName(loginName, userName);
        Optional<FriendAccept> resultAccept = acceptRepository.getAcceptFriend(loginName, userName);
        return (resultWait.isEmpty() && resultAccept.isEmpty());
    }
    @Override
    public FriendStatus checkFriendShip(String loginName, String userName) {

        if (loginName.equals(userName))
            return FriendStatus.SELF;

        Optional<FriendWait> friendWait = waitRepository.selectByName(loginName, userName);
        Optional<FriendAccept> friendAccept = acceptRepository.getAcceptFriend(loginName, userName);

        if(friendWait.isPresent()){
            if (friendWait.get().getRequester().getName().equals(loginName))
                return FriendStatus.REQUESTER;
            else if(friendWait.get().getReceiver().getName().equals(loginName))
                return FriendStatus.RECEIVER;
        }

        if (friendAccept.isPresent())
            return FriendStatus.ACCEPTED;

        return FriendStatus.NONE;
    }

    @Override
    @Transactional
    public boolean acceptFriendShip(String userName){

        String loginName = LoginContext.getClubMember().getName();

        Optional<FriendWait> friendWait = waitRepository.selectByName(loginName, userName);

        if(friendWait.isPresent()){
            int waitCount = waitRepository.delete(loginName, userName);

            if (waitCount == 1) {
                FriendAccept friendAccept = FriendAccept.builder()
                        .user1(friendWait.get().getReceiver())
                        .user2(friendWait.get().getRequester())
                        .build();
                FriendAccept friendAcceptCounter = FriendAccept.builder()
                        .user1(friendWait.get().getRequester())
                        .user2(friendWait.get().getReceiver())
                        .build();
                acceptRepository.save(friendAccept);
                acceptRepository.save(friendAcceptCounter);

                // Wait Record 조회 - 삭제 - accept Record 삽입시 성공
                return true;
            }
            // Delete Count != 1인 경우 Error
            else return false;
        }
        // Wait Table에 Record가 없는 경우 Error
        else return false;
    }

    @Override
    @Transactional
    public boolean deleteFriendShip(String userName){
        String loginName = LoginContext.getClubMember().getName();
        int result = acceptRepository.delete(loginName, userName);
        return result > 0;
    }

    @Override
    public int selectFriendNum(String userName){
        return acceptRepository.getCount(userName);

    }

    @Override
    public UserInfoResponse
    selectWaitingFriend(UserInfoPageRequest userInfoPageRequest){

        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<ClubMember> result = waitRepository.selectListById(pageable, LoginContext.getClubMember().getUserId());
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());
    }

    @Override
    public UserInfoResponse
    selectAcceptUsersInfo(UserInfoPageRequest userInfoPageRequest){
        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<ClubMember> result = acceptRepository.getByAcceptListPage(pageable, LoginContext.getClubMember().getName());
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());
    }

    @Override
    public UserInfoResponse selectInviteSearchUsers(UserInfoPageRequest userInfoPageRequest, String inviteSearchTerm, List<String> roomUsers){
        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<ClubMember> result = acceptRepository.selectInviteListByName(pageable, LoginContext.getClubMember().getName(), inviteSearchTerm, roomUsers);
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());
    }

    @Override
    public UserInfoResponse
    selectUserFriendsInfo(UserInfoPageRequest userInfoPageRequest, String userName){
        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<UserInfoProjection> result = acceptRepository.getFriendListPage(pageable, userName, LoginContext.getClubMember().getName());
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());
    }

    @Override
    public UserInfoResponse
    selectInviteList(UserInfoPageRequest userInfoPageRequest, List<String> roomUsers){
        Pageable pageable = userInfoPageRequest.getPageable();
        Slice<ClubMember> result = acceptRepository.selectInviteList(pageable, LoginContext.getClubMember().getName(), roomUsers);
        List<UserInfoDTO> userInfoDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new UserInfoResponse(userInfoDTOS, result.hasNext());
    }
    @Override
    public UserInfoDTO selectFristFriend(String userName){

        String loginName = LoginContext.getClubMember().getName();

        ClubMember loginMember = memberRepository.findByName(loginName);
        UserInfoDTO userInfoDTO = EntityMapper.entityToDTO(loginMember);

        // Self, None 처리
        userInfoDTO.setStatus(loginName.equals(userName) ?
                FriendStatus.SELF : FriendStatus.NONE);

        Optional<FriendWait> friendWait = waitRepository.selectByName(loginName, userName);
        Optional<FriendAccept> friendAccept = acceptRepository.getAcceptFriend(loginName, userName);

        // Wait Requester, Receiver 처리
        friendWait.ifPresent(wait -> userInfoDTO.setStatus(wait.getRequester().getName().equals(loginName)
                ? FriendStatus.REQUESTER : FriendStatus.RECEIVER));

        // Accept 처리
        friendAccept.ifPresent(accept -> userInfoDTO.setStatus(FriendStatus.ACCEPTED));


        return userInfoDTO;
    }

}
