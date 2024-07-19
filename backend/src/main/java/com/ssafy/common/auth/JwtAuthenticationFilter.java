//package com.ssafy.common.auth;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Base64;
//import java.util.Objects;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.ssafy.api.service.UserService;
//import com.ssafy.common.util.JwtTokenUtil;
//import com.ssafy.common.util.ResponseBodyWriteUtil;
//import com.ssafy.db.entity.User;
//
///**
// * 요청 헤더에 jwt 토큰이 있는 경우, 토큰 검증 및 인증 처리 로직 정의.
// */
//public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
//    private UserService userService;
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
//        super(authenticationManager);
//        this.userService = userService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // HTTP 요청의 헤더들 중에서 "Authorization"이라는 이름을 가진 헤더를 찾음
//        String header = request.getHeader(JwtTokenUtil.HEADER_STRING);
//
//        // 헤더가 없거나 "Bearer "로 시작하지 않으면 다음 필터로 넘깁니다.
//        if (header == null || !header.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        try {
//            // 헤더가 존재하면 DB에서 사용자 정보를 가져와 인증을 진행한다.
//            Authentication authentication = getAuthentication(request);
//            // jwt 토큰으로 부터 획득한 인증 정보(authentication) 설정.
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (Exception ex) {
//            ResponseBodyWriteUtil.sendError(request, response, ex);
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    @Transactional(readOnly = true)
//    public Authentication getAuthentication(HttpServletRequest request) throws Exception {
//
//        // HTTP 요청의 헤더들 중에서 "Authorization"이라는 이름을 가진 헤더를 찾음
//        String token = request.getHeader(JwtTokenUtil.HEADER_STRING);
//
//        // 요청 헤더에 Authorization 키값에 jwt 토큰이 포함된 경우에만, 토큰 검증 및 인증 처리 로직 실행.
//        if (token != null) {
//            // 토큰을 파싱하고 유효성을 검증한다.
//            JWTVerifier verifier = JwtTokenUtil.getVerifier();
//            JwtTokenUtil.handleError(token);
//            DecodedJWT decodedJWT = verifier.verify(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""));
//            String userId = decodedJWT.getSubject();
//
//            if (userId != null) {
//                // jwt 토큰에 포함된 계정 정보(userId) 통해 실제 디비에 해당 정보의 계정이 있는지 조회.
//                User user = userService.getUserByUserId(userId);
//                if (user != null) {
//                    // 식별된 정상 유저인 경우, 요청 context 내에서 참조 가능한 인증 정보(jwtAuthentication) 생성.
//                    SsafyUserDetails userDetails = new SsafyUserDetails(user);
//                    UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(userId,
//                            null, userDetails.getAuthorities());
//                    jwtAuthentication.setDetails(userDetails);
//
//                    // Spring Security에서 사용할 수 있는 Authentication 객체를 생성
//                    return jwtAuthentication;
//                }
//            }
//            return null;
//        }
//        return null;
//    }
//}
