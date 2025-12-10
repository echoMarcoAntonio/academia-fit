package com.academia.fit.exception;

import com.academia.fit.exception.error.ApiError;
import com.academia.fit.exception.base.BaseException;
import com.academia.fit.exception.member.DuplicateMemberException;
import com.academia.fit.exception.member.MemberNotFoundException;
import com.academia.fit.exception.workout.workoutitem.WorkoutItemNotFoundException;
import com.academia.fit.exception.resource.ResourceNotFoundException;
import com.academia.fit.service.error.ErrorMessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // ---------------------- BAD REQUEST ----------------------
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ApiError> handleBadRequest(Exception ex, HttpServletRequest request) {
        String message;

        if (ex instanceof MethodArgumentNotValidException manv) {
            message = ErrorMessageService.formatValidationErrors(manv);
            log.warn("VALIDATION_ERROR: {}", message);

        } else if (ex instanceof HttpMessageNotReadableException) {
            message = "POST em endpoint errado ou dados inválidos.";
            log.warn("BAD_REQUEST - POST inválido: {}", ex.getMessage());

        } else if (ex instanceof ConstraintViolationException cve) {
            message = ErrorMessageService.formatConstraintViolations(cve);
            log.warn("CONSTRAINT_VIOLATION: {}", message);

        } else { // IllegalArgumentException ou outros
            message = "Parâmetro inválido enviado.";
            log.warn("BAD_REQUEST - Argumento inválido: {}", ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST, message, request.getRequestURI()));
    }

    // ---------------------- METHOD NOT ALLOWED ----------------------
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.warn("METHOD_NOT_ALLOWED: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ApiError(HttpStatus.METHOD_NOT_ALLOWED,
                        "Método HTTP não permitido para este endpoint.",
                        request.getRequestURI()));
    }

    // ---------------------- BASE EXCEPTIONS ----------------------
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError> handleBaseException(BaseException ex, HttpServletRequest request) {
        log.warn("{}: {} - {}", ex.getStatus(), ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
                .body(new ApiError(ex.getStatus(), ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<ApiError> handleDuplicateMember(DuplicateMemberException ex, HttpServletRequest request) {
        log.warn("CONFLICT: {} - {}", ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler({
            MemberNotFoundException.class,
            WorkoutItemNotFoundException.class,
            ResourceNotFoundException.class
    })
    public ResponseEntity<ApiError> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        log.warn("NOT_FOUND: {} - {}", ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()));
    }

    // ---------------------- INTERNAL SERVER ERROR ----------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex, HttpServletRequest request) {
        log.error("INTERNAL_SERVER_ERROR: {} - {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", request.getRequestURI()));
    }
}
