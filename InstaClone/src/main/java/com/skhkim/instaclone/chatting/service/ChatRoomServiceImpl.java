package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void updateLastChatTime(ChatMessageDTO chatMessageDTO) {
        Optional<ChatRoom> result = chatRoomRepository.getChatRoombyRoomId(chatMessageDTO.getRoomId());
        if (result.isPresent()) {
            ChatRoom chatRoom = result.get();
            chatRoom.setLastChat(chatMessageDTO.getContent());
            chatRoom.setLastChatTime(LocalDateTime.now());
            chatRoomRepository.save(chatRoom);
        }
    }
    @Override
    public void updateUserNum(Long roomId, Integer addNum){
        chatRoomRepository.updateUserNumByRoomId(roomId, addNum);
    }
}