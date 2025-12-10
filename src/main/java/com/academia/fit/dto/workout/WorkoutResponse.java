package com.academia.fit.dto.workout;


import com.academia.fit.dto.workoutitem.WorkoutItemResponse;
import com.academia.fit.utils.enums.Level;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkoutResponse {
    private Long id;
    private String name;
    private String description;
    private Level level;
    private Integer timeInMinutes;

    /** Lista de itens do treino */
    private List<WorkoutItemResponse> items;
}
