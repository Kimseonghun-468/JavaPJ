package com.skhkim.instaclone.security.Controller;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private final ClubMemberRepository clubMemberRepository;
    @GetMapping("/signup")
    public void loginPage(String error, Model model){
        if ( error != null ){
            model.addAttribute("signUpError", true);
        }
    }
    @GetMapping("/oauth2/google")
    public void oauth2Login(){

    }
    @PostMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(String email) {
        return new ResponseEntity<>(loginService.checkEmail(email), HttpStatus.OK);
    }

    @PostMapping("/checkName")
    public ResponseEntity<Boolean> checkName(String name) {
        return new ResponseEntity<>(loginService.checkName(name), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public String singupMember(@RequestBody ClubMemberDTO memberDTO){
        log.info("memberDTO : " + memberDTO);
        boolean checkResult = loginService.checkDuplication(memberDTO);
        log.info("checkResult : " + checkResult);
        if(checkResult){
            log.info("이름이나 이메일이 중복");
            return "redirect:/login/signup?error";
        }
        else{
            loginService.register(memberDTO);
            return "redirect:/login";
        }
    }
    @PostMapping("/changeName")
    @PreAuthorize("hasRole('USER')")
    public String changeName(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
                             ClubMemberDTO memberDTO, String loadName) throws UnsupportedEncodingException {
        boolean checkResult = clubMemberRepository.existsByName(memberDTO.getName());
        if (!clubAuthMemberDTO.getName().equals(loadName)){
            return "Post-Error";
        }
        if (checkResult){
            String encodedloadName = URLEncoder.encode(loadName, "UTF-8");
            return "redirect:/userinfo/"+encodedloadName+"?nameError";
        }
        else{
            loginService.updateUserName(memberDTO.getName(), loadName);
            String encodedName = URLEncoder.encode(memberDTO.getName(), "UTF-8");
            return "redirect:/userinfo/"+encodedName;
        }
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('USER')")
    public String changePassword(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO,
                                 ClubMemberDTO memberDTO, String newPassword) throws UnsupportedEncodingException{
        boolean checkResult = loginService.checkPassword(memberDTO);
        if (!clubAuthMemberDTO.getName().equals(memberDTO.getName())){
            return "Post-Error";
        }
        if (!checkResult){
            String encodedName = URLEncoder.encode(memberDTO.getName(), "UTF-8");
            return "redirect:/userinfo/"+encodedName+"?psError";
        }
        else{
            loginService.updatePassword(memberDTO.getName(), newPassword);
            return "redirect:/login?logout";
        }
    }



    @GetMapping("")
    public void test(String error, String logout, Model model){
        if ( error != null ){
            model.addAttribute("loginError", true);
        }
        if (logout != null) {
            model.addAttribute("logoutFlag", true);
        }
    }
}
