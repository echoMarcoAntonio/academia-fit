package com.academia.fit.controller.workout;

import com.academia.fit.dto.workoutitem.WorkoutItemRequest;
import com.academia.fit.model.workout.WorkoutItem;
import com.academia.fit.service.workout.WorkoutItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST dedicado para gerenciar itens de treino (WorkoutItem)
 * que são os exercícios específicos dentro de um treino atribuído.
 */
@RestController
@RequestMapping("/api/member-workouts/{memberWorkoutId}/items")
@RequiredArgsConstructor
public class WorkoutItemController {

    private final WorkoutItemService service;

    // ---------------------- CREATE ----------------------
    /**
     * Cria um novo item de treino (exercício dentro de um treino).
     * @param request DTO contendo os dados do item (séries, reps, carga, IDs).
     * @return O WorkoutItem criado com status 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<WorkoutItem> addWorkoutItem(
            @PathVariable Long memberWorkoutId, @Valid @RequestBody WorkoutItemRequest request
    ) {
        WorkoutItem created = service.addWorkoutItemToWorkout(memberWorkoutId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------------- READ (BY MEMBER WORKOUT ID) ----------------------
    /**
     * Busca todos os itens de treino (exercícios) associados a um treino atribuído específico.
     * @param memberWorkoutId ID do MemberWorkout (treino do aluno).
     * @return Uma lista de WorkoutItems com status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<WorkoutItem>> getWorkoutItems(@PathVariable Long memberWorkoutId) {
        return ResponseEntity.ok(service.findByMemberWorkout(memberWorkoutId));
    }

    // ---------------------- READ (BY ITEM ID) ----------------------
    /**
     * Busca um item de treino pelo seu ID.
     * @param itemId ID do item de treino.
     * @return O WorkoutItem encontrado com status 200 OK.
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<WorkoutItem> getWorkoutItemById(@PathVariable Long itemId) {
        return ResponseEntity.ok(service.findById(itemId));
    }

    // ---------------------- UPDATE ----------------------
    /**
     * Atualiza um item de treino existente.
     * @param itemId ID do item de treino a ser atualizado.
     * @param request DTO com os novos dados (séries, reps, carga).
     * @return O WorkoutItem atualizado com status 200 OK.
     */
    @PutMapping("/{itemId}")
    public ResponseEntity<WorkoutItem> updateWorkoutItem(
            @PathVariable Long itemId,
            @Valid @RequestBody WorkoutItemRequest request
    ) {
        WorkoutItem updated = service.update(itemId, request);
        return ResponseEntity.ok(updated);
    }

    // ---------------------- DELETE ----------------------
    /**
     * Deleta um item de treino pelo seu ID.
     * @param itemId ID do item de treino a ser deletado.
     * @return Resposta 204 NO CONTENT.
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteWorkoutItem(@PathVariable Long itemId) {
        service.deleteWorkoutItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
