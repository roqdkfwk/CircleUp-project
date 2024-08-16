package com.ssafy.api.response;

import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.enums.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ApiModel("MemberReadGetResponse")
public class MemberReadGetRes {
    Long id;
    String email;
    String name;
    Role role;
    String contactEmail;
    String contactTel;
    List<String> tags;

    public static MemberReadGetRes of(Member member, List<String> tagNameList) {
        return MemberReadGetRes.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .contactEmail(member.getContactEmail())
                .contactTel(member.getContactTel())
                .tags(tagNameList)
                .build();
    }
}