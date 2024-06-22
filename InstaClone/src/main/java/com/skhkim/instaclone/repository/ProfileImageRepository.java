package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.dto.FriendShipProfileDTO;
import com.skhkim.instaclone.entity.ProfileImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi On fs.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs.clubMemberFriend.name =:loginName " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.WAITING " +
            "AND fs.isFrom = true")
    List<Object[]> getByWaitingList(@Param("loginName") String loginName);

    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi ON fs.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs.clubMemberFriend.name =:loginName " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT")
    List<Object[]> getByAcceptList(@Param("loginName") String loginName);

    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi On fs.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs.clubMemberFriend.name =:loginName " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.WAITING " +
            "AND fs.isFrom = true")
    Page<Object[]> getByWaitingListPage(Pageable pageable, @Param("loginName") String loginName);

    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi ON fs.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs.clubMemberFriend.name =:loginName " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT")
    Page<Object[]> getByAcceptListPage(Pageable pageable, @Param("loginName") String loginName);


    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi ON fs.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs.clubMemberFriend.name =:loginName " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT " +
            "AND fs.clubMemberUser.name like CONCAT('%', :inviteSearchTerm, '%') " +
            "AND fs.clubMemberUser.email NOT IN :roomUsers")
    Page<Object[]> getInviteListByNamePage(Pageable pageable, @Param("loginName") String loginName, String inviteSearchTerm, List<String> roomUsers);


    @Query("SELECT fs1, pi, fs2.status, fs2.isFrom " +
            "FROM FriendShip fs1 " +
            "LEFT JOIN FriendShip fs2 ON (fs1.clubMemberUser.name = fs2.clubMemberUser.name AND fs2.clubMemberFriend.name = :loginName) " +
            "LEFT JOIN ProfileImage pi ON fs1.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs1.clubMemberFriend.name =:userName " +
            "AND fs1.status =com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT AND fs1.clubMemberUser.name != :loginName " +
            "ORDER BY fs2.status DESC, fs1.clubMemberUser.name")
    Page<Object[]> getFriendListPage(Pageable pageable, @Param("userName") String userName, @Param("loginName") String loginName);

    @Query("SELECT fs1, pi " +
            "FROM FriendShip fs1 " +
            "LEFT JOIN ProfileImage pi ON fs1.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs1.clubMemberFriend.name =:loginName " +
            "AND fs1.status =com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT AND fs1.clubMemberUser.name != :loginName " +
            "AND fs1.clubMemberUser.email NOT IN :roomUsers " +
            "ORDER BY fs1.clubMemberUser.name")
    Page<Object[]> getInviteListPage(Pageable pageable, @Param("loginName") String loginName, List<String> roomUsers);

    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi ON fs.clubMemberUser.name = pi.clubMember.name " +
            "WHERE fs.clubMemberFriend.name =:userName AND fs.clubMemberUser.name =:loginName")
    List<Object[]> getFriendFirst(String userName, String loginName);

}

