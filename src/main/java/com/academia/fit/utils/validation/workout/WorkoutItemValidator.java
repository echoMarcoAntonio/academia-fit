package com.academia.fit.utils.validation.workout;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.resource.ResourceNotFoundException;
import com.academia.fit.exception.workout.workoutitem.DuplicateWorkoutItemException;
import com.academia.fit.model.exercise.Exercise;
import com.academia.fit.model.workout.Workout;
import com.academia.fit.model.workout.WorkoutItem;
import com.academia.fit.repository.workout.WorkoutItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe responsável por validar regras de negócio
 * relacionadas à entidade WorkoutItem (item de treino).
 */
@Component
public final class WorkoutItemValidator {

    private final WorkoutItemRepository workoutItemRepository;

    public WorkoutItemValidator(WorkoutItemRepository workoutItemRepository) {
        this.workoutItemRepository = workoutItemRepository;
    }

    /**
     * Valida se o item de treino existe no banco.
     *
     * @param id ID do item.
     * @return objeto WorkoutItem existente.
     * @throws ResourceNotFoundException se o item não for encontrado.
     */
    public WorkoutItem validateExistingItem(Long id) {
        return workoutItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ExceptionMessages.WORKOUT_ITEM_NOT_FOUND, id)
                ));
    }

    /**
     * Retorna todos os itens de um treino.
     *
     * @param workout treino a ser consultado.
     * @return lista de itens de treino.
     */
    public List<WorkoutItem> getItemsByWorkout(Workout workout) {
        return workoutItemRepository.findByWorkout(workout);
    }

    /**
     * Garante que o mesmo exercício não seja adicionado
     * mais de uma vez ao mesmo treino.
     *
     * @param workout treino.
     * @param exercise exercício.
     * @throws DuplicateWorkoutItemException se o exercício já estiver vinculado.
     */
    public void validateDuplicateExercise(Workout workout, Exercise exercise) {
        if (workoutItemRepository.findByWorkoutAndExercise(workout, exercise).isPresent()) {
            throw new DuplicateWorkoutItemException(ExceptionMessages.DUPLICATE_WORKOUT_ITEM);
        }
    }
}
