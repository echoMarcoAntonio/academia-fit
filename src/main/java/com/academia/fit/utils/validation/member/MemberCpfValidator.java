package com.academia.fit.utils.validation.member;

import com.academia.fit.dto.member.MemberRequest;
import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.member.CpfChangeNotAllowedException;
import com.academia.fit.exception.member.DuplicateMemberException;
import com.academia.fit.model.member.Member;
import com.academia.fit.repository.member.MemberRepository;
import lombok.experimental.UtilityClass;

/**
 * Validações relacionadas ao CPF da entidade Member.
 */
@UtilityClass
public class MemberCpfValidator {

    /**
     * Valida se o CPF do membro está sendo alterado.
     *
     * @param existingMember Membro existente no banco
     * @param request DTO com os dados atualizados
     * @throws CpfChangeNotAllowedException se o CPF for alterado
     */
    public static void validateCpfChange(Member existingMember, MemberRequest request) {
        if (existingMember != null && !existingMember.getCpf().equals(request.getCpf())) {
            throw new CpfChangeNotAllowedException(ExceptionMessages.CPF_CHANGE_NOT_ALLOWED);
        }
    }

    /**
     * Valida duplicidade de CPF no banco.
     *
     * @param repository Repositório de membros
     * @param memberId ID do membro (null se for criação)
     * @param cpf CPF a validar
     * @throws DuplicateMemberException se o CPF já existir
     */
    public static void validateCpfDuplication(MemberRepository repository, Long memberId, String cpf) {
        boolean exists = (memberId == null)
                ? repository.existsByCpf(cpf)                // criação: verifica se CPF já existe
                : repository.existsByCpfAndIdNot(cpf, memberId); // atualização: ignora próprio ID

        if (exists) {
            throw new DuplicateMemberException(ExceptionMessages.DUPLICATE_CPF);
        }
    }
}
