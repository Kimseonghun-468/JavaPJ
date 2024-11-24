package com.skhkim.instaclone.controller.apiController;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.dto.UserInfoDTO;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/")
@Log4j2
public class MemberApiController {
    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    @Value("${password.reset.key")
    private String passwordResetKey;
    @PostMapping("")
    public String singupMember(ClubMemberDTO memberDTO){

        boolean checkResult = memberService.checkDuplication(memberDTO);

        log.info("checkResult : " + checkResult);

        if(checkResult){
            log.info("Email이 중복되었습니다.");
        }

        else{
            memberService.register(memberDTO);
        }
        return "redirect:/sidebar";

    }
//    @GetMapping({"/modify",""})
//
//    public void signup(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
//                       Model model){
//
//        boolean checkPassowrd =  passwordEncoder.matches(passwordResetKey, clubMemberRepository.findByName(clubAuthMemberDTO.getName()).getPassword());
//        model.addAttribute("checkPassword", checkPassowrd);
//        model.addAttribute("memberDTO", clubAuthMemberDTO);
    // Password를 그대로 뱉는 문제가 있음 지금.. 이거 MemberDTO에서 왜 전부다 받아서 쓰는지 확인하고 수정해야함.
//    }


    @PostMapping("/socialChange")
    public String socialChange(ClubMemberDTO memberDTO, String newPassword){
        boolean checkResult = clubMemberRepository.existsByName(memberDTO.getName());
        if (checkResult){
            return "redirect:/member/modify?nameError";
        }
        else{
            String loginName = LoginContext.getUserInfo().getUserName();
            memberService.updatePassword(loginName, newPassword);
            memberService.updateUserName(memberDTO.getName(),loginName);
            String encodedName = URLEncoder.encode(memberDTO.getName(), StandardCharsets.UTF_8);
            return "redirect:/sidebar/"+encodedName;
        }
    }

    @PostMapping("/selectUserInfo")
    public ResponseEntity selectUserInfo(String userName){
        UserInfoDTO result = memberService.selectUserInfo(userName);
        return ResponseEntity.ok(result);
    }
}
