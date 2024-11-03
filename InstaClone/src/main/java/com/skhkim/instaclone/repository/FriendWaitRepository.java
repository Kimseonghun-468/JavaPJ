package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.FriendWait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FriendWaitRepository extends JpaRepository<FriendWait, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM FriendWait FW " +
            "WHERE (FW.requester.name = :loginName AND FW.receiver.name = :userName)")
    int delete(String loginName, String userName);
}
