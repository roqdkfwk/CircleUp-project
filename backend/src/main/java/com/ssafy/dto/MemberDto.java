package com.ssafy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {

    private String email;
    private String pw;
    private String name;
    private String token;
    private Long type;
    private String tel;
}
