package com.academia.fit.exception.member;

import com.academia.fit.exception.base.BaseException;
import com.academia.fit.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando há tentativa de cadastrar um aluno já existente
 * (por e-mail ou CPF duplicado).
 */
public class DuplicateMemberException extends BaseException {

    public DuplicateMemberException() {
        super(ExceptionMessages.DUPLICATE_MEMBER, HttpStatus.BAD_REQUEST);
    }

    public DuplicateMemberException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
