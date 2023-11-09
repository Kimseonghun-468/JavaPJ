package org.zerock.club.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.zerock.club.security.handler.ClubLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        log.info("-------------_Filter chin-------------");

//        http.authorizeHttpRequests((auth -> {
//            auth.requestMatchers("/sample/all").permitAll();
//            auth.requestMatchers("/sample/member").hasAnyAuthority("ROLE_USER", "OAUTH2_USER");
//        }));

        http.formLogin(Customizer.withDefaults());
        http.csrf((csrf)-> csrf.disable());
        http.logout(Customizer.withDefaults());

//        http.oauth2Login(Customizer.withDefaults())
        http.oauth2Login(oauth2Login -> oauth2Login.successHandler(clubLoginSuccessHandler()));
        http.rememberMe(rememberMe-> {rememberMe.tokenValiditySeconds(60*60);
        });

        return http.build();
    }
    @Bean
    public ClubLoginSuccessHandler clubLoginSuccessHandler(){
        return new ClubLoginSuccessHandler(passwordEncoder());
//        return new ClubLoginSuccessHandler(passwordEncoder());
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

