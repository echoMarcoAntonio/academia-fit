package com.academia.fit.controller.member;

import com.academia.fit.dto.member.MemberRequest;
import com.academia.fit.dto.member.MemberResponse;
import com.academia.fit.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class MemberController {

    private final MemberService service;

    // ---------------------- CREATE ----------------------
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberRequest request) {
        MemberResponse created = service.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ---------------------- READ (LIST ALL PAGINATED) ----------------------
    @GetMapping
    public ResponseEntity<Page<MemberResponse>> getAllMembers(Pageable pageable) {
        return ResponseEntity.ok(service.findPaginatedResponse(pageable));
    }


    // ---------------------- READ (BY ID) ----------------------
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // ---------------------- UPDATE ----------------------
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequest request
    ) {
        MemberResponse updated = service.updateMember(id, request);
        return ResponseEntity.ok(updated);
    }

    // ---------------------- DELETE ----------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------------- CUSTOM QUERY ----------------------
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MemberResponse>> getMembersByStatus(@PathVariable String status) {
        List<MemberResponse> members = service.findByMembershipStatusResponse(status);
        return ResponseEntity.ok(members);
    }
}
