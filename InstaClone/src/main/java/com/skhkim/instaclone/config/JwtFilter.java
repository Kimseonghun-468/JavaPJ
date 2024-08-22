package com.skhkim.instaclone.config;
import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.security.Utils.JwtUtils;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.LoginService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtil;
    private final LoginService loginService;
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = null;
        try{
            authorizationHeader = request.getHeader("Cookie").split("Authorization=")[1].split(";")[0];
        } catch (Exception e){
            authorizationHeader = null;

        }
        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = jwtUtil.extractUsername(jwt);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            ClubMemberDTO clubMemberDTO = loginService.getClubMemberSearchbyEmail(email);
            ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                    clubMemberDTO.getEmail(),
                    "",
                    clubMemberDTO.getName(),
                    clubMemberDTO.getRoleSet().stream().map(role ->
                            new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet())
            );
            if (jwtUtil.validateToken(jwt, clubMemberDTO.getEmail())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        clubAuthMember, null, clubAuthMember.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }
}