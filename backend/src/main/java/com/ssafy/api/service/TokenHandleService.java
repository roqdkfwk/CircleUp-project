package com.ssafy.api.service;

import java.util.Optional;

public interface TokenHandleService {

    Optional<Long> getMemberId(String token);


}
