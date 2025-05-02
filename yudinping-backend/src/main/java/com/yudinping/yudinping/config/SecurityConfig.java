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
            .csrf(csrf -> csrf.disable()) // CSRF 끄기
            .authorizeHttpRequests(auth -> auth
                // .requestMatchers("/ws/**", "/user2/**", "/auth/**").permitAll() // 여기에 필요한 경로 허용
                .anyRequest().permitAll() // 나머지 요청은 모두 허용 (이 부분이 중요)
            )
            .formLogin(form -> form.disable()) // 로그인 기능 완전 비활성화
            .logout(logout -> logout.disable()) // 로그아웃 기능도 비활성화
            
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
