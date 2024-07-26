package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.enums.Role;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("MemberLoginPostResponse")
public class MemberLoginPostRes extends BaseResponseBody {
    String accessToken;
    String refreshToken;
    String name;
    String email;
    Role role;

    public static MemberLoginPostRes of(
            Integer statusCode,
            String message,
            String accessToken,
            String refreshToken,
            String name,
            String email,
            Role role
    ) {
        MemberLoginPostRes res = new MemberLoginPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);
        res.setRefreshToken(refreshToken);
        res.setName(name);
        res.setEmail(email);
        res.setRole(role);

        return res;
    }
}