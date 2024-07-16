package com.ssafy.api.service;

import java.util.Date;

public interface TokenBlacklistService {

    void addToBlacklist(String token, Date expirationDate);
    boolean isBlacklisted(String token);
}