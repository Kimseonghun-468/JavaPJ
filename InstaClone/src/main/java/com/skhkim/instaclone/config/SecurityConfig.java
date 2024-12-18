package com.skhkim.instaclone.config;

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
import com.skhkim.instaclone.security.handler.ClubOAuth2LoginSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfig corsConfig;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin((formLogin) -> formLogin.loginPage("/login"));
        http.formLogin((formLogin) -> formLogin.successHandler(clubLoginFormSuccessHandler()));
        http.formLogin((formLogin) -> formLogin.failureHandler(failHandler()));
        http.csrf((csrf) -> csrf.disable());
        http.cors((cors) -> cors.configurationSource(corsConfig.corsConfigurationSource()));
        http.cors(Customizer.withDefaults());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.oauth2Login((oauth2Login) -> oauth2Login.loginPage("/login/oauth2"));
        http.oauth2Login(oauth2Login -> oauth2Login.successHandler(clubLoginSuccessHandler()));
        return http.build();
    }

    @Bean
    public ClubOAuth2LoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubOAuth2LoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public ClubLoginFormSuccessHandler clubLoginFormSuccessHandler() {
        return new ClubLoginFormSuccessHandler(passwordEncoder());
    }
    @Bean
    public FailHandler failHandler() {
        return new FailHandler();
    }
}

