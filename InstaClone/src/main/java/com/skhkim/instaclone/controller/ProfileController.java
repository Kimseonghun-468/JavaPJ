package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class ProfileController {

    @GetMapping("/profile")
    public void profile(){
        log.info("Profile...-----");

    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sidebar")
    public void sidevar(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO, Model model){
        log.info("Sidebar...----");
        log.info("ClubAuth DTO : " +clubAuthMemberDTO);
        model.addAttribute("memberDTO", clubAuthMemberDTO);
    }
    @GetMapping("/midle")
    public void midle(){
        log.info("midle...----");
    }
    @GetMapping("/srctest")
    public void srctest(){
        log.info("src...----");
    }
}
