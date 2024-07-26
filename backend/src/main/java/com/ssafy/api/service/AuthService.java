package com.ssafy.api.service;

import com.ssafy.api.request.MemberLoginPostReq;
import com.ssafy.api.response.MemberLoginPostRes;

public interface AuthService {
    // 로그인
    MemberLoginPostRes login(MemberLoginPostReq loginReq);

    // 로그아웃
//    void logout(Long memberId);
    void logout(String token);

    String createNewAccessToken(String refreshToken);
}