package com.academia.fit.exception.workout;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando há tentativa de cadastrar um treino já existente.
 */
public class DuplicateWorkoutException extends BaseException {

    public DuplicateWorkoutException() {
        super(ExceptionMessages.DUPLICATE_WORKOUT, HttpStatus.CONFLICT);
    }

    public DuplicateWorkoutException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
