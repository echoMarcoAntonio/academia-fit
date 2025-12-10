package com.academia.fit.exception.member.memberworkout;

import com.academia.fit.exception.base.BaseException;
import com.academia.fit.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um treino do aluno não é encontrado.
 */
public class MemberWorkoutNotFoundException extends BaseException {

    public MemberWorkoutNotFoundException(Long id) {
        super(String.format(ExceptionMessages.MEMBER_WORKOUT_NOT_FOUND, id), HttpStatus.NOT_FOUND);
    }

    public MemberWorkoutNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
