package com.ssafy.api.request;

import com.ssafy.db.entity.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberModifyUpdateReq {
    String pw;
    String name;
    Role role;
    String contactEmail;
    String contactTel;
    List<Long> tags = new ArrayList<>();
}