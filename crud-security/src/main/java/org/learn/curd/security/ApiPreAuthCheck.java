package org.learn.curd.security;

import org.learn.curd.exception.StatusCode;
import org.learn.curd.logging.BaseLogger;
import org.learn.curd.logging.CustomLogFactory;
import org.learn.curd.logging.LogLevel;
import org.learn.curd.security.jwt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("apiPreAuthCheck")
public class ApiPreAuthCheck {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    private static final BaseLogger _logger = CustomLogFactory.getLogger(ApiPreAuthCheck.class);

    public boolean hasPermission(String...authority) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                                        .getRequest();
        String token = (String) request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)) {
            _logger.log(LogLevel.ERROR,"Token not passed in authentication header");
            throw new AccessDeniedException(StatusCode.ERR_ACCESS_0001.toString());
        }
        if(!token.startsWith("Bearer ")) {
            _logger.log(LogLevel.ERROR,"Not a bearer token");
            throw new AccessDeniedException(StatusCode.ERR_ACCESS_0002.toString());
        }
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if(StringUtils.isEmpty(username)) {
            _logger.log(LogLevel.ERROR,"Unable to load user for given token");
            throw new AccessDeniedException(StatusCode.ERR_ACCESS_0003.toString());
        }
        UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
        if(userDetails==null) {
            _logger.log(LogLevel.ERROR,"Unable to load user details for given token");
            throw new AccessDeniedException(StatusCode.ERR_ACCESS_0004.toString());
        }
        if (!jwtTokenUtil.validateToken(token, userDetails)) {
            _logger.log(LogLevel.ERROR,"Invalid token");
            throw new AccessDeniedException(StatusCode.ERR_ACCESS_0005.toString());
        }
        //Alternative we can use AbstractAuthenticationToken class
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        List<String> list = new ArrayList<>();
        userDetails.getAuthorities().forEach(grantedAuthority -> {
            list.add(grantedAuthority.getAuthority());
        });
        return Arrays.stream(authority).anyMatch(list::contains);
    }
}
