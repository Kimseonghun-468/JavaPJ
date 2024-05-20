package com.skhkim.instaclone.config;
import com.skhkim.instaclone.dto.ClubMemberDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.security.Utils.JwtUtils;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import com.skhkim.instaclone.security.service.ClubUserDetailsService;
import com.skhkim.instaclone.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private LoginService loginService;
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = null;
        try{
            authorizationHeader = request.getHeader("Cookie").split("Authorization=")[1].split(";")[0];
        } catch (Exception e){
            authorizationHeader = null;

        }
//        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            ClubMemberDTO clubMemberDTO = loginService.getClubMemberByName(username);
            ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                    clubMemberDTO.getEmail(),
                    clubMemberDTO.getPassword(),
                    clubMemberDTO.getName(),
                    clubMemberDTO.getRoleSet().stream().map(role ->
                            new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet())
            );
            if (jwtUtil.validateToken(jwt, clubMemberDTO.getName())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        clubAuthMember, null, clubAuthMember.getAuthorities());
//                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }
}