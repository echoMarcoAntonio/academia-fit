package com.academia.fit.utils.validation.exercise;

import com.academia.fit.dto.exercise.ExerciseRequest;
import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.exercise.DuplicateExerciseException;
import com.academia.fit.repository.exercise.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Classe utilitária responsável por validar regras críticas da entidade Exercise.
 * Mantém a lógica de integridade fora da camada de serviço.
 */
@Component
@RequiredArgsConstructor
public final class ExerciseValidator {

    private final ExerciseRepository repository;

    /**
     * Valida os campos obrigatórios e duplicidades de um exercício.
     * @param request DTO de entrada
     * @param id ID do exercício (ou null se for criação)
     */
    public void validate(ExerciseRequest request, Long id) {
        validateName(request.getName(), id);
        validatePrimaryMuscleGroup(request.getPrimaryMuscleGroup());
    }

    private void validateName(String name, Long id) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do exercício é obrigatório.");
        }

        boolean exists = (id == null)
                ? repository.existsByName(name.trim())
                : repository.existsByNameAndIdNot(name.trim(), id);

        if (exists) {
            throw new DuplicateExerciseException(ExceptionMessages.DUPLICATE_EXERCISE);
        }
    }

    private void validatePrimaryMuscleGroup(Enum<?> primaryMuscleGroup) {
        if (primaryMuscleGroup == null) {
            throw new IllegalArgumentException("O grupo muscular principal é obrigatório.");
        }
    }
}
