package com.skhkim.instaclone.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.QClubMember;
import com.skhkim.instaclone.entity.QPost;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostImpl implements PostCustom {

    private final JPAQueryFactory queryFactory;

    public PostImpl(EntityManager em) {this.queryFactory = new JPAQueryFactory(em);}

    public Slice<Post> selectList(Pageable pageable, String name){
        QPost Post = QPost.post;

        List<Post> result = queryFactory.selectFrom(Post)
                .where(Post.clubMember.name.eq(name))
                .orderBy(Post.pno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }

    public Post select(Long pno) {
        QPost Post = QPost.post;
        return queryFactory.selectFrom(Post)
                        .where(Post.pno.eq(pno))
                        .fetchOne();
    }

    public Long getPostCount(String name) {
        QPost Post = QPost.post;
        QClubMember clubMember = QClubMember.clubMember;
        return queryFactory.select(Post.count())
                .from(Post)
                .join(Post.clubMember, clubMember)
                .where(clubMember.name.eq(name))
                .fetchOne();
    }

    public boolean validation(Long pno, Long userId){
        QPost Post = QPost.post;
        QClubMember clubMember = QClubMember.clubMember;

        Long count = queryFactory.select(Post.count())
                .from(Post)
                .join(Post.clubMember, clubMember)
                .where(clubMember.id.eq(userId)
                        .and(Post.pno.eq(pno)))
                .fetchOne();

        return count != null && count > 0;
    }

    public void delete(Long pno) {
        QPost Post = QPost.post;
        queryFactory.delete(Post)
                .where(Post.pno.eq(pno))
                .execute();
    }


}
