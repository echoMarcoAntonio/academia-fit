package com.academia.fit.exception.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * DTO padrão para respostas de erro REST.
 */
@Getter
public class ApiError {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final HttpStatus status;
    private final int statusCode;
    private final String message;
    private final String path;

    public ApiError(HttpStatus status, String message, String path) {
        this.status = status;
        this.statusCode = status.value();
        this.message = message;
        this.path = path;
    }
}
