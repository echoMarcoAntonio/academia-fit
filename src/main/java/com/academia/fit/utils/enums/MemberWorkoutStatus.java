package com.academia.fit.utils.enums;

import lombok.Getter;

/**
 * Define os possíveis status de um treino atribuído a um aluno (tabela treino_do_aluno).
 * Os valores são mapeados para as strings permitidas pela CHECK constraint do banco de dados.
 */
@Getter
public enum MemberWorkoutStatus {

    ATIVO("ativo"),
    CONCLUIDO("concluído"),
    PAUSADO("pausado");

    private final String dbValue;

    MemberWorkoutStatus(String dbValue) {
        this.dbValue = dbValue;
    }
}