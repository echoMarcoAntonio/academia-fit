package com.academia.fit.service.member;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.resource.ResourceNotFoundException;
import com.academia.fit.model.member.Member;
import com.academia.fit.model.member.MemberWorkout;
import com.academia.fit.model.workout.Workout;
import com.academia.fit.repository.member.MemberWorkoutRepository;
import com.academia.fit.utils.enums.MemberWorkoutStatus;
import com.academia.fit.utils.validation.member.MemberWorkoutValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar os vínculos entre Membros e Treinos.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MemberWorkoutService {

    private final MemberWorkoutRepository memberWorkoutRepository;
    private final MemberWorkoutValidator validator;

    /**
     * Cria um novo vínculo entre Member e Workout.
     */
    public MemberWorkout createMemberWorkout(Long memberId, Long workoutId) {
        // Valida existência das entidades
        Member member = validator.validateExistingMember(memberId);
        Workout workout = validator.validateExistingWorkout(workoutId);

        // Valida duplicidade
        validator.validateDuplicate(member, workout);

        // Cria associação
        MemberWorkout newMemberWorkout = new MemberWorkout();
        newMemberWorkout.setMember(member);
        newMemberWorkout.setWorkout(workout);
        newMemberWorkout.setStatus(MemberWorkoutStatus.ATIVO);

        return memberWorkoutRepository.save(newMemberWorkout);
    }

    /**
     * Busca MemberWorkout por ID.
     */
    @Transactional(readOnly = true)
    public MemberWorkout findByIdOrThrow(Long id) {
        return memberWorkoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ExceptionMessages.MEMBER_WORKOUT_NOT_FOUND, id)
                ));
    }

    /**
     * Retorna todos os vínculos.
     */
    @Transactional(readOnly = true)
    public List<MemberWorkout> findAll() {
        return memberWorkoutRepository.findAll();
    }

    /**
     * Busca vínculos por membro.
     */
    @Transactional(readOnly = true)
    public List<MemberWorkout> findByMember(Member member) {
        return memberWorkoutRepository.findByMember(member);
    }

    /**
     * Busca vínculos ativos por membro.
     */
    @Transactional(readOnly = true)
    public List<MemberWorkout> findActiveByMember(Member member) {
        return memberWorkoutRepository.findByMemberAndStatus(member, MemberWorkoutStatus.ATIVO);
    }

    /**
     * Busca vínculo específico de membro e treino.
     */
    @Transactional(readOnly = true)
    public Optional<MemberWorkout> findByMemberAndWorkout(Member member, Workout workout) {
        return memberWorkoutRepository.findByMemberAndWorkout(member, workout);
    }

    /**
     * Valida existência antes de operações dependentes.
     */
    @Transactional(readOnly = true)
    public MemberWorkout validateAndGet(Member member, Workout workout) {
        return findByMemberAndWorkout(member, workout)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "MemberWorkout not found for member " + member.getId() +
                                " and workout " + workout.getId()
                ));
    }

    /**
     * Exclui vínculo pelo ID, lançando exceção se não encontrado.
     */
    public void deleteByIdOrThrow(Long id) {
        MemberWorkout mw = findByIdOrThrow(id);
        memberWorkoutRepository.delete(mw);
    }
}
