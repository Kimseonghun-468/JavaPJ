package com.skhkim.instaclone.chatting.controller.apiCotnroller;

import com.skhkim.instaclone.chatting.dto.ChatUserDTO;
import com.skhkim.instaclone.chatting.request.InviteRequest;
import com.skhkim.instaclone.chatting.request.MessageRequest;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;
import com.skhkim.instaclone.chatting.response.ChatRoomResponse;
import com.skhkim.instaclone.chatting.service.ChatMessageService;
import com.skhkim.instaclone.chatting.service.ChatService;
import com.skhkim.instaclone.chatting.service.ChatUserService;
import com.skhkim.instaclone.request.MessagePageRequest;
import com.skhkim.instaclone.request.UserInfoPageRequest;
import com.skhkim.instaclone.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatApiController {
    private final ChatMessageService chatMessageService;
    private final ChatUserService chatUserService;
    private final ChatService chatService;

    @PostMapping("/sendMessage")
    public ResponseEntity sendMessage(@RequestBody MessageRequest request) {
        try {
            chatService.sendMessage(request);
        } catch (Exception e){
            return ApiResponse.ERROR(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ApiResponse.OK();
    }

    @PostMapping("/createChatRoom")
    public ResponseEntity createChatRoom(String userName){
        Long roomId = chatService.createChatRoom(userName);
        return ApiResponse.OK(roomId);
    }

    @PostMapping("/joinChatRoom")
    public ResponseEntity joinChatRoom(Long roomId){
        List<ChatUserDTO> result = chatService.joinChatRoom(roomId);
        return ApiResponse.OK(result);
    }


    @PostMapping("/selectChatRoom")
    public ResponseEntity selectChatRoom(UserInfoPageRequest userInfoPageRequest){
        ChatRoomResponse result = chatUserService.selectChatRooms(userInfoPageRequest);
        return ApiResponse.OK(result);
    }

    @PostMapping("/selectChatMessageUp")
    public ResponseEntity selectChatMessageUp(MessagePageRequest replyPageRequest, Long roomId){
        ChatMessageResponse chatMessageResponse = chatMessageService.selectChatMessageUp(replyPageRequest, roomId);
        return ApiResponse.OK(chatMessageResponse);
    }

    @PostMapping("/selectChatMessageDown")
    public ResponseEntity selectChatMessageDown(MessagePageRequest replyPageRequest, Long roomId) {
        ChatMessageResponse chatMessageResponse = chatMessageService.selectChatMessageDown(replyPageRequest, roomId);
        return ApiResponse.OK(chatMessageResponse);
    }

    @PostMapping("/updateDisConnectCid")
    public ResponseEntity updateDisconnectCid(Long roomId){
        chatUserService.updateDisConnectCid(roomId);
        return ApiResponse.OK("성공");
    }

    @PostMapping("/getNotReadNum")
    public ResponseEntity getNotReadNum(Long roomId){
        Long resultNum = chatMessageService.getNotReadNum(roomId);
        return ApiResponse.OK(resultNum);
    }

    @PostMapping("/inviteChatUsers")
    public ResponseEntity inviteChatUsers(@RequestBody InviteRequest inviteRequest){
        chatService.inviteChatUsers(inviteRequest);
        return ApiResponse.OK();
    }
}
