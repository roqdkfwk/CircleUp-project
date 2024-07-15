package com.ssafy.api.controller;

import com.ssafy.api.service.MemberService;
import com.ssafy.db.entity.Member;
import com.ssafy.dto.MemberDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = {"멤버"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이메일과 비밀번호를 통해 회원가입을 진행합니다<br/>별도 인증과정은 필요없습니다")
    public ResponseEntity<?> signup(@RequestBody MemberDto memberDto) {
        // TODO 서비스 코드
        memberService.signup(memberDto);
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
    @Operation(summary = "회원정보수정", description = "회원의 정보를 수정합니다<br/>로그인 시 아이디로 사용하는 이메일 외의 정보를 수정할 수 있습니다")
    public ResponseEntity<?> updateMember(@RequestBody MemberDto memberDto, @PathVariable("member_id") Long memberId) {
        // TODO 서비스 코드
        Member updatedMember = memberService.updateMember(memberDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMember);
    }

    @GetMapping("/{member_id}")
    @Operation(summary = "회원정보조회", description = "회원의 정보를 조회합니다")
    public ResponseEntity<?> readMember(@PathVariable("member_id") Long memberId) {
        // TODO 서비스 코드
        Optional<Member> member = memberService.readMember(memberId);
        if (member.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(member.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인합니다<br/>이메일과 비밀번호를 입력해 DB에 저장된 데이터와 비교합니다")
    public ResponseEntity<?> login(@RequestBody MemberDto memberDto) {
        // TODO 서비스 코드
        Optional<Member> member = memberService.login(memberDto.getEmail(), memberDto.getPw());
        if (member.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(member.get());
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
