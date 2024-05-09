package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.dto.FriendShipProfileDTO;
import com.skhkim.instaclone.entity.ProfileImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    @Query("SELECT pi FROM ProfileImage pi WHERE pi.userName = :userName ORDER BY pi.modDate DESC LIMIT 1")
    ProfileImage findByUserName(@Param("userName") String userName);

    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi On fs.userName = pi.userName " +
            "WHERE fs.friendName =:loginName " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.WAITING " +
            "AND fs.isFrom = true")
    List<Object[]> getByWaitingList(@Param("loginName") String loginName);

    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi ON fs.userName = pi.userName " +
            "WHERE fs.friendName =:loginName " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT")
    List<Object[]> getByAcceptList(@Param("loginName") String loginName);

    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi On fs.userName = pi.userName " +
            "WHERE fs.friendName =:loginName " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.WAITING " +
            "AND fs.isFrom = true")
    Page<Object[]> getByWaitingListPage(Pageable pageable, @Param("loginName") String loginName);

    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi ON fs.userName = pi.userName " +
            "WHERE fs.friendName =:loginName " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT")
    Page<Object[]> getByAcceptListPage(Pageable pageable, @Param("loginName") String loginName);

    @Query("SELECT fs1, pi, fs2 " +
            "FROM FriendShip fs1 " +
            "LEFT JOIN FriendShip fs2 ON (fs1.userName = fs2.userName AND fs2.friendName = :loginName) " +
            "OR (fs1.userName = :loginName AND fs2.userName = :userName AND fs2.friendName = :loginName) " +
            "LEFT JOIN ProfileImage pi ON fs1.userName = pi.userName " +
            "WHERE fs1.friendName =:userName " +
            "AND fs1.status =com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT AND fs1.userName != :loginName " +
            "ORDER BY fs2.status")
    Page<Object[]> getFriendListPage(Pageable pageable, @Param("userName") String userName, @Param("loginName") String loginName);

    @Query("SELECT fs, pi FROM FriendShip fs LEFT JOIN ProfileImage pi ON fs.userName = pi.userName " +
            "WHERE fs.friendName =:userName AND fs.userName =:loginName")
    List<Object[]> getFriendFirst(String userName, String loginName);

}

