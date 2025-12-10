package com.academia.fit.exception.exercise;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um exercício não é encontrado no banco de dados.
 */
public class ExerciseNotFoundException extends BaseException {
        public ExerciseNotFoundException() {
            super(ExceptionMessages.EXERCISE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        public ExerciseNotFoundException(String message) {
            super(message, HttpStatus.NOT_FOUND);
        }
}

