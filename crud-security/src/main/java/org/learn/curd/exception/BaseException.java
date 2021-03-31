package org.learn.curd.exception;

public abstract class BaseException extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    BaseException(StatusCode statusCode) {
        this.errorCode = statusCode.errorCode();
        this.errorMessage = ExceptionMessage.errorMessage.get(statusCode.errorCode());
    }

    BaseException(StatusCode statusCode, String errorMessage) {
        this.errorCode = statusCode.errorCode();
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
