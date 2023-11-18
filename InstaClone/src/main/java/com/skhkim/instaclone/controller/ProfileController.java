package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.PageRequestDTO;
import com.skhkim.instaclone.dto.ProfileDTO;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.LoginService;
import com.skhkim.instaclone.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    @GetMapping("/profile")
    public void profile(){
        log.info("Profile...-----");

    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sidebar")
    public void sidevar(PageRequestDTO pageRequestDTO, @AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO, Model model){
        log.info("Sidebar...----");
        log.info("ClubAuth DTO : " +clubAuthMemberDTO);
        log.info("PageRequest DTO : " + pageRequestDTO);

        model.addAttribute("memberDTO", clubAuthMemberDTO);

    }

    @PostMapping("/sidebar")
    public String sidevar(ProfileDTO profileDTO, RedirectAttributes redirectAttributes){
        log.info("profileDTO : " + profileDTO);

        return "redirect:/sidebar";
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
