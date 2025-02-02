package com.skhkim.instaclone.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendWait;
import com.skhkim.instaclone.entity.QClubMember;
import com.skhkim.instaclone.entity.QFriendWait;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FriendWaitImpl implements FriendWaitCustom{

    private final JPAQueryFactory queryFactory;

    public FriendWaitImpl(EntityManager em) {this.queryFactory = new JPAQueryFactory(em);}

    public int delete(String loginName, String userName){
        QFriendWait friendWait = QFriendWait.friendWait;
        long count = queryFactory.delete(friendWait)
                        .where(friendWait.requester.name.eq(userName)
                                .and(friendWait.receiver.name.eq(loginName)))
                        .execute();
        return (int) count;
    }


    public Slice<ClubMember> selectListById(Pageable pageable, Long userId) {
        QFriendWait friendWait = QFriendWait.friendWait;
        QClubMember clubMember = QClubMember.clubMember;
        List<ClubMember> result = queryFactory.select(clubMember)
                .from(friendWait)
                .join(friendWait.requester, clubMember)
                .where(friendWait.receiver.id.eq(userId))
                .orderBy(clubMember.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }


    public Optional<FriendWait> selectByName(String loginName, String userName) {
        QFriendWait friendWait = QFriendWait.friendWait;

        FriendWait result = queryFactory.selectFrom(friendWait)
                .where((friendWait.receiver.name.eq(loginName)
                .and(friendWait.requester.name.eq(userName)))
                .or(friendWait.receiver.name.eq(userName)
                .and(friendWait.requester.name.eq(loginName)))
                ).fetchOne();

        return Optional.ofNullable(result);
    }

}
