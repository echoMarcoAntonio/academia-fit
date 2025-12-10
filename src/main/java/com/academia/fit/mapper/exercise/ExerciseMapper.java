package com.academia.fit.mapper.exercise;

import com.academia.fit.dto.exercise.ExerciseRequest;
import com.academia.fit.dto.exercise.ExerciseResponse;
import com.academia.fit.model.exercise.Exercise;

public interface ExerciseMapper {

    public static ExerciseResponse toResponse(Exercise exercise) {
        if (exercise == null) return null;

        ExerciseResponse response = new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getPrimaryMuscleGroup(),
                exercise.getDescription()
        );
        return response;
    }

    public static Exercise toEntity(ExerciseRequest request) {
        if (request == null) return null;

        Exercise exercise = new Exercise();
        exercise.setName(request.getName());
        exercise.setPrimaryMuscleGroup(request.getPrimaryMuscleGroup());
        exercise.setDescription(request.getDescription());
        return exercise;
    }

    public static void updateEntity(Exercise exercise, ExerciseRequest request) {
        if (exercise == null || request == null) return;

        exercise.setName(request.getName());
        exercise.setPrimaryMuscleGroup(request.getPrimaryMuscleGroup());
        exercise.setDescription(request.getDescription());
    }
}
