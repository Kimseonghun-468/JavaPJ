package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.FriendShipService;
import com.skhkim.instaclone.service.LoginService;
import com.skhkim.instaclone.service.PostService;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ProfileController {

    private final PostService postService;
    private final ProfileService profileService;
    private final LoginService loginService;
    private final FriendShipService friendShipService;
    @GetMapping("/profile")
    public void profile(){
        log.info("Profile...-----");

    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sidebar/{name}")
    public String sidebar(@PathVariable("name") String name, PostPageRequestDTO postPageRequestDTO,
                          @AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,

                          Model model){
        String userEamil = postService.getEmailByUserName(name);
        ProfileImageDTO profileImageDTO = profileService.getProfileImage(name);
        String friendshipStatus = friendShipService.checkFriendShip(clubAuthMemberDTO.getName(), name);
        model.addAttribute("friendshipStatus", friendshipStatus);
        model.addAttribute("userExist", loginService.getUserExist(name));
        model.addAttribute("result", postService.getList(postPageRequestDTO, name));
        model.addAttribute("profileImageDTO", profileImageDTO);
        model.addAttribute("memberDTO", clubAuthMemberDTO);
        model.addAttribute("userName", name);
        model.addAttribute("userEmail", userEamil);
        model.addAttribute("postNum", postService.getPostNumber(userEamil));
        model.addAttribute("friendNum", friendShipService.getFriendNum(name));
        return "sidebar";
    }

    @PostMapping("/sidebar/post/{name}")
    public String sidevar(@PathVariable("name") String name, PostDTO postDTO){
        log.info("PostDTO : " + postDTO);
        Long pno = postService.register(postDTO);
        return "redirect:/sidebar/"+name;
    }

    @PostMapping("/sidebar/profileImage/{name}")
    public String profileImage(@PathVariable("name") String name, ProfileImageDTO profileImageDTO){
        log.info("Profile Image DTO : " + profileImageDTO);
        Long pfino = profileService.register(profileImageDTO);
        return "redirect:/sidebar/"+name;
    }


    @GetMapping("/midle")
    public void midle(){
        log.info("midle...----");
    }
    @GetMapping("/srctest")
    public void srctest(){
        log.info("src...----");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public void list(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO, PostPageRequestDTO postPageRequestDTO, Model model){
        log.info("pageRequestDTO : " + postPageRequestDTO);
        model.addAttribute("result", postService.getList(postPageRequestDTO, clubAuthMemberDTO.getEmail()));

        log.info("What!");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("")
    public String defaultPage(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO){
        return "redirect:/sidebar/"+clubAuthMemberDTO.getName();
    }


}
