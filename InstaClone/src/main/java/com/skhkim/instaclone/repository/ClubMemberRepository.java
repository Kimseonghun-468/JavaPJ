package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.FriendShip;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.skhkim.instaclone.entity.ClubMember;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {


    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.fromSocial = :social and m.email =:email")

    Optional<ClubMember> findByEmail(String email, boolean social);
    Optional<ClubMember> findByEmail(String eamil);
    ClubMember findByName(String name);

    @Query("SELECT f FROM ClubMember m join m.friendshipList f WHERE m.email =:email AND " +
            "f.status = com.skhkim.instaclone.entity.FriendShipStatus.WAITING")
    List<FriendShip> findByEmailStatusWaiting(String email);

    @Query("SELECT f FROM ClubMember m join m.friendshipList f WHERE f.userEmail =:requesterEmail and f.friendEmail =:accepterEmail and f.isFrom =true")
    Optional<FriendShip> findFriendshipsByEmail(@Param("requesterEmail") String requesterEmail, @Param("accepterEmail") String accepterEmail);

    @Query("SELECT f FROM ClubMember m join m.friendshipList f WHERE f.userEmail =:requesterEmail and f.friendEmail =:accepterEmail and f.isFrom =false ")
    Optional<FriendShip> findFriendshipsByEmailIsNotFrom(@Param("requesterEmail") String requesterEmail, @Param("accepterEmail") String accepterEmail);
    boolean existsByEmail(String email);
}
