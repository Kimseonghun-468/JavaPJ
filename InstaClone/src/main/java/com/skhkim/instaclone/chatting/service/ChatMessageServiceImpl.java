package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.repository.ChatMessageRepository;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import com.skhkim.instaclone.service.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatUserRepository chatUserRepository;
    private final ProfileImageRepository profileImageRepository;

    @Override
    public void register(ChatMessageDTO chatMessageDTO, Long roomID){
        ChatMessage chatMessage = dtoToEntity(chatMessageDTO, roomID);
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
    public ChatMessageResponse selectChatMessageUp(PageRequestDTO pageRequestDTO, Long roomId, String loginEmail){
        Pageable pageable = pageRequestDTO.getPageable();

        LocalDateTime disConnectTime = chatUserRepository.getDisConnectTimeByEmail(loginEmail, roomId);
        Slice<ChatMessage> result = chatMessageRepository.selectChatMessageUp(pageable, roomId, disConnectTime);
        List<ChatMessageDTO> chatMessageDTOS = result.stream().map(EntityMapper::entityToDTO).toList();

        return new ChatMessageResponse(chatMessageDTOS, result.hasNext());
    }

    @Override
    public ChatMessageResponse selectChatMessageDown(PageRequestDTO pageRequestDTO, Long roomId, String loginEmail){
        Pageable pageable = pageRequestDTO.getPageable();

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
