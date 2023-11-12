package com.skhkim.instaclone.controller;


import com.skhkim.instaclone.dto.MemberDTO;
import com.skhkim.instaclone.entity.ClubMemberRole;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("")
    public String singupMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
             MemberDTO memberDTO,
             RedirectAttributes redirectAttributes){
        log.info("@@@@@@@@@@");
//        ClubMemberRole role = new ClubMemberRole();
        log.info("authDTO : " +clubAuthMemberDTO);
        log.info("memberDTO : " + memberDTO);
//: memberDTO : MemberDTO(email=admin@faw, name=admin, password=214, regDate=null, modDate=null)
        // 회원 가입을 위해서는 email(id), passowrd, name (자동 기입으로는, Roleset, fromSocial을 처리한다..)

        return "redirect:/sidebar";

    }
    @GetMapping({"/signup",""})
    public void signup(){

    }


}
