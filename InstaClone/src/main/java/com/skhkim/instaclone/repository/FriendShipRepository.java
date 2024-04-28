package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {
    Optional<FriendShip> findByUserEmailAndFriendEmail(String userEmail, String friendEmail);
    @Query("SELECT f FROM FriendShip f WHERE f.friendEmail =:userEmail AND" +
            " f.status = com.skhkim.instaclone.entity.FriendShipStatus.WAITING")
    List<FriendShip> findByUserEmailStatusWaiting(@Param("userEmail") String userEmail);

}
