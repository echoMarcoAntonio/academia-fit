package com.academia.fit.dto.member;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de resposta para apresentar os dados do Member (Aluno/Membro).
 * Inclui informações de registro e status.
 */
@Getter
@Setter
public class MemberResponse {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private LocalDate birthDate;
    private String membershipStatus;
    private LocalDate registrationDate;
}
