package com.academia.fit.service.member;

import com.academia.fit.dto.member.MemberRequest;
import com.academia.fit.dto.member.MemberResponse;
import com.academia.fit.exception.ExceptionMessages;
import com.academia.fit.exception.base.BaseException;
import com.academia.fit.exception.member.MemberNotFoundException;
import com.academia.fit.mapper.member.MemberMapper;
import com.academia.fit.model.member.Member;
import com.academia.fit.repository.member.MemberRepository;
import com.academia.fit.utils.validation.member.MemberValidator;
import com.academia.fit.utils.validation.member.MemberCpfValidator;
import com.academia.fit.utils.validation.member.MemberEmailValidator;
import com.academia.fit.utils.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Camada de serviço responsável por aplicar regras de negócio
 * e orquestrar as operações CRUD da entidade Member (Aluno/Membro).
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository repository;
    private final MemberMapper mapper;

    /**
     * Exceção personalizada para status inválido.
     */
    public static class InvalidStatusException extends BaseException {
        public InvalidStatusException(String status) {
            super(String.format("Status inválido: %s", status), HttpStatus.BAD_REQUEST);
        }
    }

    // ---------------------- CREATE ----------------------

    /**
     * Cria um novo membro a partir do DTO.
     * Aplica validações de CPF e email antes de persistir.
     *
     * @param request DTO com dados do membro
     * @return DTO de resposta com dados do membro criado
     */
    @Transactional
    public MemberResponse createMember(MemberRequest request) {
        // Valida CPF duplicado
        MemberCpfValidator.validateCpfDuplication(repository, null, request.getCpf());
        // Valida email duplicado
        MemberEmailValidator.validateEmailDuplication(repository, null, request.getEmail());

        // Converte DTO para entidade
        Member member = mapper.toEntity(request);

        // Salva no banco
        member = repository.save(member);

        // Converte entidade para DTO de resposta
        return mapper.toResponse(member);
    }

    // ---------------------- READ ----------------------

    /**
     * Busca um membro pelo ID e retorna a entidade.
     *
     * @param id ID do membro
     * @return entidade Member
     * @throws MemberNotFoundException se não encontrado
     */
    @Transactional(readOnly = true)
    public Member findByIdEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(
                        String.format(ExceptionMessages.MEMBER_NOT_FOUND, id)
                ));
    }

    /**
     * Busca um membro pelo ID e retorna DTO de resposta.
     *
     * @param id ID do membro
     * @return DTO MemberResponse
     */
    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        return mapper.toResponse(findByIdEntity(id));
    }

    /**
     * Retorna todos os membros filtrados pelo status.
     *
     * @param status status como String
     * @return lista de DTOs de membros
     * @throws InvalidStatusException se o status for inválido
     */
    @Transactional(readOnly = true)
    public List<MemberResponse> findByMembershipStatusResponse(String status) {
        // Converte para Status ou lança InvalidStatusException automaticamente
        Status statusEnum;
        try {
            statusEnum = Status.fromString(status);
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException(status);
        }

        // Busca membros filtrando pelo status
        return repository.findByMembershipStatus(statusEnum)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Retorna todos os membros paginados.
     *
     * @param pageable objeto Pageable para paginação
     * @return página de DTOs de membros
     */
    @Transactional(readOnly = true)
    public Page<MemberResponse> findPaginatedResponse(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    /**
     * Retorna todos os membros como lista de DTOs.
     *
     * @return lista de DTOs de membros
     */
    @Transactional(readOnly = true)
    public List<MemberResponse> findAllResponse() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ---------------------- UPDATE ----------------------

    /**
     * Atualiza um membro existente com dados do DTO.
     * Aplica validações de CPF, email, data de nascimento e data de registro.
     *
     * @param id      ID do membro a ser atualizado
     * @param request DTO com dados atualizados
     * @return DTO de resposta com dados atualizados
     */
    @Transactional
    public MemberResponse updateMember(Long id, MemberRequest request) {
        // Busca entidade existente
        Member member = findByIdEntity(id);

        // Valida alterações
        MemberCpfValidator.validateCpfChange(member, request);
        MemberEmailValidator.validateEmailDuplication(repository, id, request.getEmail());
        MemberValidator.validateBirthDateChange(member, request);
        MemberValidator.validateRegistrationDateChange(member);

        // Atualiza entidade com dados do DTO
        mapper.updateEntityFromRequest(request, member);

        // Salva no banco
        member = repository.save(member);

        // Retorna DTO atualizado
        return mapper.toResponse(member);
    }

    // ---------------------- DELETE ----------------------

    /**
     * Remove um membro pelo ID.
     *
     * @param id ID do membro a ser removido
     * @throws MemberNotFoundException se não encontrado
     */
    @Transactional
    public void delete(Long id) {
        // Verifica se o membro existe
        findByIdEntity(id);
        // Remove do banco
        repository.deleteById(id);
    }
}
