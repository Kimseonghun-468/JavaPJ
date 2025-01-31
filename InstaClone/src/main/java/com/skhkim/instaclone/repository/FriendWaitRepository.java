package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.dto.UserInfoProjection;
import com.skhkim.instaclone.entity.FriendWait;
import com.skhkim.instaclone.repository.querydsl.FriendWaitCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FriendWaitRepository extends JpaRepository<FriendWait, Long>, FriendWaitCustom {

    @Modifying
    @Transactional
    @Query("DELETE FROM FriendWait FW " +
            "WHERE (FW.requester.name = :userName AND FW.receiver.name = :loginName)")
    int delete(@Param("loginName") String loginName, @Param("userName") String userName);

    @Query("SELECT FW.requester as clubMember FROM FriendWait FW " +
            "WHERE FW.receiver.name = :loginName " +
            "ORDER BY FW.requester.name ")
    Slice<UserInfoProjection> getByWaitingListPage(Pageable pageable, @Param("loginName") String loginName);
}
