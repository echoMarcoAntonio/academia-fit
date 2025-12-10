package com.academia.fit.exception.workout;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um treino (Workout) não é encontrado.
 */
public class WorkoutNotFoundException extends BaseException {
    public WorkoutNotFoundException(Long id) {
        super(String.format(ExceptionMessages.WORKOUT_NOT_FOUND, id), HttpStatus.NOT_FOUND);
    }

    public WorkoutNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
