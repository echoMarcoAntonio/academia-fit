package com.academia.fit.exception.workout;

import com.academia.fit.exception.base.BaseException;
import com.academia.fit.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

public class InvalidMuscleGroupException extends BaseException {

    public InvalidMuscleGroupException(String value) {
        super(String.format(ExceptionMessages.INVALID_MUSCLE_GROUP, value), HttpStatus.BAD_REQUEST);
    }
}
