package com.skhkim.instaclone.chatting.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.QChatMessage;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatMessageDSLImpl implements ChatMessageDSL{
    private final JPAQueryFactory queryFactory;

    public ChatMessageDSLImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Slice<ChatMessage> selectChatMessages(Pageable pageable, Long roomId, Long lastCid, Long joinCid, String loginEmail, String dType) {
        QChatMessage qChatMessage = QChatMessage.chatMessage;

        List<ChatMessage> result;

        if (dType != null) {
            if ("up".equalsIgnoreCase(dType)) {
                result = queryFactory.selectFrom(qChatMessage)
                        .where(qChatMessage.roomId.eq(roomId)
                                .and(qChatMessage.cid.loe(lastCid))
                                .and(qChatMessage.cid.gt(joinCid)))
                        .orderBy(qChatMessage.cid.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();
            } else if ("down".equalsIgnoreCase(dType)) {
                result = queryFactory.selectFrom(qChatMessage)
                        .where(qChatMessage.roomId.eq(roomId)
                                .and(qChatMessage.cid.gt(lastCid))
                                .and(qChatMessage.cid.gt(joinCid)))
                        .orderBy(qChatMessage.cid.asc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();
            } else {
                throw new IllegalArgumentException("Invalid direction type: " + dType);
            }
        } else {
            result = queryFactory.selectFrom(qChatMessage)
                    .where(qChatMessage.roomId.eq(roomId)
                            .and(qChatMessage.sendUser.email.ne(loginEmail))
                            .and(qChatMessage.cid.gt(lastCid))
                            .and(qChatMessage.cid.gt(joinCid)))
                    .fetch();
        }

        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }
}
