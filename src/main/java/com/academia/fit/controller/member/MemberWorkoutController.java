package com.academia.fit.controller.member;

import com.academia.fit.dto.memberworkout.MemberWorkoutRequest;
import com.academia.fit.dto.memberworkout.MemberWorkoutResponse;
import com.academia.fit.mapper.member.MemberWorkoutMapper;
import com.academia.fit.model.member.Member;
import com.academia.fit.model.member.MemberWorkout;
import com.academia.fit.service.member.MemberService;
import com.academia.fit.service.member.MemberWorkoutService;
import com.academia.fit.utils.validation.member.MemberWorkoutValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST responsável por gerenciar a associação entre Membro e Treino.
 * Não depende diretamente de WorkoutService.
 */
@RestController
@RequestMapping("/api/member-workouts")
public class MemberWorkoutController {

    private final MemberWorkoutService memberWorkoutService;
    private final MemberService memberService;
    private final MemberWorkoutMapper mapper;
    private final MemberWorkoutValidator validator;

    public MemberWorkoutController(MemberWorkoutService memberWorkoutService,
                                   MemberService memberService,
                                   MemberWorkoutMapper mapper,
                                   MemberWorkoutValidator validator) {
        this.memberWorkoutService = memberWorkoutService;
        this.memberService = memberService;
        this.mapper = mapper;
        this.validator = validator;
    }

    // ---------------------- CREATE ----------------------

    @PostMapping
    public ResponseEntity<MemberWorkoutResponse> create(@Valid @RequestBody MemberWorkoutRequest request) {

        // Passa apenas os IDs para o Service
        MemberWorkout mw = memberWorkoutService.createMemberWorkout(
                request.getMemberId(),
                request.getWorkoutId()
        );

        // Converte para DTO de resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(mw));
    }


    // ---------------------- READ ----------------------

    @GetMapping("/{id}")
    public ResponseEntity<MemberWorkoutResponse> findById(@PathVariable Long id) {
        MemberWorkout mw = memberWorkoutService.findByIdOrThrow(id); // esse método precisa existir no service
        return ResponseEntity.ok(mapper.toResponse(mw));
    }

    @GetMapping
    public ResponseEntity<List<MemberWorkoutResponse>> findAll() {
        List<MemberWorkoutResponse> responseList = memberWorkoutService.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<MemberWorkoutResponse>> findByMember(@PathVariable Long memberId) {
        Member member = memberService.findByIdEntity(memberId); // pega a entidade diretamente
        List<MemberWorkoutResponse> list = memberWorkoutService.findByMember(member)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // ---------------------- DELETE ----------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberWorkoutService.deleteByIdOrThrow(id);
        return ResponseEntity.noContent().build();
    }
}
