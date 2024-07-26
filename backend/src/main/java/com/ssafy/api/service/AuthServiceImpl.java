package com.ssafy.api.service;

import com.ssafy.api.request.MemberLoginPostReq;
import com.ssafy.api.response.MemberLoginPostRes;
import com.ssafy.common.custom.BadRequestException;
import com.ssafy.common.custom.NotFoundException;
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
        Member member = memberRepository.findByEmail(loginReq.getEmail()).orElseThrow(
                () -> new NotFoundException("Not Found Member")
        );
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

//    // 로그아웃
//    @Override
//    public void logout(Long memberId) {
//        Member member = memberRepository.findById(memberId).orElseThrow(
//                () -> new NotFoundException("Not Found Member : Member_id is " + memberId)
//        );
//
//        String refreshToken = member.getRefreshToken();
//
//        // refresh 토큰이 유효하고 && 블랙리스트에 등록되지 않    은 토큰이라면
//        if (jwtUtil.validateToken(refreshToken) && !tokenBlacklistService.isBlacklisted(refreshToken)) {
//            tokenBlacklistService.addToBlacklist(refreshToken, jwtUtil.extractExpiration(refreshToken));
//            member.setRefreshToken(null);
//            memberRepository.save(member);
//
//            // refresh 토큰 Cookie 값 0
//            Cookie cookie = new Cookie("refresh", null);
//            cookie.setMaxAge(0);
//            cookie.setPath("/");
//
//            response.addCookie(cookie);
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            throw new RuntimeException("Invalid token");
//        }
//    }

    // 로그아웃
    @Override
    public void logout(String token) {
        String accessToken = token.replace("Bearer ", "");
        Long id = jwtUtil.extractId(accessToken);

        Member member = memberRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Not Found Member : Member_id is " + id)
        );

        member.setRefreshToken(null);
        memberRepository.save(member);
        // 해당 토큰이 유효하고 && 블랙리트스트에 등록되지 않은 토큰이라면
        // 블랙리스트에 등록
        if (jwtUtil.validateToken(accessToken) && !tokenBlacklistService.isBlacklisted(accessToken)) {
            tokenBlacklistService.addToBlacklist(accessToken, jwtUtil.extractExpiration(accessToken));
        } else {
            throw new RuntimeException("Invalid token");
        }
    }

    @Override
    public String createNewAccessToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new BadRequestException("유효하지 않은 토큰이거나 탈퇴한 회원입니다.")
        );

        if (!jwtUtil.isTokenExpired(refreshToken)) {    // 리프레쉬 토큰이 유효하다면
            return jwtUtil.generateAccessToken(member);
        } else {    // 리프레쉬 토큰이 유효하지 않은 경우
            return null;
        }
    }
}