package com.academia.fit.exception.member;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class CpfChangeNotAllowedException extends BaseException {

    public CpfChangeNotAllowedException() {
        super(ExceptionMessages.CPF_CHANGE_NOT_ALLOWED, HttpStatus.BAD_REQUEST);
    }

    public CpfChangeNotAllowedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
