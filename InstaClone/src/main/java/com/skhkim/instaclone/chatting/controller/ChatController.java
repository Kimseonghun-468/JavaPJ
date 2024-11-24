package com.skhkim.instaclone.chatting.controller;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.event.ChatRoomSessionManager;
import com.skhkim.instaclone.chatting.request.InviteRequest;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;
import com.skhkim.instaclone.chatting.response.ChatRoomResponse;
import com.skhkim.instaclone.chatting.service.ChatMessageService;
import com.skhkim.instaclone.chatting.service.ChatRoomService;
import com.skhkim.instaclone.chatting.service.ChatUserService;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.dto.UserInfoDTO;
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
    private final ProfileImageRepository profileImageRepository;
    @MessageMapping("/chat/{roomID}")
    @SendTo("/topic/chat/{roomID}")
    public ChatMessageDTO sendMessage(@DestinationVariable String roomID, ChatMessageDTO chatMessageDTO) {
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
    public ChatUserDTO accessLoad(@DestinationVariable Long roomId, String loginName) {
        ChatUserDTO result = chatUserService.selectChatUser(roomId, loginName);
        return result;
    }


    // Invite할 때 -> Invite한 목록 Message에 저장 한번 하고,
    // Read Status를 -9999 로 넣고, 음수 체크 후 senderEmail을 기준으로 Name으로 변환한다음
    // 이 기준을 가지고 OOO님이 입장하셨습니다 로 퉁치자
    @MessageMapping("/chat/inviteLoad/{roomID}")
    @SendTo("/topic/chat/inviteLoad/{roomID}")
    public ResponseEntity inviteLoad(@RequestBody InviteRequest inviteRequest) {
        List<UserInfoDTO> result = chatUserService.selectChatUserList(inviteRequest.getRoomId(), inviteRequest.getUserNames());


        return ResponseEntity.ok(result);
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
    @PostMapping("/chat/selectChatRoomUsers")
    public ResponseEntity selectChatRoomUsers(Long roomId, String loginEmail){
        UserInfoResponse result = chatUserService.selectChatRoomUsers(roomId);
        chatMessageService.updateChatMessagesReadStatus(roomId, loginEmail);
        return ResponseEntity.ok(result);
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

    @PostMapping("/chat/selectChatRoom")
    public ResponseEntity selectChatRoom(UserInfoPageRequest userInfoPageRequest, String loginEmail){
        ChatRoomResponse result = chatUserService.getProfileAndUseByLoginNamePage(userInfoPageRequest, loginEmail);
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
    public ResponseEntity updateUserAndRoom(@RequestBody InviteRequest inviteRequest){
        chatUserService.insertChatUser(inviteRequest.getUserEmails(), inviteRequest.getRoomId());
        chatRoomService.updateUserNum(inviteRequest.getRoomId(), inviteRequest.getAddNum());
        return ResponseEntity.ok(true);
    }

}
