package com.academia.fit.dto.workoutitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutItemResponse {
    private Long id;
    private Long exerciseId;
    private String exerciseName;
    private Integer sets;
    private Integer repetitions;
    private Double weightKg;
}
