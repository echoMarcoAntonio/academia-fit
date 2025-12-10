package com.academia.fit.model.member;

import com.academia.fit.exception.member.memberworkout.DuplicateMemberWorkoutException;
import com.academia.fit.model.workout.Workout;
import com.academia.fit.model.workout.WorkoutItem;
import com.academia.fit.utils.enums.MemberWorkoutStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Objects;

import static com.academia.fit.exception.ExceptionMessages.DUPLICATE_MEMBER_WORKOUT;

/**
 * Entidade de domínio que representa um Treino de um Aluno (MemberWorkout) da academia.
 * Mapeada para a tabela 'member_workout'.
 * Inclui regras de negócio para gerenciar MemberWorkout de forma segura.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "member_workout",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"workout_id", "member_id"})
        }
)
public class MemberWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MemberWorkoutStatus status;

    @Builder.Default
    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_item_id")
    private WorkoutItem workoutItem;

    // Hooks JPA
    @PrePersist
    private void prePersist() {
        if (applicationDate == null) {
            applicationDate = LocalDate.now();
        }
        if (status == null) {
            status = MemberWorkoutStatus.ATIVO;
        }
    }

    // ---------------- Regras de negócio ----------------

    /**
     * Valida se já existe um MemberWorkout para este membro e treino.
     * Usando Objects.equals para evitar NullPointerException.
     */
    public void validateDuplicate(MemberWorkout existingMemberWorkout) {
        if (existingMemberWorkout != null
                && Objects.equals(existingMemberWorkout.getWorkout(), this.workout)
                && Objects.equals(existingMemberWorkout.getMember(), this.member)) {
            throw new DuplicateMemberWorkoutException(DUPLICATE_MEMBER_WORKOUT);
        }
    }

    /**
     * Atribui um WorkoutItem a este MemberWorkout
     * e mantém a relação bidirecional.
     */
    public void assignWorkoutItem(WorkoutItem workoutItem) {
        if (workoutItem != null) {
            workoutItem.addMemberWorkout(this); // valida e adiciona bidirecionalmente
            this.workoutItem = workoutItem;
        }
    }

    /**
     * Remove a associação com o WorkoutItem.
     */
    public void removeWorkoutItem() {
        if (this.workoutItem != null) {
            this.workoutItem.getMemberWorkouts().remove(this);
            this.workoutItem = null;
        }
    }
}
