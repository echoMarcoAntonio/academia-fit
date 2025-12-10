package com.academia.fit.repository.workout;

import com.academia.fit.model.exercise.Exercise;
import com.academia.fit.model.workout.Workout;
import com.academia.fit.model.workout.WorkoutItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutItemRepository extends JpaRepository<WorkoutItem, Long> {
    List<WorkoutItem> findByWorkout(Workout workout);

    Optional<WorkoutItem> findByWorkoutAndExercise(Workout workout, Exercise exercise);
}
