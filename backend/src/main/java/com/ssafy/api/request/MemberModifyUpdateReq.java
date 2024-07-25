package com.ssafy.api.request;

import com.ssafy.db.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberModifyUpdateReq {

    private String pw;
    private String name;
    private Role role;
    private String contactEmail;
    private String contactTel;
}