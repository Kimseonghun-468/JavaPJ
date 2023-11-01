package com.example.mreview.repository;

import com.example.mreview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
//    @Query("select m, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m "
//            + "left outer join Review r on r.movie = m group by m")
//    Page<Object[]> getListPage(Pageable pageable);

    @Query("SELECT m, mi, avg(coalesce(r.grade, 0)), count(distinct r) FROM Movie m " +
            "LEFT OUTER JOIN MovieImage mi ON mi.movie = m " +
            "LEFT OUTER JOIN Review r ON r.movie = m " +
            "WHERE (mi.inum IS NULL OR mi.inum = (SELECT MAX(mi2.inum) FROM MovieImage mi2 WHERE mi2.movie = m)) " +
            "GROUP BY m, mi")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) " +
            "from Movie m left outer join  MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m " +
            "where m.mno = :mno " +
            "GROUP BY mi")
    List<Object[]> getMovieWithAll(Long mno);
}
