package com.academia.fit.dto.exercise;

import com.academia.fit.utils.enums.MuscleGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRequest {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50)
    private String name;

    @NotNull(message = "O grupo muscular é obrigatório")
    private MuscleGroup primaryMuscleGroup;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres")
    private String description;
}
