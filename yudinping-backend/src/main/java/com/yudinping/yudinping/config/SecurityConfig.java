package com.yudinping.yudinping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER")
                .requestMatchers("/user2/loginSuccess").hasAnyRole("USER3", "USER4", "USER5")
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/user2/login") // 커스텀 로그인 페이지
                .defaultSuccessUrl("/user2/loginSuccess") // 로그인 성공 후 이동 경로
                .failureUrl("/user2/login?success=100")   // 실패 시 이동 경로
                .usernameParameter("uid")                 // 로그인 폼 input name
                .passwordParameter("pass")                // 로그인 폼 input name
            )
            .logout(logout -> logout
                .invalidateHttpSession(true) // 세션 초기화
                .logoutUrl("/user2/logout") // 로그아웃 URL
                .logoutSuccessUrl("/user2/login?success=200") // 성공 후 URL
            )
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
