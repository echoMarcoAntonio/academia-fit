package com.academia.fit.exception.member;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class BirthDateChangeNotAllowedException extends BaseException {

    public BirthDateChangeNotAllowedException() {
        super(ExceptionMessages.BIRTHDATE_CHANGE_NOT_ALLOWED, HttpStatus.BAD_REQUEST);
    }

    public BirthDateChangeNotAllowedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
