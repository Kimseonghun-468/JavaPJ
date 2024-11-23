package com.skhkim.instaclone.service;

import com.skhkim.instaclone.entity.type.FriendStatus;

public interface FriendShipService {

    boolean createFriendShip(String userName);
    boolean checkDuplication(String loginName, String userName);
    FriendStatus checkFriendShip(String loginName, String userName);
    boolean acceptFriendShip(String loginName, String userName);
    boolean deleteFriendShip(String loginName, String userName);

    int getFriendNum(String name);
}
