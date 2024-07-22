package com.ssafy.api.service;

import com.ssafy.api.request.MemberLoginPostReq;
import com.ssafy.api.response.MemberLoginPostRes;
import com.ssafy.common.util.JwtUtil;
import com.ssafy.db.entity.Member;
import com.ssafy.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    // 로그인
    @Override
    public MemberLoginPostRes login(MemberLoginPostReq loginReq) {

        Member member = memberRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!member.getPw().equals(loginReq.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtUtil.generateAccessToken(member);
        String refreshToken = jwtUtil.generateRefreshToken(member.getId());

        // Refresh 토큰을 Member 엔티티에 저장
        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

        return MemberLoginPostRes.of(200, "로그인에 성공하였습니다.", accessToken, refreshToken, member.getName(), member.getEmail(), member.getRole());
    }

    // 로그아웃
    @Override
    public void logout(String token) {

        Long id = jwtUtil.extractId(token);

        // 해당 토큰이 유효하고 && 블랙리트스트에 등록되지 않은 토큰이라면
        // 블랙리스트에 등록
        if (jwtUtil.validateToken(token) && !tokenBlacklistService.isBlacklisted(token)) {
            tokenBlacklistService.addToBlacklist(token, jwtUtil.extractExpiration(token));
        } else {
            throw new RuntimeException("Invalid token");
        }
    }
}

//package com.ssafy.api.service;
//
//import com.ssafy.api.request.MemberLoginPostReq;
//import com.ssafy.api.response.MemberLoginPostRes;
//import com.ssafy.common.util.JwtUtil;
//import com.ssafy.db.entity.Member;
//import com.ssafy.db.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthServiceImpl implements AuthService {
//
//    private final MemberRepository memberRepository;
//    private final JwtUtil jwtUtil;
//
//    @Override
//    public MemberLoginPostRes login(MemberLoginPostReq loginReq) {
//        Member member = memberRepository.findByEmail(loginReq.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!member.getPw().equals(loginReq.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        String token = jwtUtil.generateToken(member.getEmail(), member.getRole());
//        return MemberLoginPostRes.of(200, "로그인에 성공하였습니다.", token, member.getEmail(), member.getRole());
//    }
//
//    @Override
//    public Member validateMember(String token) {
//        String email = jwtUtil.extractEmail(token);
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (jwtUtil.validateToken(token, email)) {
//            return member;
//        } else {
//            throw new RuntimeException("Invalid token");
//        }
//    }
//}