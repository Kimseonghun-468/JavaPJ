package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.FriendShipDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendShip;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

public interface FriendShipService {

    String createFriendShip(String searchName);
    boolean checkDuplication(String requesterEmail, String accepterEmail);
    List<FriendShipDTO> getFriendShipList(String eamil);
    List<FriendShipDTO> getFriendShipListStatusWaiting(String eamil);
    String checkFriendShip(String requesterEmail, String accepterEmail);
    String acceptFriendShip(String requesterEmail, String accepterEmail);
    String deleteFriendShip(String requestEmail, String accepterEmail);
    default FriendShip dtoToEntity(FriendShipDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        FriendShip friendShip = FriendShip.builder()
                .id(dto.getId())
                .clubMember(ClubMember.builder().email(authentication.getName()).build())
                .userEmail(dto.getUserEmail())
                .friendEmail(dto.getFriendEmail())
                .userName(dto.getUserName())
                .friendName(dto.getFriendName())
                .status(dto.getStatus())
                .isFrom(dto.isFrom())
                .counterpartId(dto.getCounterpartId())
                .build();


        return friendShip;
    }

    default FriendShipDTO entityToDto(FriendShip friendShip){

        FriendShipDTO friendShipDTO = FriendShipDTO.builder()
                .id(friendShip.getId())
                .userEmail(friendShip.getUserEmail())
                .userName(friendShip.getUserName())
                .friendEmail(friendShip.getFriendEmail())
                .friendName(friendShip.getFriendName())
                .status(friendShip.getStatus())
                .isFrom(friendShip.isFrom())
                .counterpartId(friendShip.getCounterpartId())
                .build();
        return friendShipDTO;
    }
}
