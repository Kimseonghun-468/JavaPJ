package com.skhkim.instaclone.repository.querydsl;

import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendWait;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface FriendWaitCustom {
    int delete(String loginName, String userName);

    Slice<ClubMember> selectListById(Pageable pageable, Long userId);

    Optional<FriendWait> selectByName(String loginName, String userName);
}
