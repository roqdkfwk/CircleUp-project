package com.ssafy.api.controller;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.api.response.MemberReadGetRes;
import com.ssafy.api.service.MemberService;
import com.ssafy.db.entity.Member;
import com.ssafy.dto.MemberDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Api(tags = {"멤버"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이메일과 비밀번호를 통해 회원가입을 진행합니다<br/>별도 인증과정은 필요없습니다")
    public ResponseEntity<?> signup(@RequestBody MemberSignupPostReq memberSignupPostReq) {
        // TODO 서비스 코드
        memberService.signup(memberSignupPostReq);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/checkEmail")
    @Operation(summary = "이메일 중복체크", description = "사용자가 로그인 시 사용할 Email과<br/>중복되는 Email이 존재하는지 DB에서 확인합니다")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {

        if (memberService.checkEmail(email))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        else
            return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{member_id}")
    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 진행합니다<br/>탈퇴시 관련된 정보는 모두 즉시 삭제됩니다")
    public ResponseEntity<?> withdraw(@PathVariable("member_id") Long memberId) {
        // TODO 서비스 코드
        memberService.withdraw(memberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{member_id}")
    @Operation(summary = "회원정보수정", description = "회원의 정보를 수정합니다<br/>로그인 시 아이디로 사용하는 이메일과 토큰 외의 정보를 수정할 수 있습니다")
    public ResponseEntity<?> modifyMember(@RequestBody MemberModifyUpdateReq memberModifyUpdateReq, @PathVariable("member_id") Long memberId) {
        // TODO 서비스 코드
        Member updatedMember = memberService.modifyMember(memberId, memberModifyUpdateReq);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMember);
    }

    @GetMapping("/{member_id}")
    @Operation(summary = "회원정보조회", description = "회원의 정보를 조회합니다")
    public ResponseEntity<MemberReadGetRes> readMember(@PathVariable("member_id") Long memberId) {
        // TODO 서비스 코드
        try {
            MemberReadGetRes memberDto = memberService.readMember(memberId);
            return ResponseEntity.ok(memberDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
