package com.ssafy.api.service;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.db.entity.Member;
import com.ssafy.dto.MemberDto;

import java.util.Optional;

public interface MemberService {

    // 회원가입
    void signup(MemberSignupPostReq memberSignupPostReq);

    // 이메일 중복체크
    boolean checkEmail(String email);

    // 회원탈퇴
    void withdraw(Long memberId);
    
    // 로그인
    Optional<Member> login(String email, String pw);

    // 회원정보수정
    Member modifyMember(Long memberId, MemberModifyUpdateReq memberModifyUpdateReq);
    
    // 회원정보조회
    Optional<Member> readMember(Long memberId);

    // email로 회원찾기
    Optional<Member> findByEmail(String email);
}
