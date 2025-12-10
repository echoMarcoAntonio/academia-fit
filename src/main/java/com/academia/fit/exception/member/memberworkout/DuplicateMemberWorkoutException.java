package com.academia.fit.exception.member.memberworkout;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um treino do aluno já foi atribuído.
 */
public class DuplicateMemberWorkoutException extends BaseException {

    public DuplicateMemberWorkoutException() {
        super(ExceptionMessages.DUPLICATE_MEMBER_WORKOUT, HttpStatus.CONFLICT);
    }

    public DuplicateMemberWorkoutException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
