package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.repository.querydsl.ProfileImageCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long>, ProfileImageCustom {

}
