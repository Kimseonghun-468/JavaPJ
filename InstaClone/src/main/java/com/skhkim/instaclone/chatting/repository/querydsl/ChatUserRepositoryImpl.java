package com.skhkim.instaclone.chatting.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.entity.QChatUser;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatUserRepositoryImpl implements ChatUserCustom{

    private final JPAQueryFactory queryFactory;

    public ChatUserRepositoryImpl(EntityManager em) {this.queryFactory = new JPAQueryFactory(em);}

    public ChatUser select(Long roomId, Long userId){
        QChatUser chatUser = QChatUser.chatUser;

        return queryFactory.selectFrom(chatUser)
                .where(chatUser.chatRoom.roomId.eq(roomId)
                        .and(chatUser.member.id.eq(userId)))
                .fetchOne();
    }

    public List<ChatUser> selectList(Long roomId){
        QChatUser chatUser = QChatUser.chatUser;

        return queryFactory.selectFrom(chatUser)
                .where(chatUser.chatRoom.roomId.eq(roomId))
                .fetch();
    }

}
