package com.academia.fit.service.exercise;

import com.academia.fit.dto.exercise.ExerciseRequest;
import com.academia.fit.dto.exercise.ExerciseResponse;
import com.academia.fit.exception.exercise.ExerciseNotFoundException;
import com.academia.fit.model.exercise.Exercise;
import com.academia.fit.repository.exercise.ExerciseRepository;
import com.academia.fit.utils.enums.MuscleGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.academia.fit.mapper.exercise.ExerciseMapper;

class ExerciseServiceTest {

    @Mock
    private ExerciseRepository repository;

    @InjectMocks
    private ExerciseService service;

    private Exercise exercise;
    private ExerciseRequest request;
    private ExerciseResponse response;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Criando Exercise
        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Supino Reto");
        exercise.setPrimaryMuscleGroup(MuscleGroup.CHEST);
        exercise.setDescription("Exercício clássico de peitoral.");

        // Criando ExerciseRequest
        request = new ExerciseRequest();
        request.setName("Supino Reto");
        request.setDescription("Exercício clássico de peitoral.");
        request.setPrimaryMuscleGroup(MuscleGroup.CHEST);

        // Criando ExerciseResponse usando mapper estático
        response = ExerciseMapper.toResponse(exercise);
    }

    @Test
    void shouldReturnExerciseById() {
        when(repository.findById(1L)).thenReturn(Optional.of(exercise));

        ExerciseResponse result = service.findById(1L);

        assertNotNull(result);
        assertEquals("Supino Reto", result.getName());
        assertEquals(MuscleGroup.CHEST, result.getPrimaryMuscleGroup());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenExerciseNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> service.findById(99L));

        verify(repository, times(1)).findById(99L);
    }

    @Test
    void shouldCreateExerciseSuccessfully() {
        when(repository.save(any(Exercise.class))).thenReturn(exercise);

        ExerciseResponse created = service.createExercise(request);

        assertNotNull(created);
        assertEquals("Supino Reto", created.getName());
        assertEquals(MuscleGroup.CHEST, created.getPrimaryMuscleGroup());
        verify(repository, times(1)).save(any(Exercise.class));
    }

    @Test
    void shouldUpdateExerciseSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(exercise));
        when(repository.save(any(Exercise.class))).thenReturn(exercise);

        ExerciseResponse updated = service.updateExercise(1L, request);

        assertNotNull(updated);
        assertEquals("Supino Reto", updated.getName());
        assertEquals(MuscleGroup.CHEST, updated.getPrimaryMuscleGroup());
        verify(repository, times(1)).save(any(Exercise.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentExercise() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> service.updateExercise(99L, request));
    }

    @Test
    void shouldDeleteExerciseSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(exercise));

        service.deleteExercise(1L);

        verify(repository, times(1)).delete(exercise); // <-- ajustado para delete(entity)
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentExercise() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> service.deleteExercise(99L));

        verify(repository, never()).delete(any(Exercise.class));
    }
}
