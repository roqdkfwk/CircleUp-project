package com.ssafy.api.service;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.api.response.MemberModifyUpdateRes;
import com.ssafy.api.response.MemberReadGetRes;
import com.ssafy.api.response.MemberRes;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Tag;

public interface MemberService {

    void signup(MemberSignupPostReq memberSignupPostReq);

    void withdrawMemberByToken(String token);

    MemberRes getMyInfo(Long memberId, String accessToken);

    MemberModifyUpdateRes modifyMember(Long memberId, MemberModifyUpdateReq memberModifyUpdateReq);

    Member getMemberByEmail(String email);

    Member getMemberById(Long memberId);

    void saveFavor(Member member, Tag tag);
}
