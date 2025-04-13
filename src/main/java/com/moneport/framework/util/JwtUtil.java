package com.moneport.framework.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;

/**
 * @Project_Name : moneport
 * @Package_Name : com.moneport.framework.util
 * @Class_Name : JwtUtil.java
 * @Description : JWT 유틸
 * @author : djmoon
 * @since 2025-04-13 오후 13:25
 * @version 1.0
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * <p>
     * 토큰 생성
     * </p>
     *
     * @since 2025-04-13 오후 13:35
     * @author djmoon
     ***********************************************************
     * @modi name :
     * @modi date :
     * @modi desc :
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)       // 유저 식별자 설정
                .setIssuedAt(new Date())    // 토큰 발급 시각 설정
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 토큰 만료 시각 설정
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256) // 서명알고리즘 및 비밀키 설정
                .compact();     // 최종적으로 JWT 문자열 생성
    }

    /**
     * <p>
     * 토큰에서 유저이름 조회
     * </p>
     *
     * @since 2025-04-13 오후 13:35
     * @author djmoon
     ***********************************************************
     * @modi name :
     * @modi date :
     * @modi desc :
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())       // 검증에 사용할 비밀키 지정
                .build()
                .parseClaimsJws(token)      //토큰 파싱 및 검증
                .getBody()
                .getSubject();      // subject 값 반환
    }
    
    /**
     * <p>
     * 토큰 검증
     * </p>
     *
     * @since 2025-04-13 오후 13:35
     * @author djmoon
     ***********************************************************
     * @modi name :
     * @modi date :
     * @modi desc :
     */
     public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token);     // 서명 검증 및 만료 확인
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * <p>
     * 사용자 인증 객체 반환
     * </p>
     *
     * @since 2025-04-13 오후 14:39
     * @author djmoon
     ***********************************************************
     * @modi name :
     * @modi date :
     * @modi desc :
     */
    public Authentication getAuthentication(String token) {
        String username = getUsernameFromToken(token);
        return new UsernamePasswordAuthenticationToken(username, "", List.of());
    }

}
