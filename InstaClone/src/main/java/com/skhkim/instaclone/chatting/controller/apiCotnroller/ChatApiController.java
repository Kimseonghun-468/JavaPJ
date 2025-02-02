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

    /**
     * Logic : Message DB 저장 및 Redis BroadCasting
     * Request : roomId, chatMessageDTO[content]
     * Response : null
     * */
    @PostMapping("/sendMessage")
    public ResponseEntity sendMessage(@RequestBody MessageRequest request) {
        try {
            chatService.sendMessage(request);
        } catch (Exception e){
            return ApiResponse.ERROR(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ApiResponse.OK();
    }

    /**
     * Logic : ChatRoom 생성 or ChatRoomId 반환
     * Request : userName
     * Response : roomId
     * */
    @PostMapping("/createChatRoom")
    public ResponseEntity createChatRoom(String userName){
        Long roomId = chatService.createChatRoom(userName);
        return ApiResponse.OK(roomId);
    }

    /**
     * Logic : 채팅방 참여
     * Request : roomId
     * Response : List<ChatUserDTO>
     * Todo : Eamil 노출 제거 및 UserProfile 개별적 불러오기 (성능 보다는 정보 유출을 더 막아야함)
     * */
    @PostMapping("/joinChatRoom")
    public ResponseEntity joinChatRoom(MessageRequest request){
        List<ChatUserDTO> result = chatService.joinChatRoom(request);
        return ApiResponse.OK(result);
    }

    /**
     * Logic : 사용자의 채팅방 리스트 조회
     * Request : Page
     * Response : ChatRoomResponse[chatRoomDTOS, hasNext]
     * */
    @PostMapping("/selectChatRoom")
    public ResponseEntity selectChatRoom(UserInfoPageRequest userInfoPageRequest){
        ChatRoomResponse result = chatUserService.selectChatRooms(userInfoPageRequest);
        return ApiResponse.OK(result);
    }

    /**
     * Logic : 채팅방의 읽지 않은 채팅 기록 조회
     * Request : page, roomId, dType
     * Response : ChatMessageResponse[chatMessageDTOS, hasNext]
     * */
    @PostMapping("/selectChatMessages")
    public ResponseEntity selectChatMessage(MessagePageRequest request){
        ChatMessageResponse chatMessageResponse = chatMessageService.selectChatMessages(request);
        return ApiResponse.OK(chatMessageResponse);
    }

    /**
     * Logic : 채팅방의 마지막 CID 기록 - 읽지 않은 메시지 판별을 위한 보조 데이터
     * Request : roomId
     * Response : null
     * */
    @PostMapping("/updateDisConnectCid")
    public ResponseEntity updateDisconnectCid(Long roomId){
        chatUserService.updateDisConnectCid(roomId);
        return ApiResponse.OK("성공");
    }

    /**
     * Logic : 채티방의 읽지 않은 메시지 수 출력
     * Request : roomId
     * Response : notReadNum
     * Todo : Fetch Join을 통해 읽지 않은 Message의 수를 List 단위로 처리
     * */
    @PostMapping("/getNotReadNum")
    public ResponseEntity getNotReadNum(MessageRequest request){
        Long resultNum = chatMessageService.getNotReadNum(request);
        return ApiResponse.OK(resultNum);
    }

    /**
     * Logic : 채팅방에 친구 초대 및 초대 목록 BroadCast
     * Request : roomId, userEmails, userNum - userNames는 사용하지 않음.
     * Response : null
     * Todo : userEmails를 Names로 교체하고 Ids로 변환
     * */
    @PostMapping("/inviteChatUsers")
    public ResponseEntity inviteChatUsers(@RequestBody InviteRequest inviteRequest){
        chatService.inviteChatUsers(inviteRequest);
        return ApiResponse.OK();
    }
}
