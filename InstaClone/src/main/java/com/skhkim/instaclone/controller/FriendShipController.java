package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.FriendShipDTO;
import com.skhkim.instaclone.service.FriendShipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller

@Log4j2
@RequiredArgsConstructor
public class FriendShipController {
    private final FriendShipService friendShipService;
//    @PostMapping("{name}")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/friendShip/{name}")
    public ResponseEntity<String> sendFriendShipRequest(@PathVariable("name") String name){
        log.info("sendFriendShopRequest : " + name);
        String result = friendShipService.createFriendShip(name);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/friendShip/acceptFriendShipRequest")
    public ResponseEntity<String> accecptFriendShipRequest(String loginName, String requesterName){
        log.info("Accept FriendShip Request");
        String result = friendShipService.acceptFriendShip(requesterName, loginName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/friendShip/deleteFriendShipRequest")
    public ResponseEntity<String> deleteFriendShipRequest(String loginName, String requesterName){
        log.info("Delete FriendShip Request");
        String result = friendShipService.deleteFriendShip(requesterName, loginName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
