package com.ssafy.common.auth;

import com.ssafy.api.service.MemberService;
import com.ssafy.common.util.JwtUtil;
import com.ssafy.common.util.ResponseBodyWriteUtil;
import com.ssafy.db.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 요청 헤더에 jwt 토큰이 있는 경우, 토큰 검증 및 인증 처리 로직 정의.
 */
public class JwtAuthenticationFilterM extends BasicAuthenticationFilter {
    private MemberService memberService;
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilterM(AuthenticationManager authenticationManager, MemberService memberService, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // HTTP 요청의 헤더들 중에서 "Authorization"이라는 이름을 가진 헤더를 찾음
        String header = request.getHeader(JwtUtil.HEADER_STRING);

        // 헤더가 없거나 "Bearer "로 시작하지 않으면 다음 필터로 넘깁니다.
        if (header == null || !header.startsWith(JwtUtil.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // If header is present, try grab user principal from database and perform authorization
            Authentication authentication = getAuthentication(request);
            // jwt 토큰으로 부터 획득한 인증 정보(authentication) 설정.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            ResponseBodyWriteUtil.sendError(request, response, ex);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Transactional(readOnly = true)
    public Authentication getAuthentication(HttpServletRequest request) throws Exception {

        // HTTP 요청의 헤더들 중에서 "Authorization"이라는 이름을 가진 헤더를 찾음
        String token = request.getHeader(JwtUtil.HEADER_STRING);

        // 요청 헤더에 Authorization 키값에 jwt 토큰이 포함된 경우에만, 토큰 검증 및 인증 처리 로직 실행.
        if (token != null) {

            // parse the token and validate it (decode)
            JwtParser verifier = jwtUtil.getVerifier();
            jwtUtil.handleError(token);
            Claims claims = verifier.parseClaimsJws(token.replace(JwtUtil.TOKEN_PREFIX, "")).getBody();
            String memberId = claims.getSubject();

            if (memberId != null) {
                // jwt 토큰에 포함된 계정 정보(userId) 통해 실제 디비에 해당 정보의 계정이 있는지 조회.
                Member member = memberService.getMemberById(Long.parseLong(memberId));
                if (member != null) {
                    // 식별된 정상 유저인 경우, 요청 context 내에서 참조 가능한 인증 정보(jwtAuthentication) 생성.
                    SsafyMemberDetails memberDetails = new SsafyMemberDetails(member);
                    UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(memberId,
                            null, memberDetails.getAuthorities());
                    jwtAuthentication.setDetails(memberDetails);

                    // Spring Security에서 사용할 수 있는 Authentication 객체를 생성
                    return jwtAuthentication;
                }
            }
            return null;
        }
        return null;
    }
}
