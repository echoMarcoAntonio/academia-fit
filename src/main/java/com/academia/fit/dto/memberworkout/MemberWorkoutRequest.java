package com.academia.fit.dto.memberworkout;

import com.academia.fit.utils.enums.MemberWorkoutStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de requisição para criar/atualizar um MemberWorkout.
 */
@Getter
@Setter
public class MemberWorkoutRequest {

    @NotNull(message = "O ID do aluno é obrigatório.")
    private Long memberId;

    @NotNull(message = "O ID do treino é obrigatório.")
    private Long workoutId;

    private MemberWorkoutStatus status; // opcional (default ATIVO)
}
