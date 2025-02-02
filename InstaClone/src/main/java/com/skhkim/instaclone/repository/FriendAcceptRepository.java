package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.FriendAccept;
import com.skhkim.instaclone.repository.querydsl.FriendAcceptCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendAcceptRepository extends JpaRepository<FriendAccept, Long>, FriendAcceptCustom {

}
