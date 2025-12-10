package com.academia.fit.service.workout;

import com.academia.fit.dto.workout.WorkoutRequest;
import com.academia.fit.dto.workoutitem.WorkoutItemRequest;
import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.resource.ResourceNotFoundException;
import com.academia.fit.exception.workout.DuplicateWorkoutException;
import com.academia.fit.model.exercise.Exercise;
import com.academia.fit.model.workout.Workout;
import com.academia.fit.model.workout.WorkoutItem;
import com.academia.fit.repository.workout.WorkoutItemRepository;
import com.academia.fit.repository.workout.WorkoutRepository;
import com.academia.fit.service.exercise.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutService {

    private final WorkoutRepository repository;
    private final WorkoutItemRepository workoutItemRepository;
    private final ExerciseService exerciseService;

    /**
     * Cria um novo treino a partir de um DTO
     */
    public Workout createWorkout(WorkoutRequest request) {

        if (repository.existsByNameAndTimeInMinutes(request.getName(), request.getTimeInMinutes())) {
            throw new DuplicateWorkoutException(ExceptionMessages.DUPLICATE_WORKOUT);
        }

        // Cria a entidade Workout
        Workout workout = Workout.builder()
                .name(request.getName())
                .description(request.getDescription())
                .level(request.getLevel())
                .timeInMinutes(request.getTimeInMinutes())
                .build();

        // Adiciona itens usando o método addWorkoutItem para evitar duplicidade
        if (request.getItems() != null) {
            for (WorkoutItemRequest itemRequest : request.getItems()) {
                Exercise exercise = exerciseService.getEntity(itemRequest.getExerciseId());

                WorkoutItem item = WorkoutItem.builder()
                        .exercise(exercise)
                        .sets(itemRequest.getSets())
                        .repetitions(itemRequest.getRepetitions())
                        .weightKg(itemRequest.getWeightKg())
                        .build();

                workout.addWorkoutItem(item);
            }
        }

        return repository.save(workout);
    }

    /**
     * Retorna todos os treinos de forma paginada
     */
    public Page<Workout> findPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Retorna um treino pelo ID
     */
    public Workout findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ExceptionMessages.WORKOUT_NOT_FOUND, id)
                ));
    }

    /**
     * Atualiza os dados do treino
     */
    public Workout updateWorkout(Long id, WorkoutRequest request) {
        Workout workout = findById(id);

        workout.setName(request.getName());
        workout.setDescription(request.getDescription());
        workout.setLevel(request.getLevel());
        workout.setTimeInMinutes(request.getTimeInMinutes());
        return repository.save(workout);
    }

    /**
     * Remove um treino pelo ID
     */
    public void deleteWorkout(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}
