package com.skhkim.instaclone.repository.querydsl;

import com.skhkim.instaclone.dto.UserInfoProjection;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendAccept;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface FriendAcceptCustom {
    int delete(String loginName, String userName);
    int getCount(String userName);
    Slice<ClubMember> getByAcceptListPage(Pageable pageable, String loginName);

    Slice<UserInfoProjection> getFriendListPage(Pageable pageable, String userName, String loginName);

    Slice<ClubMember> selectInviteListByName(Pageable pageable,
                                                     String loginName,
                                                     String inviteSearchTerm,
                                                     List<String> roomUsers);
    Slice<ClubMember> selectInviteList(Pageable pageable,
                                       String loginName,
                                       List<String> roomUsers);

    Optional<FriendAccept> getAcceptFriend(String loginName, String userName);
}
