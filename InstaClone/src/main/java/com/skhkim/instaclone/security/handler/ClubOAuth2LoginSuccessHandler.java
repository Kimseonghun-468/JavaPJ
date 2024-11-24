package com.skhkim.instaclone.security.handler;

import com.skhkim.instaclone.security.Utils.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class ClubOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;

    @Value("${password.reset.key}")
    private String passwordResetKey;
    @Autowired
    private JwtUtils jwtUtils;

    public ClubOAuth2LoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
        throws IOException, ServletException{

        ClubAuthMemberDTO authMember = (ClubAuthMemberDTO)authentication.getPrincipal();
        boolean fromSocial = authMember.isFromSocial();

        boolean passwordResult = passwordEncoder.matches(passwordResetKey, authMember.getPassword());
        if(fromSocial && passwordResult){
            String token = jwtUtils.generateToken(authMember.getEmail());
            String targetUrl = "http://localhost:8080/login/oauth2/google?token=" + token + "&name="+authMember.getName() + "&modify=true";
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }
        else {
            String token = jwtUtils.generateToken(authMember.getEmail());
            String targetUrl = "http://localhost:8080/login/oauth2/google?token=" + token + "&name="+authMember.getName();
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }
    }

}
