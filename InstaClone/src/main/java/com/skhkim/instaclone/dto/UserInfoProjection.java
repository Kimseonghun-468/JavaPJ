package com.skhkim.instaclone.dto;

import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.entity.type.FriendStatus;

public interface UserInfoProjection {
    ClubMember getClubMember();
    ProfileImage getProfileImage();
    FriendStatus getStatus();

}
