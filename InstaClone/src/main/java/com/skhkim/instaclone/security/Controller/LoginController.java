package com.skhkim.instaclone.security.Controller;

import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    @GetMapping("/signup")
    public void loginPage(String error, Model model){
        if ( error != null ){
            model.addAttribute("signUpError", true);
        }
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
    public String singupMember(ClubMemberDTO memberDTO){
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
