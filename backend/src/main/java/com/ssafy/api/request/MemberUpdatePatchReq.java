package com.ssafy.api.request;

import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberUpdatePatchReq {
    private String pw;
    private String name;
    private Role role;
    private String contactEmail;
    private String contactTel;
    private List<Long> tags;

    public static void toEntity(
            MemberUpdatePatchReq memberUpdatePatchReq,
            Member member
    ) {
        member.setPw(memberUpdatePatchReq.getPw());
        member.setName(memberUpdatePatchReq.getName());
        member.setRole(memberUpdatePatchReq.getRole());
        member.setContactEmail(memberUpdatePatchReq.getContactEmail());
        member.setContactTel(memberUpdatePatchReq.getContactTel());
    }
}