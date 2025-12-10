package com.academia.fit.repository.member;

import com.academia.fit.model.member.Member;
import com.academia.fit.model.member.MemberWorkout;
import com.academia.fit.model.workout.Workout;
import com.academia.fit.utils.enums.MemberWorkoutStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para a associação Membro-Treino (treino_do_aluno).
 */
@Repository
public interface MemberWorkoutRepository extends JpaRepository<MemberWorkout, Long> {

    /** Busca a associação ativa para um membro */
    List<MemberWorkout> findByMemberAndStatus(Member member, MemberWorkoutStatus status);

    /** Busca uma associação específica Membro-Treino */
    Optional<MemberWorkout> findByMemberAndWorkout(Member member, Workout workout);

    /** Busca todos os MemberWorkouts de um membro específico (para o service) */
    List<MemberWorkout> findByMember(Member member);
}