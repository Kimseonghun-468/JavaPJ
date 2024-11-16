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
}
