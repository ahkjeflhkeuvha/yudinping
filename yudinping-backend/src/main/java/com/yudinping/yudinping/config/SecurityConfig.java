package com.yudinping.yudinping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> {}) // CORS 허용
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/ws/**", "/auth/**", "/chat/**").permitAll() // 로그인 요청들 허용
            .anyRequest().authenticated()
        )
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint((request, response, authException) -> {
                // 로그인 안 되어 있을 때 JSON으로 401 응답
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"로그인이 필요합니다.\"}");
            })
        )
        .formLogin(form -> form.disable()) // 👉 이거 중요!!
        .httpBasic(httpBasic -> httpBasic.disable()) // 👉 이것도 꺼주는 게 좋음
        .logout(logout -> logout.disable()) // 필요 없으면 꺼도 됨
        .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
