package com.ssafy.api.controller;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.api.response.MemberReadGetRes;
import com.ssafy.api.service.MemberService;
import com.ssafy.common.custom.RequiredAuth;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"멤버"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이메일과 비밀번호를 통해 회원가입을 진행합니다<br/>별도 인증과정은 필요없습니다")
    public ResponseEntity<?> signup(
            @RequestBody MemberSignupPostReq memberSignupPostReq
    ) {
        memberService.signup(memberSignupPostReq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @PostMapping("/checkEmail")
//    @Operation(summary = "이메일 중복체크", description = "사용자가 로그인 시 사용할 Email과<br/>중복되는 Email이 존재하는지 DB에서 확인합니다")
//    public ResponseEntity<?> checkEmail(
//            @RequestParam String email
//    ) {
//        if (memberService.checkEmail(email))
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        else
//            return ResponseEntity.status(HttpStatus.OK).build();
//    }

    @DeleteMapping("/withdraw")
    @ApiOperation(value = "회원탈퇴")
    @RequiredAuth
    public ResponseEntity<?> withdrawMember(
            @RequestHeader("Authorization") String token
    ) {
        try {
            memberService.withdrawMemberByToken(token);
            return ResponseEntity.ok(BaseResponseBody.of(200, "회원 탈퇴가 완료되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponseBody.of(400, e.getMessage()));
        }
    }

    @PatchMapping("/modify")
    @ApiOperation(
            value = "회원정보수정",
            notes = "회원의 정보를 수정합니다<br/>로그인 시 아이디로 사용하는 이메일과 토큰 외의 정보를 수정할 수 있습니다"
    )
    @RequiredAuth
    public ResponseEntity<?> modifyMember(
            Authentication authentication,
            @RequestBody MemberModifyUpdateReq memberModifyUpdateReq
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(memberService.modifyMember(memberId, memberModifyUpdateReq));
    }

    @GetMapping
    @ApiOperation(
            value = "마이페이지",
            notes = "마이페이지입니다<br/>이메일, 이름 등의 정보를 조회합니다"
    )
    @RequiredAuth
    public ResponseEntity<MemberReadGetRes> readMember(
            Authentication authentication,
            @RequestHeader("Authorization") String accessToken
    ) {
        Long memberId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(memberService.getMyInfo(memberId, accessToken));
    }
}
