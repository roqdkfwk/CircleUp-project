package com.ssafy.api.controller;

import com.ssafy.api.request.MemberLoginPostReq;
import com.ssafy.api.response.MemberLoginPostRes;
import com.ssafy.api.service.AuthService;
import com.ssafy.db.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"인증"})
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<MemberLoginPostRes> login(@RequestBody MemberLoginPostReq loginReq) {
        MemberLoginPostRes loginRes = authService.login(loginReq);
        return ResponseEntity.ok(loginRes);
    }

    @GetMapping("/validate")
    @Operation(summary = "토큰 유효성 검사")
    public ResponseEntity<Member> validateToken(@RequestHeader("Authorization") String token) {
        Member member = authService.validateMember(token.replace("Bearer ", ""));
        return ResponseEntity.ok(member);
    }
}