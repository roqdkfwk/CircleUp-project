package com.ssafy.api.request;

import com.ssafy.db.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberSignupPostReq {
    private String email;
    private String pw;
    private String name;
    private Role role;
    private String contactEmail;
    private String contactTel;
    private List<Long> tags = new ArrayList<>();
}
