package com.skhkim.instaclone.controller;


import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    @GetMapping({"/modify",""})
    @PreAuthorize("hasRole('USER')")
    public void signup(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
                       Model model){
        boolean checkPassowrd =  passwordEncoder.matches("PasswordReset?fjaowifjaiofawjpoif$*!fajnk", clubAuthMemberDTO.getPassword());
        model.addAttribute("checkPassword", checkPassowrd);
        model.addAttribute("memberDTO", clubAuthMemberDTO);


    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/socialChange")
    public String socialChange(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
                               ClubMemberDTO memberDTO, String newPassword) throws UnsupportedEncodingException {
        boolean checkResult = clubMemberRepository.existsByName(memberDTO.getName());
        if (checkResult){
            return "redirect:/member/modify?nameError";
        }
        else{
            loginService.updatePassword(clubAuthMemberDTO.getName(), newPassword);
            loginService.updateUserName(memberDTO.getName(),clubAuthMemberDTO.getName());
            String encodedName = URLEncoder.encode(memberDTO.getName(), "UTF-8");
            return "redirect:/sidebar/"+encodedName;
        }
    }
}
