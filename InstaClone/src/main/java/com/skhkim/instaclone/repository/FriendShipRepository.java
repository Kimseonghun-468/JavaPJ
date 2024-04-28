package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM FriendShip fs WHERE fs.userEmail =:userEmail AND fs.friendEmail =:friendEmail")
    void deleteByUserEmailAndFriendEmail(String userEmail, String friendEmail);
}
