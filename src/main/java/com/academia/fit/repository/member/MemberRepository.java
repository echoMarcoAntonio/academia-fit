package com.academia.fit.repository.member;

import com.academia.fit.model.member.Member;
import com.academia.fit.utils.enums.Status;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório de dados para a entidade Member (Aluno),
 * responsável por fornecer operações CRUD e consultas personalizadas.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByCpf(String cpf);

    boolean existsByCpfAndIdNot(String cpf, Long id);

    List<Member> findByMembershipStatus(@NotNull Status membershipStatus);
}
