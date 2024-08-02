package com.ssafy.api.request;

import com.ssafy.db.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberModifyUpdateReq {
    String pw;
    String name;
    Role role;
    String contactEmail;
    String contactTel;
    String tags;
}