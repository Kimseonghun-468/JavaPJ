package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.ProfileImage;

public interface ProfileImageCustom {
    ProfileImage select(Long userId);

    void delete(Long userId);

}
