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
            "(pi.pfino, pi.userName, pi.userEmail, fs.friendEmail,fs.friendName, pi.uuid, pi.imgName, pi.path)" +
            "FROM FriendShip fs" +
            " JOIN ProfileImage pi ON fs.friendEmail = pi.userEmail" +
            " WHERE fs.userEmail =:loginEmail AND fs.status =com.skhkim.instaclone.entity.FriendShipStatus.WAITING")
    List<FriendShipProfileDTO> findByExistProfile(@Param("loginEmail") String loginEmail);
    @Query("SELECT new com.skhkim.instaclone.dto.FriendShipProfileDTO" +
            "(pi.pfino, pi.userName, pi.userEmail, fs.friendEmail,fs.friendName , pi.uuid, pi.imgName, pi.path)" +
            " FROM FriendShip fs"+
            " LEFT JOIN ProfileImage pi ON fs.friendEmail = pi.userEmail" +
            " WHERE fs.userEmail =:loginEmail AND pi.pfino is null")
    List<FriendShipProfileDTO> findByNotExistProfile(@Param("loginEmail") String loginEmail);
    // join을 통해서 profileImage랑, FriendshipList를 같이 뽑는게 나을거 같기도 하고..
}

