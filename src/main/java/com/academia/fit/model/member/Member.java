package com.academia.fit.model.member;

import com.academia.fit.exception.member.memberworkout.DuplicateMemberWorkoutException;
import com.academia.fit.utils.enums.Status;
import com.academia.fit.utils.validation.ValidationUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.academia.fit.exception.ExceptionMessages.DUPLICATE_MEMBER_WORKOUT;

/**
 * Entidade de domínio que representa o Aluno (Membro) da academia.
 * Mapeada para a tabela 'member'.
 * Inclui regras de negócio para gerenciar MemberWorkout de forma segura.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "cpf")
        }
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos numéricos.")
    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório.")
    @Size(min = 10, max = 20, message = "O telefone deve ter entre 10 e 20 dígitos.")
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser no passado.")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "membership_status", nullable = false, length = 20)
    private Status membershipStatus;

    @Builder.Default
    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDate registrationDate = LocalDate.now();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberWorkout> memberWorkouts = new ArrayList<>();

    /**
     * Inicializa a data de registro se não estiver setada.
     */
    @PrePersist
    private void prePersist() {
        if (registrationDate == null) {
            registrationDate = LocalDate.now();
        }
    }

    // Regras de negócio para MemberWorkout

    /**
     * Adiciona um treino ao membro.
     * Evita duplicidade do mesmo treino.
     *
     * @param memberWorkout treino a adicionar
     * @throws DuplicateMemberWorkoutException se o treino já existir
     */
    public void addMemberWorkout(MemberWorkout memberWorkout) {
        ValidationUtils.validateMemberWorkoutDuplicate(memberWorkouts, memberWorkout, DUPLICATE_MEMBER_WORKOUT);
        memberWorkout.setMember(this);
        memberWorkouts.add(memberWorkout);
    }

    /**
     * Remove um treino do membro.
     *
     * @param memberWorkout treino a remover
     */
    public void removeMemberWorkout(MemberWorkout memberWorkout) {
        memberWorkouts.remove(memberWorkout);
        memberWorkout.setMember(null);
    }
}
