package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.dto.FriendShipProfileDTO;
import com.skhkim.instaclone.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    @Query("SELECT pi FROM ProfileImage pi WHERE pi.userName = :userName ORDER BY pi.modDate DESC LIMIT 1")
    ProfileImage findByUserName(@Param("userName") String userName);

    @Query("SELECT pi FROM ProfileImage pi WHERE pi.userEmail IN :emails")
    List<ProfileImage> findByEmails(@Param("emails") List<String> emails);

    @Query("SELECT pi FROM ProfileImage pi WHERE pi.userEmail NOT IN :emails")
    List<ProfileImage> findByEmailsNotIn(@Param("emails") List<String> emails);

    @Query("SELECT new com.skhkim.instaclone.dto.FriendShipProfileDTO" +
            "(pi.pfino, fs.userName,fs.userEmail, fs.friendEmail, fs.friendName, pi.uuid, pi.imgName, pi.path)" +
            "FROM FriendShip fs" +
            " JOIN ProfileImage pi ON fs.userEmail = pi.userEmail" +
            " WHERE fs.friendEmail =:loginEmail " +
            "AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.WAITING " +
            "AND fs.isFrom = true")
    List<FriendShipProfileDTO> findByExistWaitingProfile(@Param("loginEmail") String loginEmail);
    @Query("SELECT new com.skhkim.instaclone.dto.FriendShipProfileDTO" +
            "(pi.pfino, fs.userName, fs.userEmail, fs.friendEmail, fs.friendName, pi.uuid, pi.imgName, pi.path)" +
            " FROM FriendShip fs"+
            " LEFT JOIN ProfileImage pi ON fs.userEmail = pi.userEmail" +
            " WHERE fs.friendEmail =:loginEmail " +
            "AND pi.pfino is null " +
            "AND fs.status = com.skhkim.instaclone.entity.FriendShipStatus.WAITING " +
            "AND fs.isFrom = true")
    List<FriendShipProfileDTO> findByNotExistWaitingProfile(@Param("loginEmail") String loginEmail);

    @Query("SELECT new com.skhkim.instaclone.dto.FriendShipProfileDTO" +
            "(pi.pfino, fs.userName, fs.userEmail, fs.friendEmail, fs.friendName, pi.uuid, pi.imgName, pi.path)" +
            " FROM FriendShip fs"+
            " LEFT JOIN ProfileImage pi ON fs.userEmail = pi.userEmail" +
            " WHERE fs.friendEmail =:loginEmail " +
            "AND pi.pfino is null " +
            "AND fs.status = com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT " +
            "AND fs.friendEmail =:loginEmail")
    List<FriendShipProfileDTO> findByNotExistAcceptedProfile(@Param("loginEmail") String loginEmail);

    @Query("SELECT new com.skhkim.instaclone.dto.FriendShipProfileDTO" +
            "(pi.pfino, fs.userName, fs.userEmail, fs.friendEmail, fs.friendName, pi.uuid, pi.imgName, pi.path)" +
            " FROM FriendShip fs"+
            " JOIN ProfileImage pi ON fs.userEmail = pi.userEmail" +
            " WHERE fs.friendEmail =:loginEmail " +
            "AND fs.status = com.skhkim.instaclone.entity.FriendShipStatus.ACCEPT")
    List<FriendShipProfileDTO> findByExistAcceptedProfile(@Param("loginEmail") String loginEmail);
}

