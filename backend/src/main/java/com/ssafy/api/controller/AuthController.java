package com.ssafy.api.controller;

import com.ssafy.api.request.MemberLoginPostReq;
import com.ssafy.api.response.MemberLoginPostRes;
import com.ssafy.api.service.AuthService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation( value="로그아웃" )
    @ApiImplicitParam(name = "Authorization", value = "Bearer 로 시작하는 JWT 토큰 필요", required = false, dataType = "string", paramType = "header")
    public ResponseEntity<BaseResponseBody> logout(@RequestHeader("Authorization") String token) {

        authService.logout(token.replace("Bearer ", ""));
        return ResponseEntity.ok(BaseResponseBody.of(200, "로그아웃 되었습니다."));
    }
}