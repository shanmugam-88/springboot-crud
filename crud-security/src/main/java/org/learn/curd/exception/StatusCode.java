package org.learn.curd.exception;

public enum StatusCode {
    ERR_USR_0001("ERR_USR_0001"),
    ERR_0001("ERR_0001"),
    ERR_ART_0001("ERR_ART_0001"),
    ERR_ROLE_0001("ERR_ROLE_0001"),
    ERR_ACCESS_0001("ERR_ACCESS_0001"),
    ERR_ACCESS_0002("ERR_ACCESS_0002"),
    ERR_ACCESS_0003("ERR_ACCESS_0003"),
    ERR_ACCESS_0004("ERR_ACCESS_0004"),
    ERR_ACCESS_0005("ERR_ACCESS_0005");

    private String errorCode;

    StatusCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String errorCode() {
        return errorCode;
    }
}
