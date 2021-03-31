package org.learn.curd.service;

import org.learn.curd.dto.JwtTokenResponse;

public interface AuthenticationService {

    JwtTokenResponse createAuthenticationToken(String userName, String password);
    JwtTokenResponse refreshAndGetAuthenticationToken(String token);

}
