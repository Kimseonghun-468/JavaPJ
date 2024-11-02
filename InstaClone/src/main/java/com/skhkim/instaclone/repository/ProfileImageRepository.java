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

    @Query("SELECT FS.clubMemberUser as clubMember, PI AS profileImage FROM FriendShip FS " +
            "LEFT JOIN ProfileImage PI On FS.clubMemberUser.name = PI.clubMember.name " +
            "WHERE FS.clubMemberFriend.name = :loginName " +
            "AND FS.status = 0 ")
    Slice<UserInfoProjection> getByWaitingListPage(Pageable pageable, @Param("loginName") String loginName);

    @Query(value = "SELECT FS.clubMemberFriend AS clubMember, PI AS profileImage " +
            "FROM FriendShip FS " +
            "LEFT JOIN ProfileImage PI ON FS.clubMemberFriend.name = PI.clubMember.name " +
            "WHERE FS.status = 1 AND FS.clubMemberUser.name = :loginName " +
            "UNION ALL " +
            "SELECT FS.clubMemberUser AS clubMember, PI AS profileImage " +
            "FROM FriendShip FS " +
            "LEFT JOIN ProfileImage PI ON FS.clubMemberUser.name = PI.clubMember.name " +
            "WHERE FS.status = 1 AND FS.clubMemberFriend.name = :loginName " +
            "ORDER BY clubMember.name")
    Slice<UserInfoProjection> getByAcceptListPage(Pageable pageable, @Param("loginName") String loginName);


    @Query("select FA FROM FriendAccept FA " +
            "WHERE FA.user1.name = :loginName")
    List<Object[]> test(String loginName);

//    @Query("SELECT fs1, pi, fs2.status, fs2.isFrom " +
//            "FROM FriendShip fs1 " +
//            "LEFT JOIN FriendShip fs2 ON (fs1.clubMemberUser.name = fs2.clubMemberUser.name AND fs2.clubMemberFriend.name = :loginName) " +
//            "LEFT JOIN ProfileImage pi ON fs1.clubMemberUser.name = pi.clubMember.name " +
//            "WHERE fs1.clubMemberFriend.name =:userName " +
//            "AND fs1.status = 1 AND fs1.clubMemberUser.name != :loginName " +
//            "ORDER BY fs2.status DESC, fs1.clubMemberUser.name")
//    Page<Object[]> getFriendListPage(Pageable pageable, @Param("userName") String userName, @Param("loginName") String loginName);

    @Query("SELECT FA_USER.user2 AS clubMember, " +
            "CASE WHEN FA_LOGIN.user2 IS NOT NULL THEN 1 " +
            "WHEN FW_RECEVER.requester IS NOT NULL THEN 2 " +
            "WHEN FW_REQUESTER.receiver IS NOT NULL THEN 3 ELSE 4 END AS status " +
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


    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi ON fs.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs.clubMemberFriend.name =:loginName " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT " +
            "AND fs.clubMemberUser.name like CONCAT('%', :inviteSearchTerm, '%') " +
            "AND fs.clubMemberUser.email NOT IN :roomUsers")
    Page<Object[]> getInviteListByNamePage(Pageable pageable, @Param("loginName") String loginName, String inviteSearchTerm, List<String> roomUsers);

    // 이건 초대할 사람 검색하는건데, 이름으로..?

    @Query("SELECT fs1, pi " +
            "FROM FriendShip fs1 " +
            "LEFT JOIN ProfileImage pi ON fs1.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs1.clubMemberFriend.name =:loginName " +
            "AND fs1.status =com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT " +
            "AND fs1.clubMemberUser.name != :loginName " +
            "AND fs1.clubMemberUser.email NOT IN :roomUsers " +
            "ORDER BY fs1.clubMemberUser.name")
    Page<Object[]> getInviteListPage(Pageable pageable, @Param("loginName") String loginName, List<String> roomUsers);


    // 이건 초대 친구들인데, 초대 된 친구

    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi ON fs.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs.clubMemberFriend.name =:userName AND fs.clubMemberUser.name =:loginName")
    List<Object[]> getFriendFirst(String userName, String loginName);

    @Query("SELECT pi FROM ProfileImage pi WHERE pi.clubMember.email =:userEmail")
    Optional<ProfileImage> getProfileImageByUserEmail(String userEmail);

}

