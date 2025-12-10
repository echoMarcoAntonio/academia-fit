package com.academia.fit.model.workout;

import com.academia.fit.exception.workout.workoutitem.DuplicateWorkoutItemException;
import com.academia.fit.utils.enums.Level;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.academia.fit.exception.ExceptionMessages.DUPLICATE_WORKOUT_ITEM;

/**
 * Entidade que representa um plano de treino base.
 * Pode ser atribuído a vários alunos (MemberWorkout).
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "workout",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private Long id;

    @EqualsAndHashCode.Include
    @NotBlank(message = "O nome do treino é obrigatório.")
    @Size(min = 5, max = 100, message = "O nome do treino deve ter entre 5 e 100 caracteres.")
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Lob
    @NotBlank(message = "A descrição é obrigatória.")
    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private Level level;

    @NotNull(message = "A duração do treino é obrigatória.")
    @Min(value = 10, message = "A duração mínima deve ser 10 minutos.")
    @Column(name = "time_in_minutes", nullable = false)
    private Integer timeInMinutes;

    /** Relação com os itens do treino base */
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkoutItem> workoutItems = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Hooks JPA

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    // ------------------ Regras de negócio ------------------


    /**
     * Valida se já existe um WorkoutItem com o mesmo Exercise.
     */
    private void validateDuplicateWorkoutItem(WorkoutItem workoutItem) {
        boolean exists = workoutItems.stream()
                .anyMatch(i -> Objects.equals(i.getExercise(), workoutItem.getExercise()));
        if (exists) {
            throw new DuplicateWorkoutItemException(DUPLICATE_WORKOUT_ITEM);
        }
    }

    /**
     * Adiciona um WorkoutItem garantindo duplicidade.
     */
    public void addWorkoutItem(WorkoutItem workoutItem) {
        validateDuplicateWorkoutItem(workoutItem);
        workoutItems.add(workoutItem);
        workoutItem.setWorkout(this);
    }

    public void removeWorkoutItem(WorkoutItem workoutItem) {
        workoutItems.remove(workoutItem);
        workoutItem.setWorkout(null);
    }
}