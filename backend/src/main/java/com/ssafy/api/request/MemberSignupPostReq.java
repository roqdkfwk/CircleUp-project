package com.ssafy.api.request;

import com.ssafy.db.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSignupPostReq {

    private String email;
    private String pw;
    private String name;
    private String token;
    private Role role;
    private String contact;
    private String tel;
}
