package com.skhkim.instaclone.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;

import java.io.IOException;

@Log4j2
public class ClubLoginFormSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;

    public ClubLoginFormSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException{

        ClubAuthMemberDTO authMember = (ClubAuthMemberDTO)authentication.getPrincipal();
        redirectStrategy.sendRedirect(request, response, "/sidebar/"+authMember.getName());
    }


}
//
//public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        AuthenticationException exception) throws IOException, ServletException {
//        // 로그인 실패 시 수행할 로직
//    }
//}