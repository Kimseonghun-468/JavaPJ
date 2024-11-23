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
    public ResponseEntity request(String userName){
        boolean result = friendShipService.createFriendShip(userName);
        return ApiResponse.OK(result);
    }

    @PostMapping("/acceptFriend")
    public ResponseEntity acceptFriend(String userName){
        boolean result = friendShipService.acceptFriendShip(userName);
        return ApiResponse.OK(result);
    }

    @PostMapping("/deleteFriend")
    public ResponseEntity deleteFriend(String userName){
        boolean result = friendShipService.deleteFriendShip(userName);
        return ApiResponse.OK(result);
    }
}
