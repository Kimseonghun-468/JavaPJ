package com.skhkim.instaclone.service;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ClubAuthMemberDTO authenticationMember = (ClubAuthMemberDTO) authentication.getPrincipal();
        String loginedUserName = authenticationMember.getName();
        ClubMember searchedClubMember = clubMemberRepository.findByName(searchName);
        ClubMember loginedClubMember = clubMemberRepository.findByName(loginedUserName);
        FriendShip friendShipRequest = FriendShip.builder()
                .clubMember(loginedClubMember)
                .userEmail(loginedClubMember.getEmail())
                .friendEmail(searchedClubMember.getEmail())
                .userName(loginedClubMember.getName())
                .friendName(searchedClubMember.getName())
                .status(FriendShipStatus.WAITING)
                .build();

        if(checkDuplication(loginedClubMember.getEmail(), searchedClubMember.getEmail())) {
            friendShipRepository.save(friendShipRequest);

            return "친구 요청 성공";
        }
        return "친구 상태이거나 요청중";
    }
    @Override
    public boolean checkDuplication(String userEmail, String friendEmail){
        Optional<FriendShip> result = friendShipRepository.findByUserEmailAndFriendEmail(userEmail, friendEmail);
        Optional<FriendShip> result2 = friendShipRepository.findByUserEmailAndFriendEmail(friendEmail, userEmail);

        return (result.isEmpty() && result2.isEmpty());
    }
    @Override
    public List<FriendShipDTO> getFriendShipListStatusWaiting(String email){

        List<FriendShip> friendShip = friendShipRepository.findByUserEmailStatusWaiting(email);
        List<FriendShipDTO> friendShipDTO = friendShip.stream().map
                (friendShipList -> entityToDto(friendShipList)).collect(Collectors.toList());
        return friendShipDTO;
    }
    @Override
    public String checkFriendShip(String loginEmail, String friendEmail){
        if (loginEmail == friendEmail)
            return "Self";
        Optional<FriendShip> friendShip1 = friendShipRepository.findByUserEmailAndFriendEmail(loginEmail, friendEmail);
        Optional<FriendShip> friendShip2 = friendShipRepository.findByUserEmailAndFriendEmail(friendEmail, loginEmail);

        if (friendShip1.isPresent())
            return friendShip1.get().getStatus().toString();
        else if(friendShip2.isPresent())
            return friendShip2.get().getStatus().toString();
        return "NOTTING";
    }

    @Override
    public String acceptFriendShip(String requesterEmail, String accepterEmail){
        Optional<FriendShip> friendShip = friendShipRepository.findByUserEmailAndFriendEmail(requesterEmail, accepterEmail);
        if (friendShip.isPresent() ) {
            friendShip.get().acceptFriendshipRequest();
            friendShipRepository.save(friendShip.get());
            return "친구 추가 성공";
        }
        return "친구 추가 실패 - 친구 리스트가 없음";
    }

}
