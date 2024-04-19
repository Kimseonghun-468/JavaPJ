package com.example.chatting.Service;

import com.example.chatting.DTO.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatMessageServiceImpl implements ChatMessageService {

    @Override
    public ChatMessageDTO getNewChatMessageDTO(String name, String content){

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder().build();
        return chatMessageDTO;
    }
}
