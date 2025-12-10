package com.academia.fit.exception.exercise;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando há tentativa de cadastrar um exercício já existente.
 */
public class DuplicateExerciseException extends BaseException {
    public DuplicateExerciseException() {
        super(ExceptionMessages.DUPLICATE_EXERCISE, HttpStatus.CONFLICT);
    }

    public DuplicateExerciseException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}

