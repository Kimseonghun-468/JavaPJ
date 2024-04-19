package com.example.chatting.Controller;
import com.example.chatting.DTO.ChatMessageDTO;
import com.example.chatting.Entity.ChatMessage;
import com.example.chatting.Service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import lombok.extern.log4j.Log4j2;
@Controller
@Log4j2
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO) {
        ChatMessageDTO reuslt = ChatMessageDTO.builder()
                .name(HtmlUtils.htmlEscape(chatMessageDTO.getName()))
                .content(HtmlUtils.htmlEscape(chatMessageDTO.getContent()))
                .build();
        return reuslt;
    }
}
