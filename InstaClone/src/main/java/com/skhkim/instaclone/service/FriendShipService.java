package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.FriendShipDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.type.FriendStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface FriendShipService {

    boolean createFriendShip(String userName);
    boolean checkDuplication(String loginName, String userName);
    FriendStatus checkFriendShip(String loginName, String userName);
    boolean acceptFriendShip(String loginName, String userName);
    boolean deleteFriendShip(String loginName, String userName);

    int getFriendNum(String name);
//    default FriendShip dtoToEntity(FriendShipDTO dto){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        FriendShip friendShip = FriendShip.builder()
//                .id(dto.getId())
//                .clubMemberUser(ClubMember.builder().email(authentication.getName()).build())
//                .clubMemberFriend(ClubMember.builder().email(dto.getFriendEmail()).build())
//                .status(dto.getStatus())
//                .isFrom(dto.isFrom())
//                .build();
//
//
//        return friendShip;
//    }

//    default FriendShipDTO entityToDto(FriendShip friendShip){
//
//        FriendShipDTO friendShipDTO = FriendShipDTO.builder()
//                .id(friendShip.getId())
//                .userEmail(friendShip.getClubMemberUser().getEmail())
//                .userName(friendShip.getClubMemberUser().getName())
//                .friendEmail(friendShip.getClubMemberFriend().getEmail())
//                .friendName(friendShip.getClubMemberFriend().getName())
//                .status(friendShip.getStatus())
//                .isFrom(friendShip.isFrom())
//                .build();
//        return friendShipDTO;
//    }
}
