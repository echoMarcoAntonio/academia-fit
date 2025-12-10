package com.academia.fit.mapper.member;

import com.academia.fit.dto.memberworkout.MemberWorkoutRequest;
import com.academia.fit.dto.memberworkout.MemberWorkoutResponse;
import com.academia.fit.model.member.MemberWorkout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MemberWorkoutMapper {

    /**
     * Converte DTO em entidade.
     * Campos de relacionamento (member, workout) devem ser setados na Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applicationDate", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Mapping(target = "workout", ignore = true)
    MemberWorkout toEntity(MemberWorkoutRequest request);

    /**
     * Atualiza campos da entidade a partir do DTO (ignora relações, id e applicationDate).
     */
    void updateEntityFromRequest(MemberWorkoutRequest request, @MappingTarget MemberWorkout entity);

    /**
     * Converte entidade em DTO de resposta.
     * Extrai IDs das entidades relacionadas.
     */
    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "workout.id", target = "workoutId")
    MemberWorkoutResponse toResponse(MemberWorkout entity);
}
