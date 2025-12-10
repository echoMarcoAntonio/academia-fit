package com.academia.fit.utils.enums;

import lombok.Getter;

/**
 * Define os níveis de dificuldade para um plano de treino.
 */
@Getter
public enum Level {

    INICIANTE("iniciante"),
    INTERMEDIARIO("intermediário"),
    AVANCADO("avançado");

    private final String dbValue;

    Level(String dbValue) {
        this.dbValue = dbValue;
    }
}