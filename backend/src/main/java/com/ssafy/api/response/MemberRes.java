package com.ssafy.api.response;

import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.enums.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@ApiModel("MemberResponse")
public class MemberRes {
    String email;
    String name;
    Role role;
    String contactEmail;
    String contactTel;
    List<String> tags;

    public static MemberRes fromEntity(Member member, List<String> tagNameList) {
        return MemberRes.builder()
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .contactEmail(member.getContactEmail())
                .contactTel(member.getContactTel())
                .tags(tagNameList)
                .build();
    }
}
