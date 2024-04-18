package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.*;
import com.skhkim.instaclone.entity.PostImage;
import com.skhkim.instaclone.repository.ProfileImageRepository;
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

import java.util.List;

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
    public String sidebar(@PathVariable("name") String name, PageRequestDTO pageRequestDTO,
                          @AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,

                          Model model){
        log.info("Sidebar...----");
        log.info("PageRequest DTO : " + pageRequestDTO);
        log.info("Sidebar name: " + name);
        String userEamil = postService.getEmailByUserName(name);
        ProfileImageDTO profileImageDTO = profileService.getProfileImage(name);

        String friendshipStatus = friendShipService.checkFriendShip(clubAuthMemberDTO.getEmail(), userEamil);
        model.addAttribute("friendshipStatus", friendshipStatus);
        model.addAttribute("userExist", loginService.getUserExist(name));
        model.addAttribute("result", postService.getList(pageRequestDTO, userEamil));
        model.addAttribute("profileImageDTO", profileImageDTO);
        model.addAttribute("memberDTO", clubAuthMemberDTO);
        model.addAttribute("userName", name);
        model.addAttribute("userEmail", userEamil);
        model.addAttribute("postNum", postService.getPostNumber(userEamil));
//        Member Table 조회해서 Name에 없으면 redirect 없는 페이지쪽으로 넘기고,
        return "sidebar";
    }

    @PostMapping("/sidebar/{name}")
    public String sidevar(@PathVariable("name") String name, PostDTO postDTO, RedirectAttributes redirectAttributes){
        log.info("PostDTO : " + postDTO);
        Long pno = postService.register(postDTO);
        redirectAttributes.addFlashAttribute("msg", pno);
        return "redirect:/sidebar/"+name;
    }

    @PostMapping("/sidebar/profileImage/{name}")
    public String profileImage(@PathVariable("name") String name, ProfileImageDTO profileImageDTO, RedirectAttributes redirectAttributes){
        log.info("Profile Image DTO : " + profileImageDTO);
        Long pfino = profileService.register(profileImageDTO);
        redirectAttributes.addFlashAttribute("msg", pfino);
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
    public void list(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO, PageRequestDTO pageRequestDTO, Model model){
        log.info("pageRequestDTO : " + pageRequestDTO);
        model.addAttribute("result", postService.getList(pageRequestDTO, clubAuthMemberDTO.getEmail()));

        log.info("What!");
    }


}
