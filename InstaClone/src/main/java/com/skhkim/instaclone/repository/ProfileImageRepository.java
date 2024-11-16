package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.dto.UserInfoProjection;
import com.skhkim.instaclone.entity.ProfileImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    @Query("SELECT pi FROM ProfileImage pi WHERE pi.clubMember.name = :userName ORDER BY pi.modDate DESC LIMIT 1")
    ProfileImage findByUserName(@Param("userName") String userName);

    @Query("SELECT pi FROM ProfileImage pi where pi.clubMember.email = :userEmail")
    ProfileImage findByUserEmail(String userEmail);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProfileImage pi WHERE pi.clubMember.email =:userEmail")
    void deleteByUserEmail(String userEmail);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProfileImage pi WHERE pi.clubMember.name =:userName")
    void deleteByUserName(String userName);

    @Query("SELECT FW.requester as clubMember FROM FriendWait FW " +
            "WHERE FW.receiver.name = :loginName " +
            "ORDER BY FW.requester.name ")
    Slice<UserInfoProjection> getByWaitingListPage(Pageable pageable, @Param("loginName") String loginName);

    @Query("SELECT FA.user2 AS clubMember FROM FriendAccept FA " +
            "WHERE FA.user1.name =:loginName " +
            "ORDER BY FA.user2.name")
    Slice<UserInfoProjection> getByAcceptListPage(Pageable pageable, @Param("loginName") String loginName);

    @Query("SELECT FA_USER.user2 AS clubMember, " +
            "CASE WHEN FA_LOGIN.user2 IS NOT NULL THEN com.skhkim.instaclone.entity.type.FriendStatus.ACCEPTED " +
            "WHEN FW_RECEVER.requester IS NOT NULL THEN com.skhkim.instaclone.entity.type.FriendStatus.RECEIVER " +
            "WHEN FW_REQUESTER.receiver IS NOT NULL THEN com.skhkim.instaclone.entity.type.FriendStatus.REQUESTER " +
            "ELSE com.skhkim.instaclone.entity.type.FriendStatus.NONE END AS status " +
            "FROM FriendAccept FA_USER " +
            "LEFT JOIN FriendAccept FA_LOGIN ON " +
            "FA_USER.user2 = FA_LOGIN.user2 AND FA_LOGIN.user1.name = :loginName " +
            "LEFT JOIN FriendWait FW_RECEVER ON " +
            "FW_RECEVER.requester = FA_USER.user2 AND FW_RECEVER.receiver.name = :loginName " +
            "LEFT JOIN FriendWait FW_REQUESTER ON " +
            "FW_REQUESTER.receiver = FA_USER.user2 AND FW_REQUESTER.requester.name = :loginName "+
            "WHERE FA_USER.user1.name = :userName " +
            "ORDER BY FA_LOGIN.user2.name DESC, FW_RECEVER.requester.name DESC, FW_REQUESTER.receiver.name DESC")
    Slice<UserInfoProjection> getFriendListPage(Pageable pageable, String userName, String loginName);

    @Query("SELECT FA.user2 AS clubMember FROM FriendAccept FA " +
            "WHERE FA.user1.name =:loginName " +
            "AND FA.user2.name like CONCAT('%', :inviteSearchTerm, '%') " +
            "AND FA.user2.name NOT IN :roomUsers " +
            "ORDER BY FA.user2.name")
    Slice<UserInfoProjection> selectInviteListByName(Pageable pageable, String loginName, String inviteSearchTerm, List<String> roomUsers);

    @Query("SELECT FA.user2 AS clubMember FROM FriendAccept FA " +
            "WHERE FA.user1.name =:loginName " +
            "AND FA.user2.name NOT IN :roomUsers " +
            "ORDER BY FA.user2.name")
    Slice<UserInfoProjection> selectInviteList(Pageable pageable, String loginName, List<String> roomUsers);
}

