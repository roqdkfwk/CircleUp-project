package com.ssafy.api.controller;

import com.ssafy.api.request.MemberLoginPostReq;
import com.ssafy.api.response.MemberLoginPostRes;
import com.ssafy.api.service.AuthService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"인증"})
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<?> login(@RequestBody MemberLoginPostReq loginReq) {

        try {
            MemberLoginPostRes loginRes = authService.login(loginReq);
            return ResponseEntity.ok(loginRes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponseBody.of(401, "로그인에 실패하였습니다."));
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public ResponseEntity<BaseResponseBody> logout(@RequestHeader("Authorization") String token) {

        authService.logout(token.replace("Bearer ", ""));
        return ResponseEntity.ok(BaseResponseBody.of(200, "로그아웃 되었습니다."));
    }

    // 토큰 유효성 검사
    @GetMapping("/validate")
    @Operation(summary = "토큰 유효성 검사")
    public ResponseEntity<Member> validateToken(@RequestHeader("Authorization") String token) {

        Member member = authService.validateMember(token.replace("Bearer ", ""));
        return ResponseEntity.ok(member);
    }

}