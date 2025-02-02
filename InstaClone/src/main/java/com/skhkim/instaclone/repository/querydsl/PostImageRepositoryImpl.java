package com.skhkim.instaclone.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.entity.PostImage;
import com.skhkim.instaclone.entity.QPostImage;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostImageRepositoryImpl implements PostImageCustom {

    private final JPAQueryFactory queryFactory;

    public PostImageRepositoryImpl(EntityManager em) {this.queryFactory = new JPAQueryFactory(em);}

    public void delete(Long pno) {
        QPostImage postImage = QPostImage.postImage;
        queryFactory.delete(postImage)
                .where(postImage.post.pno.eq(pno))
                .execute();
    }

    public List<PostImage> selectPostImages(Long pno) {
        QPostImage postImage = QPostImage.postImage;
        return queryFactory.select(postImage)
                .from(postImage)
                .where(postImage.post.pno.eq(pno))
                .fetch();
    }




}
