package com.skhkim.instaclone.controller;


import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member/")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;
    @PostMapping("")
    public String singupMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
             ClubMemberDTO memberDTO,
             RedirectAttributes redirectAttributes){
        log.info("@@@@@@@@@@");
//        ClubMemberRole role = new ClubMemberRole();
        log.info("authDTO : " +clubAuthMemberDTO);
        log.info("memberDTO : " + memberDTO);

        boolean checkResult = loginService.checkDuplication(memberDTO);

        log.info("checkResult : " + checkResult);

        if(checkResult){
            log.info("Email이 중복되었습니다.");
        }

        else{
            loginService.register(memberDTO);
        }
        return "redirect:/sidebar";

    }
    @GetMapping({"/signup",""})
    public void signup(){

    }


}
