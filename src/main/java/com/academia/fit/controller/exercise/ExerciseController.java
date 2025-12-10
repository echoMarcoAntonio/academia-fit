package com.academia.fit.controller.exercise;

import com.academia.fit.dto.exercise.ExerciseRequest;
import com.academia.fit.dto.exercise.ExerciseResponse;
import com.academia.fit.service.exercise.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST dedicado para gerenciar a entidade Exercise (Exercício).
 * Contém todas as operações CRUD básicas.
 */
@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService service;

    // ---------------------- CREATE ----------------------

    /**
     * Cria um novo exercício.
     *
     * @param request DTO com os dados do exercício.
     * @return O Exercício criado com status 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<ExerciseResponse> createExercise(
            @Valid @RequestBody ExerciseRequest request
    ) {
        ExerciseResponse created = service.createExercise(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------------- READ (LIST ALL PAGINATED) ----------------------

    /**
     * Lista todos os exercícios de forma paginada.
     *
     * @param pageable Parâmetros de paginação e ordenação.
     * @return Uma página de Exercícios com status 200 OK.
     */
    @GetMapping
    public ResponseEntity<Page<ExerciseResponse>> getAllExercises(Pageable pageable) {
        Page<ExerciseResponse> exercises = service.findPaginated(pageable);
        return ResponseEntity.ok(exercises);
    }

    // ---------------------- READ (BY ID) ----------------------

    /**
     * Busca um exercício pelo seu ID.
     *
     * @param id ID do exercício.
     * @return O Exercício encontrado com status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponse> getExerciseById(@PathVariable Long id) {
        ExerciseResponse exercise = service.findById(id);
        return ResponseEntity.ok(exercise);
    }

    // ---------------------- UPDATE ----------------------

    /**
     * Atualiza um exercício existente.
     *
     * @param id      ID do exercício a ser atualizado.
     * @param request DTO com os novos dados.
     * @return O Exercício atualizado com status 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponse> updateExercise(
            @PathVariable Long id,
            @Valid @RequestBody ExerciseRequest request
    ) {
        ExerciseResponse updated = service.updateExercise(id, request);
        return ResponseEntity.ok(updated);
    }

    // ---------------------- DELETE ----------------------

    /**
     * Deleta um exercício pelo seu ID.
     *
     * @param id ID do exercício a ser deletado.
     * @return Resposta 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        service.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}
