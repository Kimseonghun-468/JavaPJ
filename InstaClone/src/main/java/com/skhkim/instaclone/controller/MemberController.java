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
        // True로 나오면 중복 있는거임
        // False로 나오면 중복 없는거임
        // 근데 여기서 중복처리를 확인할게 아니라 회원 가입중 알아서 중복처리하고 이 로직을 생각해볼 필요가 있을거 같음.


        if(checkResult){
            log.info("Email이 중복되었습니다.");
        }

        else{
            loginService.register(memberDTO);
        }



        // 1. 기존 멤버와 iD가 겹치는게 있는지 확인
        // 2. 없으면, 저장하고 회원 가입 처리
        // redirect는 home으로 or lgoin으로 하자, home으로 하는 경우, 로그인까지 자동으로 되어야함..
        // 회원 아이디가 이미 존재합니다는 script로 띄우고
//: memberDTO : MemberDTO(email=admin@faw, name=admin, password=214, regDate=null, modDate=null)
        // 회원 가입을 위해서는 email(id), passowrd, name (자동 기입으로는, Roleset, fromSocial을 처리한다..)

        return "redirect:/sidebar";

    }
    @GetMapping({"/signup",""})
    public void signup(){

    }


}
