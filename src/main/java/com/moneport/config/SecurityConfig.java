package com.moneport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Project_Name : moneport
 * @Package_Name : com.moneport.config
 * @Class_Name : SecurityConfig.java
 * @Description : 스프링 시큐리티 설정
 * @author : djmoon
 * @since 2025-04-10 오전 10:36
 * @version 1.0
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable()) // CSRF 끄기
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // iframe 허용
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/h2-console/**")
                    .permitAll()
                    .anyRequest()
                    .permitAll()
            );

        return http.build();
    }

}
