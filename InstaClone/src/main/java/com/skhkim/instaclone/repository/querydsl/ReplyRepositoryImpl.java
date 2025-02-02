package com.skhkim.instaclone.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.entity.QClubMember;
import com.skhkim.instaclone.entity.QReply;
import com.skhkim.instaclone.entity.Reply;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class ReplyRepositoryImpl implements ReplyCustom {

    private final JPAQueryFactory queryFactory;

    public ReplyRepositoryImpl(EntityManager em) {this.queryFactory = new JPAQueryFactory(em);}

    public Slice<Reply> selectList(Pageable pageable, Long pno) {
        QReply reply = QReply.reply;

        List<Reply> result = queryFactory.selectFrom(reply)
                .where(reply.post.pno.eq(pno))
                .orderBy(reply.rno.desc())
                .fetch();

        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }

    public boolean validation(Long rno, Long userId){
        QReply reply = QReply.reply;
        QClubMember clubMember = QClubMember.clubMember;
        Long count = queryFactory.select(reply.count())
                .from(reply)
                .join(reply.clubMember, clubMember)
                .where(clubMember.id.eq(userId)
                        .and(reply.rno.eq(rno)))
                .fetchOne();

        return count != null && count > 0;
    }

    public void delete(Long pno){
        QReply reply = QReply.reply;
        queryFactory.delete(reply)
                .where(reply.post.pno.eq(pno))
                .execute();
    }
}
