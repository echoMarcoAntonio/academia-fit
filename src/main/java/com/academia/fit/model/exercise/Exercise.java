package com.academia.fit.model.exercise;

import com.academia.fit.utils.enums.MuscleGroup;
import com.academia.fit.model.workout.WorkoutItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade que representa um exercício da academia.
 * Reutilizável em diversos treinos (template).
 * Contém informações sobre o tipo de exercício, grupo muscular e descrição.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exercise")
public class Exercise {

    /** Identificador único do exercício **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /** Nome do exercício (único e obrigatório) **/
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    /** Grupo muscular principal trabalhado no exercício **/
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O grupo muscular é obrigatório.")
    @Column(name = "primary_muscle_group", nullable = false, length = 50)
    private MuscleGroup primaryMuscleGroup;

    /** Descrição detalhada do exercício **/
    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
    @Column(name = "description", length = 500)
    private String description;

    /** Campos de auditoria **/
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /** Relação com itens de treino (WorkoutItem) **/
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutItem> workoutItems;
}
