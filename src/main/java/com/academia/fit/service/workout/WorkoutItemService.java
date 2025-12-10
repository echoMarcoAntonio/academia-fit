package com.academia.fit.service.workout;

import com.academia.fit.dto.workoutitem.WorkoutItemRequest;
import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.resource.ResourceNotFoundException;
import com.academia.fit.exception.workout.workoutitem.DuplicateWorkoutItemException;
import com.academia.fit.model.exercise.Exercise;
import com.academia.fit.model.member.MemberWorkout;
import com.academia.fit.model.workout.Workout;
import com.academia.fit.model.workout.WorkoutItem;
import com.academia.fit.repository.workout.WorkoutItemRepository;
import com.academia.fit.service.exercise.ExerciseService;
import com.academia.fit.service.member.MemberWorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutItemService {

    /**
     * Busca Workout e Exercise antes de criar o item
     */
    private final WorkoutItemRepository workoutItemRepository;
    private final ExerciseService exerciseService;
    private final MemberWorkoutService memberWorkoutService;

    public WorkoutItem addWorkoutItemToWorkout(Long memberWorkoutId, WorkoutItemRequest request) {
        MemberWorkout memberWorkout = memberWorkoutService.findByIdOrThrow(memberWorkoutId);
        Exercise exercise = exerciseService.getEntityById(request.getExerciseId());

        boolean exists = workoutItemRepository
                .findByWorkoutAndExercise(memberWorkout.getWorkout(), exercise)
                .isPresent();
        if (exists) {
            throw new DuplicateWorkoutItemException(ExceptionMessages.DUPLICATE_WORKOUT_ITEM);
        }

        WorkoutItem item = new WorkoutItem();
        item.setWorkout(memberWorkout.getWorkout());
        item.setExercise(exercise);
        item.setSets(request.getSets());
        item.setRepetitions(request.getRepetitions());
        item.setWeightKg(request.getWeightKg());

        return workoutItemRepository.save(item);
    }

    /**
     * Retorna todos os itens de treino
     */
    public List<WorkoutItem> findAll() {
        return workoutItemRepository.findAll();
    }

    /**
     * Retorna todos os itens de um treino aplicado a um membro
     */
    public List<WorkoutItem> findByMemberWorkout(Long memberWorkoutId) {
        MemberWorkout mw = memberWorkoutService.findByIdOrThrow(memberWorkoutId); // <--- use este

        Workout workout = mw.getWorkout();
        return workoutItemRepository.findByWorkout(workout);
    }

    /**
     * Busca item pelo ID
     */
    public WorkoutItem findById(Long id) {
        return workoutItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ExceptionMessages.WORKOUT_ITEM_NOT_FOUND, id)
                ));
    }

    /**
     * Atualiza item de treino usando DTO.
     */
    public WorkoutItem update(Long id, WorkoutItemRequest request) {
        WorkoutItem existing = findById(id);

        existing.setSets(request.getSets());
        existing.setRepetitions(request.getRepetitions());
        existing.setWeightKg(request.getWeightKg());

        return workoutItemRepository.save(existing);
    }

    /**
     * Deleta item de treino
     */
    public void deleteWorkoutItem(Long id) {
        findById(id); // garante exceção se não existir
        workoutItemRepository.deleteById(id);
    }
}
