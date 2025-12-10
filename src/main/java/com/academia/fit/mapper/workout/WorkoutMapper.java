package com.academia.fit.mapper.workout;

import com.academia.fit.dto.workout.WorkoutRequest;
import com.academia.fit.dto.workout.WorkoutResponse;
import com.academia.fit.model.workout.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WorkoutMapper {

    /** Converte DTO para entidade */
    Workout toEntity(WorkoutRequest request);

    /** Atualiza entidade existente a partir do DTO */
    void updateEntityFromRequest(WorkoutRequest request, @MappingTarget Workout workout);

    /** Converte a entidade para DTO de resposta */
    WorkoutResponse toResponse(Workout workout);
}
