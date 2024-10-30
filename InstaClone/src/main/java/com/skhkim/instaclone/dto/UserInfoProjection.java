package com.skhkim.instaclone.dto;

import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.ProfileImage;

public interface UserInfoProjection {
    ClubMember getClubMember();
    ProfileImage getProfileImage();

}
