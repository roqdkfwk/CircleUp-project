package com.ssafy.api.response;

import com.ssafy.api.request.MemberModifyUpdateReq;
import com.ssafy.db.entity.Member;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ApiModel("MemberModifyUpdateRes")
public class MemberModifyUpdateRes {
    Member member;
    String accessToken;
    List<String> tags;

    public static MemberModifyUpdateRes toEntity(
            Member member,
            String accessToken,
            List<String> tags
    ) {
        return MemberModifyUpdateRes.builder()
                .member(member)
                .accessToken(accessToken)
                .tags(tags)
                .build();
    }
}