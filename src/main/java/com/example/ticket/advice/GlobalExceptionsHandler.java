package com.example.ticket.advice;

import com.example.ticket.dto.rep.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;


import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.example.ticket.controller")
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .success(false)
                .message("Validation failed")
                .error((List<String>) errors)
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("Constraint violation: {}", ex.getMessage());

        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage(),
                        (error1, error2) -> error1
                ));

        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .success(false)
                .message("Validation constraints violated")
                .error((List<String>) errors)
                .build());
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse<Object>> handleInvalidArguments(Exception ex) {
        log.error("Invalid argument: {}", ex.getMessage());

        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .success(false)
                .message("Invalid parameter provided")
                .error(Collections.singletonList(ex.getMessage()))
                .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("Message not readable: {}", ex.getMessage());

        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .success(false)
                .message("Malformed request body")
                .error(Collections.singletonList("Request body is invalid or malformed"))
                .build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingParams(MissingServletRequestParameterException ex) {
        log.error("Missing parameter: {}", ex.getMessage());

        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .success(false)
                .message("Missing required parameter")
                .error(Collections.singletonList(ex.getParameterName() + " is required"))
                .build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
        log.error("No handler found: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .success(false)
                .message("Resource not found")
                .error(Collections.singletonList("The requested resource doesn't exist"))
                .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access denied: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.builder()
                .success(false)
                .message("Access Denied")
                .error(Collections.singletonList("You do not have permission to access this resource"))
                .build());
    }

    // Uncomment and implement these business exceptions
    /*
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataNotFound(DataNotFoundException ex) {
        log.error("Data not found: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .success(false)
                .message("Resource not found")
                .error(Collections.singletonList(ex.getMessage()))
                .build());
    }
    */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleUncaughtException(Exception ex) {
        log.error("Unexpected error occurred: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                .success(false)
                .message("An internal server error occurred")
                .error(Collections.singletonList("Please contact support with request timestamp"))
                .build());
    }
}