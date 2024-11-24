package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.request.MessagePageRequest;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.repository.ChatMessageRepository;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;
import com.skhkim.instaclone.service.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatUserRepository chatUserRepository;

    @Override
    public void register(ChatMessageDTO chatMessageDTO, Long roomID){
        ChatMessage chatMessage = EntityMapper.dtoToEntity(chatMessageDTO, roomID);
        chatMessageRepository.save(chatMessage);
    }
    @Override
    public void updateChatMessagesReadStatus(Long roomID, String userEmail){
        ChatUser chatUser = chatUserRepository.getChatUsersByRoomIdAndEmail(roomID, userEmail);
        List<ChatMessage> result = chatMessageRepository.updateByRoomIdAndSenderEmailAndTime(roomID, userEmail, chatUser.getDisConnect());
        result.forEach(chatMessage -> chatMessage.setReadStatus(chatMessage.getReadStatus()-1));
        chatMessageRepository.saveAll(result);
    }

    @Override
    public ChatMessageResponse selectChatMessageUp(MessagePageRequest messagePageRequest, Long roomId, String loginEmail){
        Pageable pageable = messagePageRequest.getPageable();

        LocalDateTime disConnectTime = chatUserRepository.getDisConnectTimeByEmail(loginEmail, roomId);
        Slice<ChatMessage> result = chatMessageRepository.selectChatMessageUp(pageable, roomId, disConnectTime);
        List<ChatMessageDTO> chatMessageDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new ChatMessageResponse(chatMessageDTOS, result.hasNext());
    }

    @Override
    public ChatMessageResponse selectChatMessageDown(MessagePageRequest messagePageRequest, Long roomId, String loginEmail){
        Pageable pageable = messagePageRequest.getPageable();

        LocalDateTime disConnectTime = chatUserRepository.getDisConnectTimeByEmail(loginEmail, roomId);
        Slice<ChatMessage> result = chatMessageRepository.selectChatMessageDown(pageable, roomId, disConnectTime);
        List<ChatMessageDTO> chatMessageDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new ChatMessageResponse(chatMessageDTOS, result.hasNext());
    }

    @Override
    public Long getNotReadNum(String loginEmail, Long roomId){
        LocalDateTime disConnectTime = chatUserRepository.getChatUsersByRoomIdAndEmail(roomId, loginEmail).getDisConnect();
        return chatMessageRepository.getNotReadNum(roomId, loginEmail, disConnectTime);
    }


}
