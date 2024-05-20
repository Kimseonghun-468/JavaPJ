package com.skhkim.instaclone.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhkim.instaclone.security.Utils.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class ClubLoginFormSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    public ClubLoginFormSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        ClubAuthMemberDTO authMember = (ClubAuthMemberDTO) authentication.getPrincipal();
        String token = jwtUtils.generateToken(authMember.getName());

        // JSON 객체 생성
        Map<String, String> responseData = new HashMap<>();
        responseData.put("token", token);
        responseData.put("name", authMember.getName());

        // JSON 응답 작성
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseData));
        response.getWriter().flush();
    }


}
//
