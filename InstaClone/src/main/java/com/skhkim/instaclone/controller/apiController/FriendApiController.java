package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.response.ApiResponse;
import com.skhkim.instaclone.service.FriendShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend")
public class FriendApiController {

    private final FriendShipService friendShipService;
    @PostMapping("/request")
    public ResponseEntity sendFriendShipRequest(String name){
        boolean result = friendShipService.createFriendShip(name);
        return ApiResponse.OK(result);
    }

    @PostMapping("/acceptFriendShipRequest")
    public ResponseEntity accecptFriendShipRequest(String loginName, String requesterName){
        boolean result = friendShipService.acceptFriendShip(requesterName, loginName);
        return ApiResponse.OK(result);
    }

    @PostMapping("/deleteFriendShipRequest")
    public ResponseEntity deleteFriendShipRequest(String loginName, String requesterName){
        boolean result = friendShipService.deleteFriendShip(requesterName, loginName);
        return ApiResponse.OK(result);
    }
}
