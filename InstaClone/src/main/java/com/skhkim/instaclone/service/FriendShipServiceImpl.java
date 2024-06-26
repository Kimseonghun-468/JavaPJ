package com.skhkim.instaclone.service;

import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendShip;
import com.skhkim.instaclone.entity.FriendShipStatus;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.repository.FriendShipRepository;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Log4j2
public class FriendShipServiceImpl implements FriendShipService{

    private final ClubMemberRepository clubMemberRepository;
    private final FriendShipRepository friendShipRepository;
    @Override
    @Transactional
    public String createFriendShip(String searchName){
        //        로그인한 아이디
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ClubAuthMemberDTO authenticationMember = (ClubAuthMemberDTO) authentication.getPrincipal();
        String loginedUserName = authenticationMember.getName();
//        검색한 아이디
        ClubMember searchedClubMember = clubMemberRepository.findByName(searchName);
        ClubMember loginedClubMember = clubMemberRepository.findByName(loginedUserName);
        // 보내는 요청이랑 받은 요청 모두 필요하니 2개씩 저장
        FriendShip friendShipRequester = FriendShip.builder()
                .clubMemberUser(loginedClubMember)
                .clubMemberFriend(searchedClubMember)
                .status(FriendShipStatus.WAITING)
                .isFrom(true)
                .build();
        // 받는 요청
        FriendShip friendShipAccepter = FriendShip.builder()
                .clubMemberUser(searchedClubMember)
                .clubMemberFriend(loginedClubMember)
                .status(FriendShipStatus.WAITING)
                .isFrom(false)
                .build();

        if(checkDuplication(loginedClubMember.getName(), searchedClubMember.getName())) {
            friendShipRepository.save(friendShipRequester);
            friendShipRepository.save(friendShipAccepter);

            return "친구 요청 성공";
        }
        return "친구 상태이거나 요청중";
    }
    @Override
    public boolean checkDuplication(String requesterName, String accepterName){
        Optional<FriendShip> result = clubMemberRepository.getFriendshipsByName(requesterName, accepterName);
        return result.isEmpty();
    }
    @Override
    public String checkFriendShip(String requesterName, String accepterName){
        if (requesterName==accepterName)
            return "Self";
        Optional<FriendShip> friendShip = clubMemberRepository.getFriendshipsByName(requesterName, accepterName);
        if (friendShip.isEmpty()){
            Optional<FriendShip> friendShipIsNotFrom = clubMemberRepository.getFriendshipsByNameIsNotFrom(requesterName, accepterName);
            if(friendShipIsNotFrom.isPresent() && friendShipIsNotFrom.get().getStatus() == FriendShipStatus.ACCEPT)
                return "ACCEPT";
            return friendShipIsNotFrom.isPresent() ? "WAITACCEPT" : "NOTTING";
        }
        return friendShip.isPresent() ? friendShip.get().getStatus().toString() : "Notting";
    }

    @Override
    @Transactional
    public String acceptFriendShip(String requesterName, String accepterName){
        Optional<FriendShip> accepterFriendShip = clubMemberRepository.getFriendshipsByName(requesterName, accepterName);
        Optional<FriendShip> requesterFriendShip = clubMemberRepository.getFriendshipsByNameIsNotFrom(accepterName, requesterName);

        if (accepterFriendShip.isPresent() && requesterFriendShip.isPresent()) {
            accepterFriendShip.get().acceptFriendshipRequest();
            requesterFriendShip.get().acceptFriendshipRequest();

            friendShipRepository.save(accepterFriendShip.get());
            friendShipRepository.save(requesterFriendShip.get());
            return "친구 추가 성공";
        }
        return "친구 추가 실패 - 친구 리스트가 DB에 없음";
    }

    @Override
    @Transactional
    public String deleteFriendShip(String requestName, String accepterName){
        friendShipRepository.deleteByUserNameAndFriendName(requestName, accepterName);
        friendShipRepository.deleteByUserNameAndFriendName(accepterName, requestName);
        return "삭제";
    }

    @Override
    public Long getFriendNum(String name){
        return friendShipRepository.getFriendShipCount(name, FriendShipStatus.ACCEPT);

    }

}
