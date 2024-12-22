package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void updateLastCmId(ChatMessageDTO chatMessageDTO) {
        ChatRoom chatRoom = chatRoomRepository.selectChatRoom(chatMessageDTO.getRoomId());
        chatRoom.setLastChat(chatMessageDTO.getContent());
        chatRoom.setLastCid(chatMessageDTO.getCid());
        chatRoomRepository.save(chatRoom);

    }
    @Override
    public void updateUserNum(Long roomId, Integer addNum){
        chatRoomRepository.updateUserNumByRoomId(roomId, addNum);
    }
}