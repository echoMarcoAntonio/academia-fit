package com.academia.fit.repository.exercise;

import com.academia.fit.model.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório de dados para a entidade Exercise (Exercício),
 * responsável por fornecer operações CRUD e consultas personalizadas.
 */
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    /**
     * Verifica se já existe um exercício com o nome fornecido.
     */
    boolean existsByName(String name);

    /**
     * Verifica se existe outro exercício com o mesmo nome, desconsiderando o ID atual.
     */
    boolean existsByNameAndIdNot(String name, Long id);
}
