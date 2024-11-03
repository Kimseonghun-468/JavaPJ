package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.FriendAccept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FriendAcceptRepository extends JpaRepository<FriendAccept, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM FriendAccept FA " +
            "WHERE (FA.user1 = :loginName AND FA.user2 = :userName) " +
            "OR (FA.user1 = :userName AND FA.user2 = :loginName)")
    int delete(String loginName, String userName);

    @Query("SELECT count(*) FROM FriendAccept FA " +
            "WHERE FA.user2.name = :userName")
    int getCount(String userName);
}
