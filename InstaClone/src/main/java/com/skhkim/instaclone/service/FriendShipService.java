package com.skhkim.instaclone.service;

import com.skhkim.instaclone.entity.type.FriendStatus;

public interface FriendShipService {

    boolean createFriendShip(String userName);
    boolean checkDuplication(String loginName, String userName);
    FriendStatus checkFriendShip(String loginName, String userName);
    boolean acceptFriendShip(String userName);
    boolean deleteFriendShip(String userName);

    int selectFriendNum(String userName);
}
