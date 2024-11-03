//package com.skhkim.instaclone.repository;
//
//import com.skhkim.instaclone.entity.FriendShip;
//import com.skhkim.instaclone.entity.FriendShipStatus;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.transaction.annotation.Transactional;
//
//public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {
//
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM FriendShip fs WHERE fs.clubMemberUser.name =:userName AND fs.clubMemberFriend.name =:friendName")
//    void deleteByUserNameAndFriendName(String userName, String friendName);
//
//    @Query("SELECT count(fs) FROM FriendShip fs where fs.clubMemberFriend.name = :name AND fs.status = :status")
//    Long getFriendShipCount(String name, FriendShipStatus status);
//}
