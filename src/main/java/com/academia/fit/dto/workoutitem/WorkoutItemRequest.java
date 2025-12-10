package com.academia.fit.dto.workoutitem;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de requisição para criar ou atualizar um item (exercício) dentro de um
 * template de treino (Workout).
 */
@Getter
@Setter
public class WorkoutItemRequest {

    @NotNull(message = "O ID do exercício é obrigatório.")
    @Min(value = 1, message = "O ID do exercício deve ser maior que zero.")
    private Long exerciseId;

    @NotNull(message = "O número de séries é obrigatório.")
    @Min(value = 1, message = "As séries devem ser pelo menos 1.")
    private Integer sets;

    @NotNull(message = "O número de repetições é obrigatório.")
    @Min(value = 1, message = "As repetições devem ser pelo menos 1.")
    private Integer repetitions;

    @NotNull(message = "A carga (em Kg) é obrigatória.")
    @DecimalMin(value = "0.0", inclusive = true, message = "A carga mínima deve ser zero ou maior.")
    private Double weightKg;
}
