package com.academia.fit.repository.workout;

import com.academia.fit.model.workout.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    boolean existsByNameAndTimeInMinutes(String name, Integer timeInMinutes);
}
