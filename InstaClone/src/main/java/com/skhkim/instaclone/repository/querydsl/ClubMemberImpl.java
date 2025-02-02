package com.skhkim.instaclone.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.QClubMember;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClubMemberImpl implements ClubMemberCustom {

    private final JPAQueryFactory queryFactory;

    public ClubMemberImpl(EntityManager em) {this.queryFactory = new JPAQueryFactory(em);}

    public Optional<ClubMember> selectByEmail(String email){
        QClubMember clubMember = QClubMember.clubMember;
        return Optional.ofNullable(queryFactory.selectFrom(clubMember)
                .where(clubMember.email.eq(email))
                .fetchOne());
    }

    public Optional<ClubMember> selectByEmail(String email, boolean social){
        QClubMember clubMember = QClubMember.clubMember;
        return Optional.ofNullable(queryFactory.selectFrom(clubMember)
                .where(clubMember.email.eq(email)
                        .and(clubMember.fromSocial.eq(social)))
                .fetchOne());
    }

    public ClubMember selectByName(String userName){
        QClubMember clubMember = QClubMember.clubMember;
        return queryFactory.selectFrom(clubMember)
                .where(clubMember.name.eq(userName))
                .fetchOne();
    }

    public List<ClubMember> selectByIds(List<Long> ids){
        QClubMember clubMember = QClubMember.clubMember;
        return queryFactory.selectFrom(clubMember)
                .where(clubMember.id.in(ids))
                .fetch();
    }

    public List<ClubMember> selectByNames(List<String> names){
        QClubMember clubMember = QClubMember.clubMember;
        return queryFactory.selectFrom(clubMember)
                .where(clubMember.name.in(names))
                .fetch();
    }

    public boolean signValidation(String name, String email){
        QClubMember clubMember = QClubMember.clubMember;
        return queryFactory.selectFrom(clubMember)
                .where(clubMember.name.eq(name)
                        .and(clubMember.email.eq(email)))
                .fetchOne() != null;
    }

    public Slice<ClubMember> selectSearchUserList(Pageable pageable, String searchTerm, Long userId){
        QClubMember clubMember = QClubMember.clubMember;
        List<ClubMember> result = queryFactory.selectFrom(clubMember)
                .where(clubMember.id.ne(userId)
                .and(clubMember.name.containsIgnoreCase(searchTerm)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }

    public void updateByName(String changeName, String originalName){
        QClubMember clubMember = QClubMember.clubMember;
        queryFactory.update(clubMember)
                .set(clubMember.name, changeName)
                .where(clubMember.name.eq(originalName))
                .execute();
    }

    public void updateByPassward(String passward, String name){
        QClubMember clubMember = QClubMember.clubMember;
        queryFactory.update(clubMember)
                .set(clubMember.password, passward)
                .where(clubMember.name.eq(name))
                .execute();
    }

}









