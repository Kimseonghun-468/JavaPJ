package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.FriendAccept;
import com.skhkim.instaclone.entity.FriendWait;
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

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.fromSocial = :social and m.email = :email")
    Optional<ClubMember> findByEmail(@Param("email") String email, @Param("social") boolean social);

    Optional<ClubMember> findByEmail(@Param("email") String email);

    @Query("select m from ClubMember m where m.id in :ids")
    List<ClubMember> findByIds(@Param("ids") List<Long> ids);

    @Query("select m from ClubMember m where m.name in :names")
    List<ClubMember> selectUserByNames(@Param("names") List<String> names);

    ClubMember findByName(@Param("name") String name);

    boolean existsByEmail(@Param("email") String email);

    boolean existsByName(@Param("name") String name);

    @Query("SELECT CASE WHEN count(m) > 0 THEN TRUE ELSE FALSE END from ClubMember m WHERE m.name = :name OR m.email =:email")
    boolean existsByNameAndEmail(@Param("name") String name, @Param("email") String email);

    @Query("SELECT FW FROM FriendWait FW " +
            "WHERE (FW.receiver.name = :loginName AND FW.requester.name = :userName) " +
            "OR (FW.receiver.name = :userName AND FW.requester.name = :loginName)")
    Optional<FriendWait> getWaitByName(@Param("loginName") String loginName, @Param("userName") String userName);

    @Query("SELECT FA FROM FriendAccept FA " +
            "WHERE FA.user1.name = :loginName AND FA.user2.name = :userName")
    Optional<FriendAccept> getAcceptFriend(@Param("loginName") String loginName, @Param("userName") String userName);

    @Query("SELECT CM AS clubMember FROM ClubMember CM " +
            "WHERE CM.name like CONCAT('%', :searchTerm, '%') AND CM.name != :loginName ORDER BY CM.name")
    Slice<ClubMember> selectSearchUserInfo(@Param("pageable") Pageable pageable,
                                           @Param("searchTerm") String searchTerm,
                                           @Param("loginName") String loginName);

    @Query("SELECT CM FROM ClubMember CM WHERE CM.name = :userName")
    ClubMember selectUserInfo(@Param("userName") String userName);

    @Modifying
    @Transactional
    @Query("UPDATE ClubMember m SET m.name =:changeName WHERE m.name =:originalName")
    void updateByName(@Param("changeName") String changeName, @Param("originalName") String originalName);

    @Modifying
    @Transactional
    @Query("UPDATE ClubMember m SET m.password =:newPassword WHERE m.name =:userName")
    void updateByPassword(@Param("newPassword") String newPassword, @Param("userName") String userName);
}
