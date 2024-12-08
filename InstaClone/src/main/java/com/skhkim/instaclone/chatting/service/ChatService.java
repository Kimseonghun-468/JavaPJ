package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.request.InviteRequest;
import com.skhkim.instaclone.chatting.request.MessageRequest;

import java.util.List;

public interface ChatService {

    /**
     * 채팅 메시지 작성
     * Param : roomId, ChatMessageDTO
     * 1. ChatUser, Session을 통해 현재 ReadStatus 확인
     * 2. Message Insert
     * 3. Room Update
     * */
    void sendMessage(MessageRequest request);

    /**
     * 채팅방 유저 초대
     * Param : roomId, InviteUsers
     * 1. ChatUser Insert
     * 2. Room Update
     * 3. Invite Message Insert
     * 4. Redis Pub
     * */
    void inviteChatUsers(InviteRequest request);


    /**
     * 채팅방 참여
     * Param : roomId
     * 1. Select ChatUsers
     * 2. Update ReadStatus
     * */
    List<ChatUserDTO> joinChatRoom(Long roomId);


    Long createChatRoom(String userName);
}
