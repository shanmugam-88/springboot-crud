package org.learn.curd.controller;

import org.learn.curd.dto.JwtTokenRequest;
import org.learn.curd.dto.JwtTokenResponse;
import org.learn.curd.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationServiceImpl;

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<JwtTokenResponse> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
            throws AuthenticationException {
      JwtTokenResponse response =  authenticationServiceImpl.createAuthenticationToken(
                                                            authenticationRequest.getUsername(),
                                                            authenticationRequest.getPassword());
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        JwtTokenResponse response = authenticationServiceImpl.refreshAndGetAuthenticationToken(authToken);
        if(response!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.badRequest().body(null);
    }

}
