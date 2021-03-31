package org.learn.curd.exception;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMessage {
    public static Map<String,String> errorMessage = new HashMap();
    static {
        errorMessage.put("ERR_USR_0001", "User not found");
        errorMessage.put("ERR_ART_0001", "Article not found");
        errorMessage.put("ERR_ROLE_0001", "Role not found");
        errorMessage.put("ERR_ACCESS_0001", "JWT Token not passed in authentication header");
        errorMessage.put("ERR_ACCESS_0002", "Not a bearer token");
        errorMessage.put("ERR_ACCESS_0003", "Unable to load user for given token");
        errorMessage.put("ERR_ACCESS_0004", "Unable to load user details for given token");
        errorMessage.put("ERR_ACCESS_0005", "Invalid token");
    }
}
