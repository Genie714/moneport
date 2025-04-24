package com.moneport.framework.config;

import com.moneport.framework.filter.JwtAuthenticFilter;
import com.moneport.framework.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    private static final List<String> permitAllUrls  = List.of(
        "/h2-console/**",
        "/api/auth/**",
        "/api/users",
        "/swagger-ui/**",
        "/api-docs/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable()) // CSRF 끄기
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // iframe 허용
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(permitAllUrls.toArray(new String[0]))
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )
            .addFilterBefore(new JwtAuthenticFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
            .cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // 프론트 주소
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // JWT 헤더용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
