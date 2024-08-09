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
@ApiModel("MemberModifyUpdateRes")
public class MemberModifyUpdateRes {
    String email;
    String name;
    Role role;
    String contactEmail;
    String contactTel;
    String accessToken;
    List<String> tags;

    public static MemberModifyUpdateRes fromEntity(
            Member member,
            String accessToken,
            List<String> tags
    ) {
        return MemberModifyUpdateRes.builder()
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .contactEmail(member.getContactEmail())
                .contactTel(member.getContactTel())
                .accessToken(accessToken)
                .tags(tags)
                .build();
    }
}