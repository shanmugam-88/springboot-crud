package org.learn.curd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionResponseEntityHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<APIError> handleDefaultException(Exception exception) {
        APIError apiError = new APIError(exception.getMessage(), StatusCode.ERR_0001.errorCode());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<APIError> handleBusinessException(BusinessException exception) {
        APIError apiError = new APIError(exception.getErrorMessage(), exception.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<APIError> handleAccessDeniedException(AccessDeniedException exception) {
        StatusCode statusCode = StatusCode.valueOf(exception.getMessage());
        if(statusCode.errorCode().equals(exception.getMessage())) {
            APIError apiError = new APIError(exception.getMessage(),
                    ExceptionMessage.errorMessage.get(exception.getMessage()));
            return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
        }
        APIError apiError = new APIError(exception.getMessage(), exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}
