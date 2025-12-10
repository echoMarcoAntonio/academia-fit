package com.academia.fit.service.exercise;

import com.academia.fit.dto.exercise.ExerciseRequest;
import com.academia.fit.dto.exercise.ExerciseResponse;
import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.exercise.DuplicateExerciseException;
import com.academia.fit.exception.exercise.ExerciseNotFoundException;
import com.academia.fit.mapper.exercise.ExerciseMapper;
import com.academia.fit.model.exercise.Exercise;
import com.academia.fit.repository.exercise.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Camada de serviço responsável por aplicar regras de negócio
 * e orquestrar as operações CRUD da entidade Exercise (Exercício).
 */
@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository repository;

    /**
     * Cria um novo exercício.
     *
     * @param request DTO contendo os dados do exercício.
     * @return DTO de resposta do exercício criado.
     * @throws DuplicateExerciseException se já existir um exercício com o mesmo nome.
     */
    @Transactional
    public ExerciseResponse createExercise(ExerciseRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new DuplicateExerciseException(ExceptionMessages.DUPLICATE_EXERCISE);
        }

        Exercise exercise = ExerciseMapper.toEntity(request);
        Exercise savedExercise = repository.save(exercise);
        return ExerciseMapper.toResponse(savedExercise);
    }

    /**
     * Lista todos os exercícios de forma paginada.
     *
     * @param pageable parâmetros de paginação
     * @return página de DTOs de exercício
     */
    @Transactional(readOnly = true)
    public Page<ExerciseResponse> findPaginated(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ExerciseMapper::toResponse);
    }

    /**
     * Retorna a entidade Exercise pelo ID (uso exclusivo para outros serviços
     * que precisam da entidade para construir relacionamentos).
     *
     * @param id ID do exercício
     * @return Entidade Exercise
     * @throws ExerciseNotFoundException se não encontrado
     */
    @Transactional(readOnly = true)
    public Exercise getEntity(Long id) {
        return getEntityById(id); // Se getEntityById falhar, a exceção é propagada
    }

    /**
     * Busca um exercício pelo ID.
     *
     * @param id ID do exercício
     * @return DTO de exercício
     * @throws ExerciseNotFoundException se não encontrado
     */
    @Transactional(readOnly = true)
    private Exercise getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(ExceptionMessages.EXERCISE_NOT_FOUND));
    }

    /**
     * Atualiza um exercício existente.
     *
     * @param id ID do exercício
     * @param request DTO com novos dados
     * @return DTO do exercício atualizado
     * @throws ExerciseNotFoundException se não encontrado
     * @throws DuplicateExerciseException se o nome já existir
     */
    @Transactional
    public ExerciseResponse updateExercise(Long id, ExerciseRequest request) {
        Exercise existingExercise = repository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(ExceptionMessages.EXERCISE_NOT_FOUND));

        if (repository.existsByNameAndIdNot(request.getName(), id)) {
            throw new DuplicateExerciseException(ExceptionMessages.DUPLICATE_EXERCISE);
        }

        ExerciseMapper.updateEntity(existingExercise, request);
        Exercise updatedExercise = repository.save(existingExercise);

        return ExerciseMapper.toResponse(updatedExercise);
    }

    /**
     * Remove um exercício pelo ID.
     *
     * @param id ID do exercício
     * @throws ExerciseNotFoundException se não encontrado
     */
    @Transactional
    public void deleteExercise(Long id) {
        Exercise exercise = repository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(ExceptionMessages.EXERCISE_NOT_FOUND));
        repository.delete(exercise);
    }

    /**
     * Retorna um exercício pelo ID em formato DTO.
     *
     * @param id ID do exercício
     * @return DTO do exercício encontrado
     * @throws ExerciseNotFoundException se não encontrado
     */
    @Transactional(readOnly = true)
    public ExerciseResponse findById(Long id) {
        Exercise exercise = repository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(ExceptionMessages.EXERCISE_NOT_FOUND));

        return ExerciseMapper.toResponse(exercise);
    }

}
