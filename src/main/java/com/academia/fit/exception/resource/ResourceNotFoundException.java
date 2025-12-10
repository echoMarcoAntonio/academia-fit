package com.academia.fit.exception.resource;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exceção genérica quando algum recurso não é encontrado.
 */
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException() {
        super(ExceptionMessages.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
