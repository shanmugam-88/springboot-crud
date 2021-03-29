package org.learn.curd.exception;

public enum StatusCode {
    ERR_USR_0001("ERR_USR_0001"),
    ERR_0001("ERR_0001"),
    ERR_ART_0001("ERR_ART_0001");
    private String errorCode;

    StatusCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String errorCode() {
        return errorCode;
    }
}
