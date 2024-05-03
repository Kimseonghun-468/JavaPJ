package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {
    @Query("SELECT p, pi FROM Post p " +
            "LEFT OUTER JOIN PostImage pi ON pi.post = p " +
            "WHERE p.clubMember.email = :email " +
            "AND (pi.pino is null or pi.pino = (SELECT MIN(pi2.pino) FROM PostImage pi2 WHERE pi2.post = p))")
    Page<Object[]>getListPage(Pageable pageable, String email);

    @Query("SELECT p, pi FROM Post p LEFT JOIN PostImage pi ON pi.post = p WHERE p.pno =:pno")
    List<Object[]> getPostWithAll(Long pno);

    @Query("SELECT count(p) FROM Post p " +
            "where p.clubMember.email = :email ")
    Long getPostCount(String email);

    @Query("SELECT m.email FROM ClubMember m " +
            "WHERE m.name = :name ")
    String getEmail(String name);
}


//    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) " +
//            "from Movie m left outer join  MovieImage mi on mi.movie = m " +
//            "left outer join Review r on r.movie = m " +
//            "where m.mno = :mno " +
//            "GROUP BY mi")
//    List<Object[]> getMovieWithAll(Long mno);
//}
