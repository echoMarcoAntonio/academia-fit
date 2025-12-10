package com.academia.fit.model.workout;

import com.academia.fit.exception.member.memberworkout.DuplicateMemberWorkoutException;
import com.academia.fit.model.exercise.Exercise;
import com.academia.fit.model.member.MemberWorkout;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.academia.fit.exception.ExceptionMessages.DUPLICATE_MEMBER_WORKOUT;

/**
 * Representa um item individual de um treino (Workout).
 * Pode ser associado a múltiplos MemberWorkout.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "workout_item",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"workout_id", "exercise_id"})
        }
)
public class WorkoutItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Treino base (plano reutilizável) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    /** Exercício associado */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    /** Número de séries (mínimo 1) */
    @Min(1)
    @Column(name = "sets", nullable = false)
    private Integer sets;

    /** Número de repetições por série (mínimo 1) */
    @Min(1)
    @Column(name = "repetitions", nullable = false)
    private Integer repetitions;

    /** Carga utilizada em kg (mínimo 0.0) */
    @DecimalMin("0.0")
    @Column(name = "weight_kg", nullable = false)
    private Double weightKg;

    /** Data de criação do registro */
    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /** Data da última atualização do registro */
    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** MemberWorkouts associados a este item para histórico e rastreamento */
    @OneToMany(mappedBy = "workoutItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<MemberWorkout> memberWorkouts = new HashSet<>();

    // Regras de negócio

    /**
     * Valida se já existe um MemberWorkout associado ao mesmo membro.
     * Lança DuplicateMemberWorkoutException se duplicado.
     */
    public void validateDuplicate(MemberWorkout memberWorkout) {
        boolean exists = memberWorkouts.stream()
                .anyMatch(mw -> Objects.equals(mw.getMember(), memberWorkout.getMember()));
        if (exists) {
            throw new DuplicateMemberWorkoutException(DUPLICATE_MEMBER_WORKOUT);
        }
    }

    /**
     * Adiciona um MemberWorkout ao conjunto, evitando duplicidade para o mesmo membro.
     * Mantém a relação bidirecional.
     */
    public void addMemberWorkout(MemberWorkout memberWorkout) {
        validateDuplicate(memberWorkout); // chama validação centralizada
        memberWorkouts.add(memberWorkout);
        memberWorkout.setWorkoutItem(this); // bi-direcional
    }

    /**
     * Remove um MemberWorkout do conjunto, também desassociando do item.
     */
    public void removeMemberWorkout(MemberWorkout memberWorkout) {
        memberWorkouts.remove(memberWorkout);
        memberWorkout.setWorkoutItem(null);
    }
}
