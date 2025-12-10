package com.academia.fit.exception.member;

import com.academia.fit.exception.base.BaseException;
import com.academia.fit.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando há tentativa de cadastrar um aluno já existente
 * (por e-mail ou CPF duplicado).
 */
public class InvalidStatusException extends BaseException {
     public InvalidStatusException() {
            super(ExceptionMessages.INVALID_STATUS, HttpStatus.BAD_REQUEST);
        }

        public InvalidStatusException(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }
}

