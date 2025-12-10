package com.academia.fit.dto.exercise;

import com.academia.fit.utils.enums.MuscleGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseResponse {
    private Long id;
    private String name;
    private MuscleGroup primaryMuscleGroup;
    private String description;
}
