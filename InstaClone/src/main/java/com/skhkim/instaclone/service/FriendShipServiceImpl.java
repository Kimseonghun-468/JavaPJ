package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.dto.FriendShipDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class FriendShipServiceImpl implements FriendShipService{

    private final ClubMemberRepository clubMemberRepository;
    private final FriendShipRepository friendShipRepository;
    @Override
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
                .clubMember(loginedClubMember)
                .userEmail(loginedClubMember.getEmail())
                .friendEmail(searchedClubMember.getEmail())
                .userName(loginedClubMember.getName())
                .friendName(searchedClubMember.getName())
                .status(FriendShipStatus.WAITING)
                .isFrom(true)
                .build();
        // 받는 요청
        FriendShip friendShipAccepter = FriendShip.builder()
                .clubMember(searchedClubMember)
                .userEmail(searchedClubMember.getEmail())
                .userName(searchedClubMember.getName())
                .friendEmail(loginedClubMember.getEmail())
                .friendName(loginedClubMember.getName())
                .status(FriendShipStatus.WAITING)
                .isFrom(false)
                .build();

        if(checkDuplication(loginedClubMember.getEmail(), searchedClubMember.getEmail())) {
            friendShipRepository.save(friendShipRequester);
            friendShipRepository.save(friendShipAccepter);

            return "친구 요청 성공";
        }
        return "친구 상태이거나 요청중";
    }
    @Override
    public boolean checkDuplication(String requesterEmail, String accepterEmail){
        Optional<FriendShip> result = clubMemberRepository.findFriendshipsByEmail(requesterEmail, accepterEmail);
        return result.isEmpty();
    }

    @Override
    public List<FriendShipDTO> getFriendShipList(String eamil){

        Optional<ClubMember> clubMember = clubMemberRepository.findByEmail(eamil);
        List<FriendShip> friendShip = clubMember.get().getFriendshipList();
        List<FriendShipDTO> friendShipDTO = friendShip.stream().map
                (friendShipList -> entityToDto(friendShipList)).collect(Collectors.toList());
        return friendShipDTO;
    }

    @Override
    public List<FriendShipDTO> getFriendShipListStatusWaiting(String email){

        List<FriendShip> friendShip = clubMemberRepository.findByEmailStatusWaiting(email);
        List<FriendShipDTO> friendShipDTO = friendShip.stream().map
                (friendShipList -> entityToDto(friendShipList)).collect(Collectors.toList());
        return friendShipDTO;
    }

    @Override
    public String checkFriendShip(String requesterEmail, String accepterEmail){
        if (requesterEmail==accepterEmail)
            return "Self";
        Optional<FriendShip> friendShip = clubMemberRepository.findFriendshipsByEmail(requesterEmail, accepterEmail);
        if (friendShip.isEmpty()){
            Optional<FriendShip> friendShipIsNotFrom = clubMemberRepository.findFriendshipsByEmailIsNotFrom(requesterEmail, accepterEmail);
            if(friendShipIsNotFrom.isPresent() && friendShipIsNotFrom.get().getStatus() == FriendShipStatus.ACCEPT)
                return "ACCEPT";
            return friendShipIsNotFrom.isPresent() ? "WAITACCEPT" : "NOTTING";
        }
        return friendShip.isPresent() ? friendShip.get().getStatus().toString() : "Notting";
    }

    @Override
    public String acceptFriendShip(String requesterEmail, String accepterEmail){
        Optional<FriendShip> accepterFriendShip = clubMemberRepository.findFriendshipsByEmail(requesterEmail, accepterEmail);
        Optional<FriendShip> requesterFriendShip = clubMemberRepository.findFriendshipsByEmailIsNotFrom(accepterEmail, requesterEmail);

        if (accepterFriendShip.isPresent() && requesterFriendShip.isPresent()) {
            accepterFriendShip.get().acceptFriendshipRequest();
            requesterFriendShip.get().acceptFriendshipRequest();

            friendShipRepository.save(accepterFriendShip.get());
            friendShipRepository.save(requesterFriendShip.get());
            return "친구 추가 성공";
        }
        return "친구 추가 실패 - 친구 리스트가 없음";
    }

    @Override
    public String deleteFriendShip(String requestEmail, String accepterEmail){
        friendShipRepository.deleteByUserEmailAndFriendEmail(requestEmail, accepterEmail);
        friendShipRepository.deleteByUserEmailAndFriendEmail(accepterEmail, requestEmail);
        return "삭제";
    }

}
