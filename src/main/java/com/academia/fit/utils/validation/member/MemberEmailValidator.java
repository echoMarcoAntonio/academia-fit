package com.academia.fit.utils.validation.member;

import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.member.DuplicateMemberException;
import com.academia.fit.repository.member.MemberRepository;
import lombok.experimental.UtilityClass;

/**
 * Validações relacionadas a Email da entidade Member.
 */
@UtilityClass
public class MemberEmailValidator {

    /**
     * Valida duplicidade de email.
     *
     * @param repository repositório de membros
     * @param memberId id do membro existente (null se for criação)
     * @param email email a validar
     * @throws DuplicateMemberException se o email já existir
     */
    public static void validateEmailDuplication(MemberRepository repository, Long memberId, String email) {
        boolean exists = (memberId == null)
                ? repository.existsByEmail(email)
                : repository.existsByEmailAndIdNot(email, memberId);

        if (exists) {
            throw new DuplicateMemberException(ExceptionMessages.DUPLICATE_EMAIL);
        }
    }
}
