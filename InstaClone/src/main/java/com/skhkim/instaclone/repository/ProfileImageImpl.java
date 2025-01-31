package com.skhkim.instaclone.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.entity.QClubMember;
import com.skhkim.instaclone.entity.QProfileImage;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileImageImpl implements ProfileImageCustom {
    private final JPAQueryFactory queryFactory;

    public ProfileImageImpl(EntityManager em) {this.queryFactory = new JPAQueryFactory(em);}

    public ProfileImage select(Long userId) {
        QProfileImage profileImage = QProfileImage.profileImage;
        QClubMember clubMember = QClubMember.clubMember;

        return queryFactory.selectFrom(profileImage)
                .join(profileImage.clubMember, clubMember)
                .where(clubMember.id.eq(userId))
                .orderBy(profileImage.modDate.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public void delete(Long userId) {
        QProfileImage profileImage = QProfileImage.profileImage;

        queryFactory.delete(profileImage)
                .where(profileImage.clubMember.id.eq(userId))
                .execute();
    }
}
