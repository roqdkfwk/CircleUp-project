package com.ssafy.api.service;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.api.response.MemberModifyUpdateRes;
import com.ssafy.api.response.MemberReadGetRes;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Tag;

public interface MemberService {
    // 회원가입
    void signup(MemberSignupPostReq memberSignupPostReq);

    // 회원탈퇴
    void withdrawMemberByToken(String token);

    // 회원정보수정
    MemberModifyUpdateRes modifyMember(Long memberId, MemberModifyUpdateReq memberModifyUpdateReq);

    // 회원정보조회
    MemberReadGetRes getMyInfo(Long memberId, String accessToken);

    // email로 회원찾기
    Member getMemberByEmail(String email);

    Member getMemberById(Long memberId);

    void saveFavor(Member member, Tag tag);
}
