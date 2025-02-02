package com.skhkim.instaclone.chatting.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.entity.QChatMessage;
import com.skhkim.instaclone.chatting.entity.QChatUser;
import com.skhkim.instaclone.chatting.request.MessageRequest;
import com.skhkim.instaclone.request.MessagePageRequest;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatMessageImpl implements ChatMessageCustom {
    private final JPAQueryFactory queryFactory;

    public ChatMessageImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Slice<ChatMessage> selectChatMessagesPage(MessagePageRequest request) {
        QChatMessage qChatMessage = QChatMessage.chatMessage;
        Pageable pageable = request.getPageable();

        List<ChatMessage> result;


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


        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());
    }

    public List<ChatMessage> selectChatMessages(MessageRequest request){
        QChatMessage chatMessage = QChatMessage.chatMessage;
        return queryFactory.selectFrom(chatMessage)
                .where(chatMessage.roomId.eq(request.getRoomId())
                        .and(chatMessage.sendUser.id.ne(request.getUserId()))
                        .and(chatMessage.cid.gt(request.getLastCid()))
                        .and(chatMessage.cid.goe(request.getJoinCid())))
                .fetch();
    }

    public Long getNotReadNum(MessageRequest request) {
        QChatMessage qChatMessage = QChatMessage.chatMessage;
        return queryFactory
                .select(qChatMessage.count())
                .from(qChatMessage)
                .where(
                        qChatMessage.roomId.eq(request.getRoomId())
                                .and(qChatMessage.sendUser.id.ne(request.getChatId()))
                                .and(qChatMessage.cid.gt(request.getLastCid()))
                                .and(qChatMessage.cid.gt(request.getJoinCid()))
                )
                .fetchOne();
    }

    // todo : join으로 처리하자
    public void updateReadNum(MessageRequest request) {
        QChatUser qChatUser = QChatUser.chatUser;
        QChatMessage qChatMessage = QChatMessage.chatMessage;

        ChatUser resultUser = queryFactory
                .selectFrom(qChatUser)
                .where(qChatUser.member.id.eq(request.getChatId()))
                .fetchOne();

        Long joinCid = resultUser.getJoinCid();
        Long lastCid = resultUser.getLastCid();
        Long roomId = resultUser.getChatRoom().getRoomId();

        // Update readStatus for chat messages
        queryFactory
                .update(qChatMessage)
                .set(qChatMessage.readStatus, -1L)
                .where(
                        qChatMessage.roomId.eq(roomId)
                                .and(qChatMessage.cid.gt(joinCid))
                                .and(qChatMessage.cid.gt(lastCid))
                                .and(qChatMessage.sendUser.id.ne(request.getChatId()))
                )
                .execute();

    }

}
