package org.learn.curd.service.impl;

import org.learn.curd.dto.JwtTokenResponse;
import org.learn.curd.security.jwt.service.JwtMyUserDetailsService;
import org.learn.curd.security.jwt.util.JwtTokenUtil;
import org.learn.curd.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtMyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public JwtTokenResponse createAuthenticationToken(String userName, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtTokenResponse(token,userDetails);
    }

    @Override
    public JwtTokenResponse refreshAndGetAuthenticationToken(String authToken) {
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        final UserDetails userDetails =  userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return new JwtTokenResponse(refreshedToken,userDetails);
        }
        return null;
    }
}
