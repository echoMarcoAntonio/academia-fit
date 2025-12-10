package com.academia.fit.exception.member;

import com.academia.fit.exception.base.BaseException;
import com.academia.fit.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um aluno (Member) não é encontrado no banco de dados.
 */
public class MemberNotFoundException extends BaseException {

    public MemberNotFoundException(Long id) {
        super(String.format(ExceptionMessages.MEMBER_NOT_FOUND, id), HttpStatus.NOT_FOUND);
    }

    public MemberNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
