package com.skhkim.instaclone.chatting.controller;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.event.ChatRoomSessionManager;
import com.skhkim.instaclone.chatting.request.InviteRequest;
import com.skhkim.instaclone.chatting.request.MessageRequest;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;
import com.skhkim.instaclone.chatting.response.ChatRoomResponse;
import com.skhkim.instaclone.chatting.service.ChatMessageService;
import com.skhkim.instaclone.chatting.service.ChatRoomService;
import com.skhkim.instaclone.chatting.service.ChatUserService;
import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.request.MessagePageRequest;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final ChatUserService chatUserService;
    private final ChatRoomSessionManager chatRoomSessionManager;
    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/chat/sendMessage")
    public ResponseEntity sendMessage(@RequestBody MessageRequest request) {
        Long userNum = chatRoomService.getUserNum(request.getRoomId());
        int readStatus = userNum.intValue() - chatRoomSessionManager.getRoomJoinNum(request.getRoomId().toString());
        request.getChatMessageDTO().setSenderEmail(LoginContext.getUserInfo().getUserEmail());
        request.getChatMessageDTO().setReadStatus((long) readStatus);
        request.getChatMessageDTO().setRoomId(request.getRoomId());
        chatMessageService.register(request.getChatMessageDTO());
        chatRoomService.updateLastChatTime(request.getChatMessageDTO());
        redisTemplate.convertAndSend("/chat/"+ request.getRoomId(), request.getChatMessageDTO());

        return ApiResponse.OK();
    }

    @MessageMapping("/chat/accessLoad/{roomID}")
    @SendTo("/topic/chat/accessLoad/{roomID}")
    public ChatUserDTO accessLoad(@DestinationVariable Long roomId) {
        ChatUserDTO result = chatUserService.selectChatUser(roomId);
        return result;
    }

    @PostMapping("/chat/getORCreateChatRoom")
    public ResponseEntity<Long> getORCreateChatRoom(String loginEmail, String friendEmail, Long requestRoomId){
        // room id가 존재하는 경우엔 update하고 들어가고, 없는 경우에는 챗룸 생성하고, 업데이트 한다.. 가 맞겠지?
        // 그럼 룸 아이디가 널일때 생성하고, 나머지는 동일 로직으로 묶어야겠네.
        // 그 와중에 이름으로 바꿔야겠네

        if (requestRoomId != null){
            chatMessageService.updateChatMessagesReadStatus(requestRoomId);
            return new ResponseEntity<>(requestRoomId, HttpStatus.OK);
        }
        Map<String, Object> chatRoomIDAndOR = chatRoomService.getORCreateChatRoomID(loginEmail, friendEmail);
        Long roomId = (Long) chatRoomIDAndOR.get("roomId");
        if ((boolean) chatRoomIDAndOR.get("OR")) {
            chatUserService.register(loginEmail, roomId);
            chatUserService.register(friendEmail, roomId);
        }
        chatMessageService.updateChatMessagesReadStatus(roomId);
        return new ResponseEntity<>(roomId, HttpStatus.OK);
    }
    @PostMapping("/chat/selectChatRoomUsers")
    public ResponseEntity selectChatRoomUsers(Long roomId){
        List<ChatUserDTO> result = chatUserService.selectChatRoomUsers(roomId);
        chatMessageService.updateChatMessagesReadStatus(roomId);
        return ApiResponse.OK(result);
    }

    @PostMapping("/chat/selectChatMessageUp")
    public ResponseEntity selectChatMessageUp(MessagePageRequest replyPageRequest, Long roomId){
        ChatMessageResponse chatMessageResponse = chatMessageService.selectChatMessageUp(replyPageRequest, roomId);
        return ApiResponse.OK(chatMessageResponse);
    }

    @PostMapping("/chat/selectChatMessageDown")
    public ResponseEntity selectChatMessageDown(MessagePageRequest replyPageRequest, Long roomId) {
        ChatMessageResponse chatMessageResponse = chatMessageService.selectChatMessageDown(replyPageRequest, roomId);
        return ApiResponse.OK(chatMessageResponse);
    }

    @PostMapping("/chat/getEmailAndNameByRoomId")
    public ResponseEntity<List<Object[]>> getEmailAndNameByRoomId(Long roomId){
        List<Object[]> result = chatUserService.getEmailAndName(roomId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/chat/selectChatRoom")
    public ResponseEntity selectChatRoom(UserInfoPageRequest userInfoPageRequest){
        ChatRoomResponse result = chatUserService.getProfileAndUseByLoginNamePage(userInfoPageRequest);
        return ApiResponse.OK(result);
    }

    @PostMapping("/chat/updateDisConnectTime")
    public ResponseEntity updateDisconnectTime(Long roomId){
        chatUserService.updateDisConnect(roomId);
        return ApiResponse.OK("성공");
    }

    @PostMapping("/chat/getNotReadNum")
    public ResponseEntity getNotReadNum(Long roomId){
        Long resultNum = chatMessageService.getNotReadNum(roomId);
        return ApiResponse.OK(resultNum);
    }


    @PostMapping("/chat/updateUserAndRoom")
    public ResponseEntity updateUserAndRoom(@RequestBody InviteRequest inviteRequest){
        chatUserService.insertChatUser(inviteRequest.getUserEmails(), inviteRequest.getRoomId());
        chatRoomService.updateUserNum(inviteRequest.getRoomId(), inviteRequest.getAddNum());

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .roomId(inviteRequest.getRoomId())
                .inviteNames(inviteRequest.getUserNames().toString())
                .inviterName(LoginContext.getUserInfo().getUserName())
                .regDate(LocalDateTime.now())
                .build();
        chatMessageService.register(chatMessageDTO);
        return ApiResponse.OK();
    }

    @PostMapping("/chat/broadInviteUsers")
    public ResponseEntity broadInviteUsers(@RequestBody InviteRequest inviteRequest){
        redisTemplate.convertAndSend("/invite/"+ inviteRequest.getRoomId(), inviteRequest);
        return ApiResponse.OK();
    }

}
