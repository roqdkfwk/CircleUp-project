package com.ssafy.api.service;

import com.ssafy.db.entity.Member;
import com.ssafy.dto.MemberDto;

import java.util.Optional;

public interface MemberService {

    // 회원가입
    void signup(MemberDto memberDto);
    
    // 회원탈퇴
    void withdraw(Long memberId);
    
    // 로그인
    Optional<Member> login(String email, String pw);
    
    // 회원정보수정
    Member updateMember(MemberDto memberDto);
    
    // 회원정보조회
    Optional<Member> readMember(Long memberId);
}
