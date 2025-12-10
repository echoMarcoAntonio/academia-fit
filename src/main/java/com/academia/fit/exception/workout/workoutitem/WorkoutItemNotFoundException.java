package com.academia.fit.exception.workout.workoutitem;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um item de treino não é encontrado.
 */
public class WorkoutItemNotFoundException extends BaseException {

    public WorkoutItemNotFoundException(Long id) {
        super(String.format(ExceptionMessages.WORKOUT_ITEM_NOT_FOUND, id), HttpStatus.NOT_FOUND);
    }

    public WorkoutItemNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
