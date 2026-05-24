package com.skanda.travels.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionControllerAdvice {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionControllerAdvice.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<Map<String, Object>> handleValidation (MethodArgumentNotValidException ex) {
    Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(
            FieldError::getField,
            fe -> fe.getDefaultMessage() == null ? "invalid" : fe.getDefaultMessage(),
            (a, b) -> a + "; " + b,
            LinkedHashMap::new
        ));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
        "message", "Validation failed",
        "errors", fieldErrors
    ));
  }

  @ExceptionHandler(BadRequestException.class)
  ResponseEntity<Map<String, String>> handleBadRequest (BadRequestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
        "message", ex.getMessage()
    ));
  }

  @ExceptionHandler(NotFoundException.class)
  ResponseEntity<Map<String, String>> handleNotFound (NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
        "message", ex.getMessage()
    ));
  }

  @ExceptionHandler(BadCredentialsException.class)
  ResponseEntity<Map<String, String>> handleBadCredentials () {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
        "message", "Invalid username or password"
    ));
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<Map<String, String>> handleGeneric (Exception ex) {
    log.error("Unhandled exception", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
        "message", "Unexpected error"
    ));
  }
}
