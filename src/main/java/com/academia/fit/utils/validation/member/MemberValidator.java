package com.academia.fit.utils.validation.member;

import com.academia.fit.dto.member.MemberRequest;
import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.member.BirthDateChangeNotAllowedException;
import com.academia.fit.exception.member.CpfChangeNotAllowedException;
import com.academia.fit.exception.member.DuplicateMemberException;
import com.academia.fit.model.member.Member;
import com.academia.fit.repository.member.MemberRepository;

/**
 * Classe utilitária responsável por validar regras de negócio
 * relacionadas à entidade Member (Aluno/Membro).
 */
public final class MemberValidator {

    private MemberValidator() {}

    /**
     * Valida se o CPF do membro está sendo alterado.
     */
    public static void validateCpfChange(Member existing, MemberRequest request) {
        if (!existing.getCpf().equals(request.getCpf())) {
            throw new CpfChangeNotAllowedException(ExceptionMessages.CPF_CHANGE_NOT_ALLOWED);
        }
    }

    /**
     * Valida se a data de nascimento do membro está sendo alterada.
     */
    public static void validateBirthDateChange(Member existing, MemberRequest request) {
        if (!existing.getBirthDate().equals(request.getBirthDate())) {
            throw new BirthDateChangeNotAllowedException(ExceptionMessages.BIRTHDATE_CHANGE_NOT_ALLOWED);
        }
    }

    /**
     * Garante que a data de registro não será alterada.
     * Este método é apenas ilustrativo, pois o campo é imutável e gerenciado pelo JPA.
     */
    public static void validateRegistrationDateChange(Member existing) {
        // Nenhuma ação necessária, campo protegido pelo JPA
    }

    /**
     * Valida duplicidade de email, ignorando o ID do membro atual.
     */
    public static void validateEmailDuplication(MemberRepository repository, Long memberId, String email) {
        if (repository.existsByEmailAndIdNot(email, memberId)) {
            throw new DuplicateMemberException(ExceptionMessages.DUPLICATE_EMAIL);
        }
    }
}
