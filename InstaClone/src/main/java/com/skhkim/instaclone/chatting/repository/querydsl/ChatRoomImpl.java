package com.skhkim.instaclone.chatting.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.entity.QChatRoom;
import com.skhkim.instaclone.chatting.entity.QChatUser;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChatRoomImpl implements ChatRoomCustom{

    private final JPAQueryFactory queryFactory;

    public ChatRoomImpl(EntityManager em) {this.queryFactory = new JPAQueryFactory(em);}

    public ChatRoom select(Long roomId){
        QChatRoom chatRoom = QChatRoom.chatRoom;

        return queryFactory.selectFrom(chatRoom)
                .where(chatRoom.roomId.eq(roomId))
                .fetchOne();
    }

    public void updateUserNum(Long roomId, Long addNum){
        QChatRoom chatRoom = QChatRoom.chatRoom;

        queryFactory.update(chatRoom)
                .set(chatRoom.userNum, addNum)
                .where(chatRoom.roomId.eq(roomId))
                .execute();
    }

    public Slice<ChatRoom> selectList(Pageable pageable, Long userId){
        QChatUser chatUser = QChatUser.chatUser;

        List<ChatRoom> result;
        result = queryFactory.select(chatUser.chatRoom)
                .from(chatUser)
                .where(chatUser.member.id.eq(userId))
                .orderBy(chatUser.chatRoom.lastCid.desc())
                .fetch();

        return new SliceImpl<>(result, pageable, result.size() == pageable.getPageSize());

    }

    public Optional<Long> validate(String loginName, String userName){
        QChatUser chatUser = QChatUser.chatUser;

        return Optional.ofNullable(
                queryFactory
                        .select(chatUser.chatRoom.roomId)
                        .from(chatUser)
                        .join(chatUser.chatRoom)
                        .join(chatUser.chatRoom.chatUserList, chatUser)
                        .where(
                                chatUser.member.name.eq(loginName),
                                chatUser.chatRoom.userNum.eq(2L),
                                chatUser.chatRoom.chatUserList.any().member.name.eq(userName)
                        )
                        .fetchOne()
        );
    }






}
