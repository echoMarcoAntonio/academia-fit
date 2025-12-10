package com.academia.fit.utils.enums;

import lombok.Getter;

/**
 * Enum que define o principal grupo muscular trabalhado em um exercício.
 */
@Getter
public enum MuscleGroup {
    CHEST("Peitoral"),
    BACK("Costas"),
    SHOULDERS("Ombros"),
    ARMS_BICEPS("Braços - Bíceps"),
    ARMS_TRICEPS("Braços - Tríceps"),
    ABS_CORE("Abdômen e Core"),
    CALVES("Panturrilhas");

    private final String description;

    MuscleGroup(String description) {
        this.description = description;
    }

}
