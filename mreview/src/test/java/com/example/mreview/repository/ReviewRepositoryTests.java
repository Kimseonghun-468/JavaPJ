package com.example.mreview.repository;

import com.example.mreview.entity.Member;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;

//    @Test
//    public void insertMovieReviews(){
//        IntStream.rangeClosed(1, 300).forEach( i-> {
//            Long mno = (long)(Math.random() * 100) +1;
//
//            Long mid = ((long)(Math.random() * 100) +1);
//            Member member = Member.builder().mid(mid).build();
//            Review movieReview = Review.builder()
//                    .member(member)
//                    .movie(Movie.builder().mno(mno).build())
//                    .grade((int)(Math.random()*5) +1)
//                    .text("이 영화에 대한 느낌..." + i)
//                    .build();
//
//            reviewRepository.save(movieReview);
//        });
//    }
//    @Test
//    public void testGetMovieReviews() {
//        Movie movie = Movie.builder().mno(92L).build();
//
//        List<Review> result = reviewRepository.findByMovie(movie);
//
//        result.forEach(movieReview -> {
//            System.out.print(movieReview.getReviewnum());
//            System.out.print("  "+movieReview.getGrade());
//            System.out.print("  "+movieReview.getText());
//            System.out.print("  "+movieReview.getMember().getEmail());
//            System.out.println("------------------------");
//
//        });

}
