package com.ssafy.api.service;

import com.ssafy.api.request.MemberLoginPostReq;
import com.ssafy.api.response.MemberLoginPostRes;
import com.ssafy.db.entity.Member;

public interface AuthService {
    
    // 로그인
    MemberLoginPostRes login(MemberLoginPostReq loginReq);
    
    // 토큰의 유효성 검사
    Member validateMember(String token);
}