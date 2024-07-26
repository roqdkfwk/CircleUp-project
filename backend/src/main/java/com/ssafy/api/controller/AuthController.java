package com.ssafy.api.controller;

import com.ssafy.api.request.MemberLoginPostReq;
import com.ssafy.api.response.MemberLoginPostRes;
import com.ssafy.api.service.AuthService;
import com.ssafy.common.custom.RequiredAuth;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(tags = {"인증"})
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /////////////////////////////////////////////////////
    private final JwtUtil jwtUtil;

    @GetMapping("/jwt")
    @ApiOperation(value = "(개발용) Access Token 생성",
            notes = "<strong>유효기간 1일</strong>의 임시 토큰을 발급합니다<br/>" +
                    "이 과정에서 해당 memberId가 실제로 존재하는지는 확인하지 않습니다<br/>" +
                    "반환값은 <strong>'접두사(Bearer ) + JWT토큰'</strong>입니다")
    public String test(@RequestParam(name = "member_id") Long memberId) {
        return "Bearer "
                + jwtUtil.createToken(new HashMap<>(), memberId.toString(), 86400000L, true);
    }
    /////////////////////////////////////////////////////

    // 로그인
    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<?> login(@RequestBody MemberLoginPostReq loginReq) {
        try {
            MemberLoginPostRes loginRes = authService.login(loginReq);
            return ResponseEntity.ok(loginRes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponseBody.of(401, "로그인에 실패하였습니다."));
        }
    }

//    // 로그아웃
//    @PostMapping("/logout")
//    @ApiOperation(value = "로그아웃")
//    @RequiredAuth
//    public ResponseEntity<BaseResponseBody> logout(
//            Authentication authentication
//    ) {
//        Long memberId = Long.valueOf(authentication.getName());
//        authService.logout(memberId);
//        return ResponseEntity.ok(BaseResponseBody.of(200, "로그아웃 되었습니다."));
//    }

    // 로그아웃
    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃")
    @RequiredAuth
    public ResponseEntity<BaseResponseBody> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.replace("Bearer ", ""));
        return ResponseEntity.ok(BaseResponseBody.of(200, "로그아웃 되었습니다."));
    }

    // AccessToken 재발급
    @PostMapping("/reissue")
    @ApiOperation(value = "Access Token 재발급", notes = "Refresh Token 을 통해 새로운 Access Token을 발급합니다<br/>DB에 저장된 Refresh Token과 일치해야합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "탈퇴한 회원이거나 유효하지 않는 토큰입니다"),
            @ApiResponse(code = 403, message = "유효시간이 만료된 리프레쉬 토큰입니다. 다시 로그인해주세요")
    })
    public ResponseEntity<String> reissue(
            @RequestHeader String refresh
    ) {
        String newAccessToken = authService.createNewAccessToken(refresh);
        if (newAccessToken == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, newAccessToken)
                .body("New access token generated successfully.");
    }
}