package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.FriendAccept;
import com.skhkim.instaclone.entity.FriendWait;
import com.skhkim.instaclone.repository.querydsl.ClubMemberCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.skhkim.instaclone.entity.ClubMember;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long>, ClubMemberCustom {

    ClubMember findByName(@Param("name") String name);

    boolean existsByEmail(@Param("email") String email);

    boolean existsByName(@Param("name") String name);
}
