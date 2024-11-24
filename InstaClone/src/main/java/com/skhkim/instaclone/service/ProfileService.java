package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ProfileImageDTO;

public interface ProfileService {
    void register(ProfileImageDTO profileImageDTO);
    void deleteByName(String name);


}
