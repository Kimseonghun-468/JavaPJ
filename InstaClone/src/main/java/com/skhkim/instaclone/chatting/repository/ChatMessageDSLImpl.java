package com.skhkim.instaclone.chatting.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.QChatMessage;
import com.skhkim.instaclone.request.MessagePageRequest;
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

    public Slice<ChatMessage> selectChatMessages(MessagePageRequest request) {
        QChatMessage qChatMessage = QChatMessage.chatMessage;
        Pageable pageable = request.getPageable();

        List<ChatMessage> result;

        if (request.getDType() != null) {
            if ("up".equalsIgnoreCase(request.getDType())) {
                result = queryFactory.selectFrom(qChatMessage)
                        .where(qChatMessage.roomId.eq(request.getRoomId())
                                .and(qChatMessage.cid.loe(request.getLastCid()))
                                .and(qChatMessage.cid.gt(request.getJoinCid())))
                        .orderBy(qChatMessage.cid.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();
            } else if ("down".equalsIgnoreCase(request.getDType())) {
                result = queryFactory.selectFrom(qChatMessage)
                        .where(qChatMessage.roomId.eq(request.getRoomId())
                                .and(qChatMessage.cid.gt(request.getLastCid()))
                                .and(qChatMessage.cid.gt(request.getJoinCid())))
                        .orderBy(qChatMessage.cid.asc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();
            } else {
                throw new IllegalArgumentException("Invalid direction type: " + request.getDType());
            }
        } else {
            result = queryFactory.selectFrom(qChatMessage)
                    .where(qChatMessage.roomId.eq(request.getRoomId())
                            .and(qChatMessage.sendUser.id.ne(request.getChatId()))
                            .and(qChatMessage.cid.gt(request.getLastCid()))
                            .and(qChatMessage.cid.gt(request.getJoinCid())))
                    .fetch();
        }

        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }
}
