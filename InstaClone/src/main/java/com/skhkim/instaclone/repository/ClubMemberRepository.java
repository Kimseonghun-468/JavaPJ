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

    @Query("SELECT f FROM ClubMember m join m.friendshipList f WHERE f.friendEmail =:email AND " +
            "f.status = com.skhkim.instaclone.entity.FriendShipStatus.WAITING and f.isFrom =false ")
    List<FriendShip> findByEmailStatusWaiting(String email);

    @Query("SELECT f FROM ClubMember m join m.friendshipList f WHERE f.userName =:requesterName and f.friendName =:accepterName and f.isFrom =true")
    Optional<FriendShip> getFriendshipsByName(@Param("requesterName") String requesterName, @Param("accepterName") String accepterName);

    @Query("SELECT f FROM ClubMember m join m.friendshipList f WHERE f.userName =:requesterName and f.friendName =:accepterName and f.isFrom =false ")
    Optional<FriendShip> getFriendshipsByNameIsNotFrom(@Param("requesterName") String requesterName, @Param("accepterName") String accepterName);
    boolean existsByEmail(String email);
}
