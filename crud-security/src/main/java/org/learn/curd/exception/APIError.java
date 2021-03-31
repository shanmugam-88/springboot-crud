package org.learn.curd.exception;

import org.learn.curd.logging.filter.MessageHeaderConstants;
import org.slf4j.MDC;

public class APIError {

    private String errorMessage;
    private String errorCode;
    private String correlationId;

    public APIError(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.correlationId = MDC.get(MessageHeaderConstants.CORRELATION_ID_LOG);
    }

    public APIError(String errorMessage, String errorCode, String correlationId) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.correlationId = correlationId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
