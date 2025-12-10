package com.academia.fit.dto.member;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * DTO de requisição para criar ou atualizar a entidade Member (Aluno/Membro).
 * Contém todos os dados que podem ser alterados pelo usuário.
 */
@Getter
@Setter
public class MemberRequest {

    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(min = 5, max = 150, message = "O nome deve ter entre 5 e 150 caracteres.")
    private String name;

    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(regexp = "^[0-9\\-()\\s]+$", message = "Formato de telefone inválido.")
    @Size(max = 20, message = "O telefone não pode exceder 20 caracteres.")
    private String phone;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O formato do email é inválido.")
    @Size(max = 100, message = "O email não pode exceder 100 caracteres.")
    private String email;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve ter 11 dígitos.")
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória.")
    private LocalDate birthDate;

    @NotBlank(message = "O status da associação é obrigatório.")
    private String membershipStatus;
}
