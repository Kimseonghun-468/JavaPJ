package com.skhkim.instaclone.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.dto.UserInfoProjection;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.FriendAccept;
import com.skhkim.instaclone.entity.QClubMember;
import com.skhkim.instaclone.entity.QFriendAccept;
import com.skhkim.instaclone.entity.QFriendWait;
import com.skhkim.instaclone.entity.type.FriendStatus;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FriendAcceptRepositoryImpl implements FriendAcceptCustom {

    private final JPAQueryFactory queryFactory;

    public FriendAcceptRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public int delete(String loginName, String userName) {
        QFriendAccept friendAccept = QFriendAccept.friendAccept;

        long count = queryFactory.delete(friendAccept)
                .where(friendAccept.user1.name.eq(loginName)
                        .and(friendAccept.user2.name.eq(userName))
                        .or(friendAccept.user1.name.eq(userName)
                                .and(friendAccept.user2.name.eq(loginName))))
                .execute();

        return (int) count;
    }

    public int getCount(String userName) {
        QFriendAccept friendAccept = QFriendAccept.friendAccept;

        return queryFactory.select(friendAccept.count())
                .from(friendAccept)
                .where(friendAccept.user2.name.eq(userName))
                .fetchOne().intValue();
    }

    public Slice<ClubMember> getByAcceptListPage(Pageable pageable, String loginName) {
        QFriendAccept friendAccept = QFriendAccept.friendAccept;
        QClubMember clubMember = QClubMember.clubMember;

        List<ClubMember> result = queryFactory
                .select(clubMember)
                .from(friendAccept)
                .join(friendAccept.user2, clubMember)
                .where(friendAccept.user1.name.eq(loginName))
                .orderBy(clubMember.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }

    public Slice<UserInfoProjection> getFriendListPage(Pageable pageable, String userName, String loginName) {
        QFriendAccept faUser = QFriendAccept.friendAccept;
        QFriendAccept faLogin = new QFriendAccept("faLogin");
        QFriendWait fwReceiver = QFriendWait.friendWait;
        QFriendWait fwRequester = new QFriendWait("fwRequester");

        List<UserInfoProjection> result = queryFactory
                .select(Projections.constructor(UserInfoProjection.class,
                        faUser.user2,
                        new CaseBuilder()
                                .when(faLogin.user2.isNotNull()).then(FriendStatus.ACCEPTED)
                                .when(fwReceiver.requester.isNotNull()).then(FriendStatus.RECEIVER)
                                .when(fwRequester.receiver.isNotNull()).then(FriendStatus.REQUESTER)
                                .otherwise(FriendStatus.NONE)
                ))
                .from(faUser)
                .leftJoin(faLogin).on(faUser.user2.eq(faLogin.user2)
                        .and(faLogin.user1.name.eq(loginName)))
                .leftJoin(fwReceiver).on(fwReceiver.requester.eq(faUser.user2)
                        .and(fwReceiver.receiver.name.eq(loginName)))
                .leftJoin(fwRequester).on(fwRequester.receiver.eq(faUser.user2)
                        .and(fwRequester.requester.name.eq(loginName)))
                .where(faUser.user1.name.eq(userName))
                .orderBy(faLogin.user2.name.desc().nullsLast(),
                         fwReceiver.requester.name.desc().nullsLast(),
                         fwRequester.receiver.name.desc().nullsLast())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }

    public Slice<ClubMember> selectInviteListByName(Pageable pageable, String loginName, String inviteSearchTerm, List<String> roomUsers) {
        QFriendAccept friendAccept = QFriendAccept.friendAccept;
        QClubMember clubMember = QClubMember.clubMember;

        List<ClubMember> result = queryFactory
                .select(clubMember) // Assuming UserInfoProjection is mapped to ClubMember
                .from(friendAccept)
                .join(friendAccept.user2, clubMember)
                .where(friendAccept.user1.name.eq(loginName)
                        .and(clubMember.name.contains(inviteSearchTerm))
                        .and(clubMember.name.notIn(roomUsers)))
                .orderBy(clubMember.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }

    public Slice<ClubMember> selectInviteList(Pageable pageable, String loginName, List<String> roomUsers) {
        QFriendAccept friendAccept = QFriendAccept.friendAccept;
        QClubMember clubMember = QClubMember.clubMember;

        List<ClubMember> result = queryFactory
                .select(clubMember)
                .from(friendAccept)
                .join(friendAccept.user2, clubMember)
                .where(friendAccept.user1.name.eq(loginName)
                        .and(clubMember.name.notIn(roomUsers)))
                .orderBy(clubMember.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }

    public Optional<FriendAccept> getAcceptFriend(String loginName, String userName) {
        QFriendAccept friendAccept = QFriendAccept.friendAccept;

        FriendAccept result = queryFactory.selectFrom(friendAccept)
                .where(friendAccept.user1.name.eq(loginName)
                        .and(friendAccept.user2.name.eq(userName)))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
