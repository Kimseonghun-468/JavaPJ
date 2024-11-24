package com.skhkim.instaclone.config;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.security.Utils.JwtUtils;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.service.EntityMapper;
import com.skhkim.instaclone.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtil;
    private final MemberService memberService;

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
            ClubMember clubMember = memberService.selectClubMember(email);
            ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                    clubMember.getEmail(),
                    clubMember.getPassword(),
                    clubMember.getName(),
                    clubMember.getRoleSet().stream().map(role ->
                            new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet())
            );
            if (jwtUtil.validateToken(jwt, clubMember.getEmail())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        clubAuthMember, null, clubAuthMember.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
                LoginContext.setUserInfo(EntityMapper.entityToDTO(clubMember));
            }
        }
        chain.doFilter(request, response);
    }
}