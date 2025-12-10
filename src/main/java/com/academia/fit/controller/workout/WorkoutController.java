package com.academia.fit.controller.workout;

import com.academia.fit.dto.workout.WorkoutRequest;
import com.academia.fit.model.workout.Workout;
import com.academia.fit.service.workout.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST dedicado para gerenciar a entidade Workout (Planos de Treino: Conjunto de WorkoutItems).
 */
@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService service;

    // ---------------------- CREATE ----------------------
    /**
     * Cria um novo plano de treino.
     * @param request DTO com os dados do treino.
     * @return O Template de Treino criado com status 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<Workout> addWorkoutItemToWorkout(
            @Valid @RequestBody WorkoutRequest request
    ) {
        Workout created = service.createWorkout(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------------- READ (LIST ALL PAGINATED) ----------------------
    /** * Lista todos os planos de treino de forma paginada.
     * @param pageable Parâmetros de paginação e ordenação.
     * @return Uma página de Workouts com status 200 OK.
     */
    @GetMapping
    public ResponseEntity<Page<Workout>> getAllWorkouts(Pageable pageable) {
        return ResponseEntity.ok(service.findPaginated(pageable));
    }

    // ---------------------- READ (BY ID) ----------------------
    /**
     * Busca um plano de treino pelo seu ID.
     * @param id ID do plano de treino.
     * @return O Workout encontrado com status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // ---------------------- UPDATE ----------------------
    /**
     * Atualiza um plano de treino existente.
     * @param id ID do treino a ser atualizado.
     * @param request DTO com os novos dados.
     * @return O Workout atualizado com status 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutRequest request
    ) {
        return ResponseEntity.ok(service.updateWorkout(id, request));
    }

    // ---------------------- DELETE ----------------------
    /**
     * Deleta um plano de treino pelo seu ID.
     * @param id ID do treino a ser deletado.
     * @return Resposta 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        service.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}