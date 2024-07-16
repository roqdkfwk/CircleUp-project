package com.ssafy.common.util;

import com.ssafy.db.entity.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final Long expiration;

    // jwt.expiration = 1h
    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") Long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    // accessToken을 발급하는 메소드
    public String generateAccessToken(Long id, Role role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        // role, id, 만료 시간, refreshToken 여부
        return createToken(claims, id.toString(), expiration, false);
    }

    // refreshToken을 발급하는 메소드
    // refreshToken의 유효기간은 1h * 24 * 15 = 15days
    public String generateRefreshToken(Long id) {

        // role, id, 만료 시간, refreshToken 여부
        return createToken(new HashMap<>(), id.toString(), expiration * 24 * 15, true);
    }

    // 토큰을 생성하는 메소드
    private String createToken(Map<String, Object> claims, String subject, Long expiration, boolean isRefreshToken) {

        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256"); // 암호화 알고리즘
        header.put("typ", isRefreshToken ? "Refresh" : "JWT");  // 토큰의 타입을 결정

        return Jwts.builder()
                .setHeader(header)  // 헤더 설정 추가
                .setClaims(claims)  // payload에 role 정보 추가
                .setSubject(subject)    // 해당 토큰 사용자의 식별자 추가 
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 토큰 생성 시각
                .setExpiration(new Date(System.currentTimeMillis() + expiration))   // 토큰 만료 시각
                .signWith(key, SignatureAlgorithm.HS256)    // 암호화 알고리즘 명시
                .compact();
    }

    // 토큰에서 사용자 ID를 추출
    public Long extractId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    // 토큰에서 특정 클레임을 추출
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 토큰에서 모든 클레임을 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 토큰이 만료되었는지 확인
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 토큰의 만료 시간을 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 토큰이 유효한지 검증
    public Boolean validateToken(String token, Long id) {
        final Long extractedId = extractId(token);
        return (extractedId.equals(id) && !isTokenExpired(token));
    }

    // 주어진 토큰이 리프레시 토큰인지 확인
    public Boolean isRefreshToken(String token) {
        return extractExpiration(token).getTime() - System.currentTimeMillis() > expiration;
    }
}