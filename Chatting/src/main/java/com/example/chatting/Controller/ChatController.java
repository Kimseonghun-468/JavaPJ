package com.example.chatting.Controller;
import com.example.chatting.Entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import lombok.extern.log4j.Log4j2;
@Controller
@Log4j2
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        log.info("ChatMessage : ", chatMessage);
        return new ChatMessage(HtmlUtils.htmlEscape(chatMessage.getName()), HtmlUtils.htmlEscape(chatMessage.getContent()));
    }
}
