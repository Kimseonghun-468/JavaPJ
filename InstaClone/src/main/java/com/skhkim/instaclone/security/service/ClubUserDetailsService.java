package com.skhkim.instaclone.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.security.dto.ClubAuthMemberDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        log.info("ClubUserDetailsService loadUserbyUsername " + username);

        Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check Email or social");
        }

        ClubMember clubMember = result.get();
        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.getName(),
                clubMember.getRoleSet().stream().map(role ->
                        new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toSet())
        );
        clubAuthMember.setFromSocial(clubMember.isFromSocial());
        return clubAuthMember;
    }
}