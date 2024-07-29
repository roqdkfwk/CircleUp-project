package com.ssafy.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private static final Logger logger = LoggerFactory.getLogger(TokenBlacklistServiceImpl.class);

    // tokenBlacklist에 token : expiration 쌍으로 데이터를 저장
    private final Map<String, Date> tokenBlacklist = new ConcurrentHashMap<>();

    // 토큰을 블랙리스트에 추가
    @Override
    public void addToBlacklist(String token, Date expirationDate) {
        tokenBlacklist.put(token, expirationDate);
    }

    // 토큰이 블랙리스트에 포함되어 있는지 확인
    @Override
    public boolean isBlacklisted(String token) {

        // 전달 받은 token의 만료 시각을 가져옴
        Date expirationDate = tokenBlacklist.get(token);

        if (expirationDate != null) {
            if (expirationDate.before(new Date())) {    // 토큰이 만료된 경우
                tokenBlacklist.remove(token);   // 블랙리스트에서 제거
            }

            // 유효하지 않은 토큰이므로 true를 반환
            return true;
        }

        // 유효한 토큰이므로 false를 반환
        return false;
    }

    // 만료된 토큰을 제거
    @Scheduled(fixedRate = 3600000) // 해당 메소드가 1시간마다 실행
    public void removeExpiredTokens() {

        Date now = new Date();

        // tokenBlacklist에서 token : expiration 형태로 가져옴
        tokenBlacklist.entrySet().removeIf(entry -> {
            // 가져온 entry의 expiration이 now 이전이라면 true를 반환하고 removeIf는 해당 토큰들을 제거
            if (entry.getValue().before(now)) {
                logger.info("Removing expired token from blacklist");
                return true;
            }

            return false;
        });
    }
}


//package com.ssafy.api.service;
//
//import com.ssafy.common.util.JwtUtil;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//public class TokenBlacklistServiceImpl implements TokenBlacklistService {
//
//    private final Set<String> tokenBlacklist = ConcurrentHashMap.newKeySet();
//    private final JwtUtil jwtUtil;
//
//    public TokenBlacklistServiceImpl(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    // 토큰을 블랙리스트에 추가
//    @Override
//    public void addToBlacklist(String token, Date expirationDate) {
//        tokenBlacklist.add(token);
//        // 여기에 만료된 토큰을 주기적으로 제거하는 로직을 추가할 수 있습니다.
//        // 예를 들어, 스케줄러를 사용하여 주기적으로 만료된 토큰을 제거할 수 있습니다.
//    }
//
//    // 토큰이 블랙리스트에 포함되어 있는지 확인
//    @Override
//    public boolean isBlacklisted(String token) {
//        return tokenBlacklist.contains(token);
//    }
//
//    // 만료된 토큰을 제거
//    public void removeExpiredTokens() {
//        Date now = new Date();
//        tokenBlacklist.removeIf(token -> {
//            try {
//                Date expiration = jwtUtil.extractExpiration(token); // JwtUtil을 이용해 토큰 만료 시간 추출
//                return expiration.before(now);
//            } catch (Exception e) {
//                // 토큰 파싱 실패 시 해당 토큰 제거
//                return true;
//            }
//        });
//    }
//}