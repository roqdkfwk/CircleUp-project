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

    // 로그인
    @Override
    public MemberLoginPostRes login(MemberLoginPostReq loginReq) {
        Member member = memberRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!member.getPw().equals(loginReq.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtUtil.generateAccessToken(member.getId(), member.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(member.getId());

        return MemberLoginPostRes.of(200, "로그인에 성공하였습니다.", accessToken, refreshToken, member.getEmail(), member.getRole());
    }

    // 토큰의 유효성 검사
    @Override
    public Member validateMember(String token) {
        Long id = jwtUtil.extractId(token);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (jwtUtil.validateToken(token, id)) {
            return member;
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