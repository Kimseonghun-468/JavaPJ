package com.skhkim.instaclone.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User implements OAuth2User {
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    private Map<String, Object> attr;
    public ClubAuthMemberDTO(String userEmail, String password,
                             String userName,
                             boolean fromSocial, Collection<? extends GrantedAuthority> authorities,
                             Map<String, Object> attr) {
        super(userEmail, password, authorities);
        this.email = userEmail;
        this.name = userName;
        this.password = password;
        this.fromSocial = fromSocial;
        this.attr = attr;
    }

    public ClubAuthMemberDTO(String userEmail, String password,
                             String userName,
                             Collection<? extends GrantedAuthority> authorities) {
        super(userEmail, password, authorities);
        this.name = userName;
        this.password = password;
        this.email = userEmail;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}

