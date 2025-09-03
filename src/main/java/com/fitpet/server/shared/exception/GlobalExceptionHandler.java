package com.fitpet.server.shared.exception;

import java.util.LinkedHashMap;          
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(new ErrorResponse(errorCode));
    }

    /** @Valid @RequestBody 실패 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidBody(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity.status(ErrorCode.INVALID_REQUEST.getStatus())
                .body(Map.of(
                        "code", ErrorCode.INVALID_REQUEST.getCode(),
                        "message", ErrorCode.INVALID_REQUEST.getMessage(),
                        "errors", errors
                ));
    }

    /** @Validated 파라미터/패스변수 실패 */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getConstraintViolations().forEach(v ->
                errors.put(v.getPropertyPath().toString(), v.getMessage())
        );
        return ResponseEntity.status(ErrorCode.INVALID_REQUEST.getStatus())
                .body(Map.of(
                        "code", ErrorCode.INVALID_REQUEST.getCode(),
                        "message", ErrorCode.INVALID_REQUEST.getMessage(),
                        "errors", errors
                ));
    }
}
