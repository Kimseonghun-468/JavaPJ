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
    public ResponseEntity<String> accecptFriendShipRequest(String loginEmail, String requesterEmail){
        log.info("Accept FriendShip Request");
        String result = friendShipService.acceptFriendShip(requesterEmail, loginEmail);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/friendShip/getFriendShipList")
    public ResponseEntity<List<FriendShipDTO>> getFriendShipList(String loginEmail){
        log.info("Get Friendship List");
        List<FriendShipDTO> friendShipDTOList = friendShipService.getFriendShipListStatusWaiting(loginEmail);
        log.info("Friendship DTO LIST : "+friendShipDTOList);
        return new ResponseEntity<>(friendShipDTOList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/friendShip/deleteFriendShipRequest")
    public ResponseEntity<String> deleteFriendShipRequest(String loginEmail, String requesterEmail){
        log.info("Delete FriendShip Request");
        String result = friendShipService.deleteFriendShip(requesterEmail, loginEmail);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
