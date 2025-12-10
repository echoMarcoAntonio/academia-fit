package com.academia.fit.dto.workout;

import com.academia.fit.dto.workoutitem.WorkoutItemRequest;
import com.academia.fit.utils.enums.Level;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO de requisição para criar ou atualizar um template de treino (Workout).
 */
@Getter
@Setter
public class WorkoutRequest {

    @NotBlank(message = "O nome do treino é obrigatório.")
    @Size(max = 100, message = "O nome não pode exceder 100 caracteres.")
    private String name;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
    private String description;

    @NotNull(message = "O nível de dificuldade é obrigatório.")
    private Level level;

    @NotNull(message = "A duração é obrigatória.")
    @Min(value = 10, message = "A duração mínima do treino deve ser de 10 minutos.")
    private Integer timeInMinutes;

    /** Lista de itens do treino */
    private List<WorkoutItemRequest> items;
}
