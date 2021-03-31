package org.learn.curd.exception;

public class BusinessException extends BaseException{

    public BusinessException(StatusCode statusCode) {
        super(statusCode);
    }

    public BusinessException(StatusCode statusCode, String errorMessage) {
        super(statusCode,errorMessage);
    }
}
