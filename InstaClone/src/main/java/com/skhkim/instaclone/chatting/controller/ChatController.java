package com.skhkim.instaclone.chatting.controller;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.event.ChatRoomSessionManager;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;
import com.skhkim.instaclone.chatting.response.ChatRoomResponse;
import com.skhkim.instaclone.chatting.service.ChatMessageService;
import com.skhkim.instaclone.chatting.service.ChatRoomService;
import com.skhkim.instaclone.chatting.service.ChatUserService;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import com.skhkim.instaclone.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final ChatUserService chatUserService;
    private final ChatRoomSessionManager chatRoomSessionManager;
    private final ProfileImageRepository profileImageRepository;
    @MessageMapping("/chat/{roomID}")
    @SendTo("/topic/chat/{roomID}")
    public ChatMessageDTO sendMessage(@DestinationVariable String roomID, ChatMessageDTO chatMessageDTO) {
        log.info("Room ID :"+ roomID);
        Long id = Long.parseLong(roomID);
        Long userNum = chatRoomService.getUserNum(id);
        int readStatus = userNum.intValue() - chatRoomSessionManager.getRoomJoinNum(roomID);
        chatMessageDTO.setReadStatus((long) readStatus);
        chatMessageDTO.setRegDate(LocalDateTime.now());
        chatMessageService.register(chatMessageDTO, id);
        chatRoomService.updateLastChatTime(id, chatMessageDTO.getContent());

        return chatMessageDTO;
    }

    @MessageMapping("/chat/accessLoad/{roomID}")
    @SendTo("/topic/chat/accessLoad/{roomID}")
    public String accessLoad(@DestinationVariable String roomID, String loginName) {
        log.info(roomID);
        log.info(loginName);
        return loginName;
    }

    @MessageMapping("/chat/inviteLoad/{roomID}")
    @SendTo("/topic/chat/inviteLoad/{roomID}")
    public List<String> inviteLoad(@DestinationVariable String roomID, @RequestParam List<String> userNames) {
        return userNames;
    }

    @PostMapping("/chat/getORCreateChatRoom")
    public ResponseEntity<Long> getORCreateChatRoom(String loginEmail, String friendEmail, Long requestRoomId){
        // room id가 존재하는 경우엔 update하고 들어가고, 없는 경우에는 챗룸 생성하고, 업데이트 한다.. 가 맞겠지?
        // 그럼 룸 아이디가 널일때 생성하고, 나머지는 동일 로직으로 묶어야겠네.
        // 그 와중에 이름으로 바꿔야겠네

        if (requestRoomId != null){
            chatMessageService.updateChatMessagesReadStatus(requestRoomId, loginEmail);
            return new ResponseEntity<>(requestRoomId, HttpStatus.OK);
        }
        Map<String, Object> chatRoomIDAndOR = chatRoomService.getORCreateChatRoomID(loginEmail, friendEmail);
        Long roomId = (Long) chatRoomIDAndOR.get("roomId");
        if ((boolean) chatRoomIDAndOR.get("OR")) {
            chatUserService.register(loginEmail, roomId);
            chatUserService.register(friendEmail, roomId);
        }
        chatMessageService.updateChatMessagesReadStatus(roomId, loginEmail);
        return new ResponseEntity<>(roomId, HttpStatus.OK);
    }

    @PostMapping("/chat/selectChatMessageUp")
    public ResponseEntity selectChatMessageUp(PageRequestDTO pageRequestDTO, Long roomId, String loginEmail){
        ChatMessageResponse chatMessageResponse = chatMessageService.selectChatMessageUp(pageRequestDTO, roomId, loginEmail);
        return ResponseEntity.ok(chatMessageResponse);
    }

    @PostMapping("/chat/selectChatMessageDown")
    public ResponseEntity selectChatMessageDown(PageRequestDTO pageRequestDTO, Long roomId, String loginEmail) {
        ChatMessageResponse chatMessageResponse = chatMessageService.selectChatMessageDown(pageRequestDTO, roomId, loginEmail);
        return ResponseEntity.ok(chatMessageResponse);
    }

    @PostMapping("/chat/getEmailAndNameByRoomId")
    public ResponseEntity<List<Object[]>> getEmailAndNameByRoomId(Long roomId){
        List<Object[]> result = chatUserService.getEmailAndName(roomId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/chat/selectChatRoomUsers")
    public ResponseEntity selectChatRoomUsers(Long roomId){
        UserInfoResponse result = chatUserService.selectChatRoomUsers(roomId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/chat/selectChatRoom")
    public ResponseEntity selectChatRoom(ProfilePageRequestDTO profilePageRequestDTO, String loginEmail){
        ChatRoomResponse result = chatUserService.getProfileAndUseByLoginNamePage(profilePageRequestDTO, loginEmail);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/chat/updateDisConnectTime")
    public ResponseEntity<String> updateDisconnectTime(Long roomId, String loginEmail){
        chatUserService.updateDisConnect(roomId, loginEmail);
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

    @PostMapping("/chat/getNotReadNum")
    public ResponseEntity<Long> getNotReadNum(String loginEmail, Long roomId){
        Long resultNum = chatMessageService.getNotReadNum(loginEmail, roomId);
        return new ResponseEntity<>(resultNum, HttpStatus.OK);
    }

    @PostMapping("/chat/updateUserAndRoom")
    public ResponseEntity<Long> updateUserAndRoom(@RequestParam List<String> userEmails, Long roomId, Long addNum){
        chatUserService.insertChatUser(userEmails, roomId);
        chatRoomService.updateUserNum(roomId, addNum);
        return new ResponseEntity<>(1L, HttpStatus.OK);
    }

    @PostMapping("/chat/getDisConnectTime")
    public ResponseEntity<LocalDateTime> getDissConnectTime(Long roomId, String loginEmail){
        LocalDateTime result = chatUserService.getDisConnectTime(roomId, loginEmail);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
