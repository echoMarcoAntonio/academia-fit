package com.academia.fit.exception.workout.workoutitem;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um item de treino já foi adicionado ao treino.
 */
public class DuplicateWorkoutItemException extends BaseException {

    public DuplicateWorkoutItemException() {
        super(ExceptionMessages.DUPLICATE_WORKOUT_ITEM, HttpStatus.CONFLICT);
    }

    public DuplicateWorkoutItemException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
