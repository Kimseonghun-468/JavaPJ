package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.FriendShip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.skhkim.instaclone.entity.ClubMember;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {


    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.fromSocial = :social and m.email =:email")
    Optional<ClubMember> findByEmail(String email, boolean social);
    Optional<ClubMember> findByEmail(String eamil);
    ClubMember findByName(String name);

    boolean existsByEmail(String email);
    boolean existsByName(String name);


    @Query("SELECT CASE WHEN count(m) > 0 THEN TRUE ELSE FALSE END from ClubMember m WHERE m.name = :name OR m.email =:email")
    boolean existsByNameAndEmail(String name, String email);

    @Query("SELECT f FROM ClubMember m join m.friendshipList f WHERE f.clubMemberUser.name =:requesterName and f.clubMemberFriend.name =:accepterName and f.isFrom =true")
    Optional<FriendShip> getFriendshipsByName(@Param("requesterName") String requesterName, @Param("accepterName") String accepterName);

    @Query("SELECT f FROM ClubMember m join m.friendshipList f WHERE f.clubMemberUser.name =:requesterName and f.clubMemberFriend.name =:accepterName and f.isFrom =false ")
    Optional<FriendShip> getFriendshipsByNameIsNotFrom(@Param("requesterName") String requesterName, @Param("accepterName") String accepterName);

    @Query("SELECT m, pi FROM ClubMember m LEFT JOIN ProfileImage pi ON m.name = pi.clubMember.name WHERE m.name like CONCAT('%', :searchTerm, '%') AND m.name != :userName ORDER BY m.name")
    Page<Object[]> findByNamePage(Pageable pageable, @Param("searchTerm")String searchTerm, String userName);

    @Modifying
    @Transactional
    @Query("UPDATE ClubMember m SET m.name =:changeName WHERE m.name =:originalName")
    void updateByName(String changeName, String originalName);

    @Modifying
    @Transactional
    @Query("UPDATE ClubMember m SET m.password =:newPassword WHERE m.name =:userName")
    void updateByPassword(String newPassword, String userName);
}
