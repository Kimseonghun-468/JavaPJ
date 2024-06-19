package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.repository.ChatMessageRepository;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatUserRepository chatUserRepository;

    @Override
    public Long register(ChatMessageDTO chatMessageDTO, Long roomID){
        ChatMessage chatMessage = dtoToEntity(chatMessageDTO, roomID);
        chatMessageRepository.save(chatMessage);
        return chatMessage.getCid();
    }
    @Override
    public void updateChatMessagesReadStatus(Long roomID, String userEmail){
        ChatUser chatUser = chatUserRepository.getChatUsersByRoomIdAndEmail(roomID, userEmail);
        chatMessageRepository.updateByRoomIdAndSenderEmailAndTime(chatUser.getChatRoom().getRoomId(), userEmail, chatUser.getDisConnect());

    }

    @Override
    public PageResultDTO<ChatMessageDTO, Object[]> getChatMessageListByRoomIDPageBefore(PageRequestDTO pageRequestDTO, Long roomID, String loginEmail){
        Pageable pageable = pageRequestDTO.getPageable();
        Function<Object[], ChatMessageDTO> fn = (arr -> entityToDTO(
                (ChatMessage) arr[0])
        );
        LocalDateTime disConnectTime = chatUserRepository.getDisConnectTimeByEmail(loginEmail, roomID);
        Page<Object[]> result = chatMessageRepository.getChatMessageByRoomIdAndTimeBefore(pageable, roomID, disConnectTime);
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<ChatMessageDTO, Object[]> getChatMessageListByRoomIDPageAfter(PageRequestDTO pageRequestDTO, Long roomID, String loginEmail){
        Pageable pageable = pageRequestDTO.getPageable();
        Function<Object[], ChatMessageDTO> fn = (arr -> entityToDTO(
                (ChatMessage)arr[0])
        );
        LocalDateTime disConnectTime = chatUserRepository.getDisConnectTimeByEmail(loginEmail, roomID);
        Page<Object[]> result = chatMessageRepository.getChatMessageByRoomIdAndTimeAfter(pageable, roomID, disConnectTime);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public Long getNotReadNum(String loginEmail, Long roomId){
        LocalDateTime disConnectTime = chatUserRepository.getChatUsersByRoomIdAndEmail(roomId, loginEmail).getDisConnect();
        return chatMessageRepository.getNotReadNum(roomId, loginEmail, disConnectTime);
    }


}
