package com.academia.fit.utils.enums;

import com.academia.fit.exception.member.InvalidStatusException;
import lombok.Getter;

@Getter
public enum Status {

    ATIVO("ativo"),
    INATIVO("inativo");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    /**
     * Converte uma String para o enum Status.
     * Retorna o enum correspondente ou lança InvalidStatusException se inválido.
     */
    public static Status fromString(String status) {
        for (Status s : Status.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new InvalidStatusException();
    }
}
