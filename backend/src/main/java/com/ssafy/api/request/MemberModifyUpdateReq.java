package com.ssafy.api.request;

import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.enums.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberModifyUpdateReq {
    String pw;
    String name;
    Role role;
    String contactEmail;
    String contactTel;
    List<Long> tags;

    public static void toEntity(
            MemberModifyUpdateReq memberModifyUpdateReq,
            Member member
    ) {
        member.setPw(memberModifyUpdateReq.getPw());
        member.setRole(memberModifyUpdateReq.getRole());
        member.setContactEmail(memberModifyUpdateReq.getContactEmail());
        member.setContactTel(memberModifyUpdateReq.getContactTel());
    }
}