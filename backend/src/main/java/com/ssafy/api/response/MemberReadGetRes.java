package com.ssafy.api.response;

import com.ssafy.db.entity.enums.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("MemberReadGetResponse")
public class MemberReadGetRes {

    @ApiModelProperty(name="회원 ID")
    private Long id;

    @ApiModelProperty(name="이메일")
    private String email;

    @ApiModelProperty(name="이름")
    private String name;

    @ApiModelProperty(name="역할")
    private Role role;

    @ApiModelProperty(name="연락처")
    private String contact;

    @ApiModelProperty(name="전화번호")
    private String tel;
}