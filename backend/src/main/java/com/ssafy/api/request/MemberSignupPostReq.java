package com.ssafy.api.request;

import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.enums.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberSignupPostReq {

    @NotEmpty
    @ApiModelProperty(name = "아이디", example = "circle@circle.com")
    private String email;

    @NotEmpty
    @ApiModelProperty(name = "비밀번호", example = "circle123")
    private String pw;

    @ApiModelProperty(name = "이름", example = "이름")
    private String name;

    @ApiModelProperty(name = "역할", example = "사용자, 강사, 관리자")
    private Role role;

    @ApiModelProperty(name = "이메일", example = "연락용 이메일")
    private String contactEmail;

    @ApiModelProperty(name = "핸드폰", example = "연락처")
    private String contactTel;

    @ApiModelProperty(name = "태그", example = "선호 카테고리")
    private List<Long> tags;

    public static Member toEntity(MemberSignupPostReq memberSignupPostReq) {
        return Member.builder()
                .email(memberSignupPostReq.getEmail())
                .pw(memberSignupPostReq.getPw())
                .name(memberSignupPostReq.getName())
                .role(memberSignupPostReq.getRole())
                .contactTel(memberSignupPostReq.getContactTel())
                .contactEmail(memberSignupPostReq.getContactEmail())
                .build();
    }
}
