package com.academia.fit.dto.memberworkout;

import com.academia.fit.utils.enums.MemberWorkoutStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de resposta para MemberWorkout.
 * Contém apenas campos primitivos (IDs e status).
 */
@Getter
@Setter
public class MemberWorkoutResponse {
    private Long id;
    private Long memberId;
    private Long workoutId;
    private MemberWorkoutStatus status;
    private LocalDate applicationDate;
}
