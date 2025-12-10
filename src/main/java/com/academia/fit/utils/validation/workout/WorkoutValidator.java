package com.academia.fit.utils.validation.workout;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.resource.ResourceNotFoundException;
import com.academia.fit.exception.workout.DuplicateWorkoutException;
import com.academia.fit.model.workout.Workout;
import com.academia.fit.repository.workout.WorkoutRepository;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por validar regras de negócio
 * relacionadas à entidade Workout (Treino).
 */
@Component
public final class WorkoutValidator {

    private final WorkoutRepository workoutRepository;

    public WorkoutValidator(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    /**
     * Valida se o treino existe no banco.
     *
     * @param id ID do treino.
     * @return objeto Workout existente.
     * @throws ResourceNotFoundException se o treino não for encontrado.
     */
    public Workout validateExistingWorkout(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ExceptionMessages.WORKOUT_NOT_FOUND, id)
                ));
    }

    /**
     * Valida duplicidade de nome e tempo.
     *
     * @param name nome do treino.
     * @param timeInMinutes duração em minutos.
     * @throws DuplicateWorkoutException se já existir treino igual.
     */
    public void validateDuplicate(String name, Integer timeInMinutes) {
        if (workoutRepository.existsByNameAndTimeInMinutes(name, timeInMinutes)) {
            throw new DuplicateWorkoutException(ExceptionMessages.DUPLICATE_WORKOUT);
        }
    }
}
