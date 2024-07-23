package com.ssafy.api.service;

import com.ssafy.common.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenHandleServiceImpl implements TokenHandleService {

    private final JwtUtil jwtUtil;

    public TokenHandleServiceImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Optional<Long> getMemberId(String token) {

        // 전달 받은 토큰의 접두사 처리
        String accessToken = token.replace("Bearer ", "");

        // 토큰의 유효성 검사
        if (jwtUtil.validateToken(accessToken)) {
            // 토큰이 유효하다면 전달받은 토큰에 해당하는 사용자의 식별자를 반환
            return Optional.of(jwtUtil.extractId(accessToken));
        } else {
            return Optional.empty();
        }
    }
}
