package com.skhkim.instaclone.config;

import com.nimbusds.oauth2.sdk.auth.JWTAuthentication;
import com.skhkim.instaclone.security.Utils.JwtUtils;
import com.skhkim.instaclone.security.handler.ClubLoginFormSuccessHandler;
import com.skhkim.instaclone.security.handler.FailHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.skhkim.instaclone.security.handler.ClubLoginSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Log4j2
//@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private final JwtUtils tokenProvider;

    public SecurityConfig(JwtUtils tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        log.info("-------------_Filter chin-------------");

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin((formLgoin)->formLgoin.loginPage("/login"));
        http.formLogin((formLogin) -> formLogin.successHandler(clubLoginFormSuccessHandler()));
        http.formLogin((formlogin) -> formlogin.failureHandler(failHandler()));

        http.csrf((csrf)-> csrf.disable());
//        http.logout(Customizer.withDefaults());

//        http.oauth2Login(Customizer.withDefaults())
        http.oauth2Login(oauth2Login -> oauth2Login.loginPage("/login/auth"));
//        http.oauth2Login(oauth2Login -> oauth2Login.successHandler(clubLoginSuccessHandler()));
        http.rememberMe(rememberMe-> {rememberMe.tokenValiditySeconds(60*60);
        });

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public ClubLoginSuccessHandler clubLoginSuccessHandler(){
        return new ClubLoginSuccessHandler(passwordEncoder());
    }
    @Bean
    public ClubLoginFormSuccessHandler clubLoginFormSuccessHandler(){
        return new ClubLoginFormSuccessHandler(passwordEncoder());
    }

    @Bean
    public FailHandler failHandler(){
        return new FailHandler();
    }
    @Bean
    public JwtFilter jwtAuthenticationFilter() {
        return new JwtFilter();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails user = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode("1111"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

}

