package com.ssafy.api.response;

import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.enums.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel("MemberReadGetResponse")
public class MemberReadGetRes {

    @ApiModelProperty(name = "회원 ID")
    private Long id;

    @ApiModelProperty(name = "로그인용 이메일")
    private String email;

    @ApiModelProperty(name = "이름")
    private String name;

    @ApiModelProperty(name = "역할")
    private Role role;

    @ApiModelProperty(name = "연락용 이메일")
    private String contactEmail;

    @ApiModelProperty(name = "연락용 전화번호")
    private String contactTel;

    public static MemberReadGetRes of(Member member) {
        return MemberReadGetRes.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .contactEmail(member.getContactEmail())
                .contactTel(member.getContactTel())
                .build();
    }
}