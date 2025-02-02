package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.FriendWait;
import com.skhkim.instaclone.repository.querydsl.FriendWaitCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendWaitRepository extends JpaRepository<FriendWait, Long>, FriendWaitCustom {

}
