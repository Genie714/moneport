package com.moneport.backend.filter;

import com.moneport.framework.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @Project_Name : Moneport
 * @Package_Name : com.moneport.backend.filter
 * @Class_Name : JwtAuthenticFilter.java
 * @Description : SpringSecurity와 통합을 위한 Jwt 필터
 * @author : djmoon
 * @since 2025-04-13 오후 14:34
 * @version 1.0
 */
@RequiredArgsConstructor
public class JwtAuthenticFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    /**
     * <p>
     * jwt 핕터
     * </p>
     *
     * @since 2025-04-13 오후 14:36
     * @author djmoon
     ***********************************************************
     * @modi name :
     * @modi date :
     * @modi desc :
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request); //- 토큰가져오기

        if (token != null && jwtUtil.validateToken(token)) {
            Authentication auth = jwtUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // "Bearer "로 시작하고 길이가 충분히 긴 경우에만 처리
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }

        return null;
    }

}
