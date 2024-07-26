package com.ssafy.api.response;

import com.ssafy.api.request.MemberSignupPostReq;
import com.ssafy.db.entity.Member;

public class MemberSignupPostRes {

    public static Member of(MemberSignupPostReq memberSignupPostReq) {
        return Member.builder()
                .email(memberSignupPostReq.getEmail())
                .pw(memberSignupPostReq.getPw())
                .name(memberSignupPostReq.getName())
                .role(memberSignupPostReq.getRole())
                .contactTel(memberSignupPostReq.getContactTel())
                .contactEmail(memberSignupPostReq.getContactEmail())
                .build();
    }
}
