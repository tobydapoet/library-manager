package com.example.LibraryManager.exception;

import com.example.LibraryManager.dtos.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        return response(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", ex.getMessage(), null);
    }

    @ExceptionHandler({AuthorizationDeniedException.class, ForbiddenException.class})
    public ResponseEntity<ErrorResponse> handleForbidden(Exception ex) {
        return response(HttpStatus.FORBIDDEN, "FORBIDDEN", ex.getMessage(), null);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        return response(HttpStatus.BAD_REQUEST, "BAD_REQUEST", ex.getMessage(), null);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return response(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", ex.getMessage(), null);
    }

    @ExceptionHandler({DuplicateResourceException.class, ResourceInUseException.class})
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex) {
        String code = ex instanceof DuplicateResourceException ? "DUPLICATE_RESOURCE" : "RESOURCE_IN_USE";
        return response(HttpStatus.CONFLICT, code, ex.getMessage(), null);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileStorage(FileStorageException ex) {
        return response(HttpStatus.BAD_GATEWAY, "FILE_STORAGE_ERROR", ex.getMessage(), null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return response(HttpStatus.BAD_REQUEST, "INVALID_REQUEST",
                "Request body is required and must be valid JSON", null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> details = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> details.putIfAbsent(error.getField(), error.getDefaultMessage()));
        return response(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Invalid input", details);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
        return response(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND",
                "The requested resource does not exist", null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred", null);
    }

    private ResponseEntity<ErrorResponse> response(
            HttpStatus status, String code, String message, Object details) {
        return ResponseEntity.status(status).body(ErrorResponse.builder()
                .code(code)
                .message(message)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build());
    }
}
