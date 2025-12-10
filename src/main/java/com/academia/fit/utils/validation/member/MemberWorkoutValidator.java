package com.academia.fit.utils.validation.member;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.member.memberworkout.DuplicateMemberWorkoutException;
import com.academia.fit.exception.resource.ResourceNotFoundException;
import com.academia.fit.model.member.Member;
import com.academia.fit.model.member.MemberWorkout;
import com.academia.fit.model.workout.Workout;
import com.academia.fit.repository.member.MemberRepository;
import com.academia.fit.repository.member.MemberWorkoutRepository;
import com.academia.fit.repository.workout.WorkoutRepository;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por validar regras de negócio relacionadas
 * ao vínculo entre Member e Workout (MemberWorkout).
 */
@Component
public class MemberWorkoutValidator {

    private final MemberRepository memberRepository;
    private final WorkoutRepository workoutRepository;
    private final MemberWorkoutRepository memberWorkoutRepository;

    public MemberWorkoutValidator(MemberRepository memberRepository,
                                  WorkoutRepository workoutRepository,
                                  MemberWorkoutRepository memberWorkoutRepository) {
        this.memberRepository = memberRepository;
        this.workoutRepository = workoutRepository;
        this.memberWorkoutRepository = memberWorkoutRepository;
    }

    /**
     * Garante que o membro informado exista no banco de dados.
     *
     * @param memberId ID do membro.
     * @return o objeto Member existente.
     * @throws ResourceNotFoundException se o membro não for encontrado.
     */
    public Member validateExistingMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ExceptionMessages.MEMBER_NOT_FOUND, memberId)
                ));
    }

    /**
     * Garante que o treino informado exista no banco de dados.
     *
     * @param workoutId ID do treino.
     * @return o objeto Workout existente.
     * @throws ResourceNotFoundException se o treino não for encontrado.
     */
    public Workout validateExistingWorkout(Long workoutId) {
        return workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ExceptionMessages.WORKOUT_NOT_FOUND, workoutId)
                ));
    }

    /**
     * Verifica se o vínculo entre o membro e o treino já existe,
     * evitando duplicidade no banco.
     *
     * @param member  membro a ser verificado.
     * @param workout treino a ser verificado.
     * @throws DuplicateMemberWorkoutException se o vínculo já existir.
     */
    public void validateDuplicate(Member member, Workout workout) {
        if (memberWorkoutRepository.findByMemberAndWorkout(member, workout).isPresent()) {
            throw new DuplicateMemberWorkoutException(ExceptionMessages.DUPLICATE_MEMBER_WORKOUT);
        }
    }
}
