package com.ssafy.api.response;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberModifyUpdateRes {

    private String pw;
    private String name;
    private Role role;
    private String contact;
    private String tel;

    public static void of(
            MemberModifyUpdateReq memberModifyUpdateReq,
            Member member
    ) {
        // member의 정보를 수정
        member.setPw(memberModifyUpdateReq.getPw());
        member.setName(memberModifyUpdateReq.getName());
        member.setRole(memberModifyUpdateReq.getRole());
        member.setContactEmail(memberModifyUpdateReq.getContactEmail());
        member.setContactTel(memberModifyUpdateReq.getContactTel());
    }
}