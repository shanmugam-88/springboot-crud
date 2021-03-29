package org.learn.curd.exception;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMessage {
    public static Map<String,String> errorMessage = new HashMap();
    static {
        errorMessage.put("ERR_USR_0001", "User not found");
        errorMessage.put("ERR_ART_0001", "Article not found");
    }
}
