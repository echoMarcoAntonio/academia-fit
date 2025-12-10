package com.academia.fit.utils.validation;

import com.academia.fit.exception.member.memberworkout.DuplicateMemberWorkoutException;
import com.academia.fit.model.member.MemberWorkout;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.BiConsumer;

/**
 * Classe utilitária com métodos de validação genéricos.
 */
public final class ValidationUtils {

    private ValidationUtils() {
    }

    /**
     * Valida duplicidade em qualquer coleção com predicate customizado.
     *
     * @param collection   coleção a ser verificada
     * @param predicate    condição para identificar duplicidade
     * @param errorMessage mensagem de erro caso duplicidade seja encontrada
     * @param <T>          tipo da coleção
     */
    public static <T> void checkDuplicate(Collection<T> collection, Predicate<T> predicate, String errorMessage) {
        if (collection.stream().anyMatch(predicate)) {
            throw new DuplicateMemberWorkoutException(errorMessage);
        }
    }

    /**
     * Valida duplicidade específica de MemberWorkout.
     * Evita adicionar um treino duplicado para o mesmo membro.
     *
     * @param existingMemberWorkouts coleção de MemberWorkouts existentes
     * @param newMemberWorkout       novo MemberWorkout a ser adicionado
     * @param errorMessage           mensagem de erro caso duplicidade seja encontrada
     */
    public static void validateMemberWorkoutDuplicate(Collection<MemberWorkout> existingMemberWorkouts,
                                                      MemberWorkout newMemberWorkout,
                                                      String errorMessage) {
        boolean duplicate = existingMemberWorkouts.stream()
                .anyMatch(mw -> Objects.equals(mw.getWorkout(), newMemberWorkout.getWorkout())
                        && Objects.equals(mw.getMember(), newMemberWorkout.getMember()));
        if (duplicate) {
            throw new DuplicateMemberWorkoutException(errorMessage);
        }
    }

    public static <P, C> void addBidirectional(
            Collection<C> collection,
            C child,
            BiConsumer<C, P> parentSetter,
            Predicate<C> duplicateCheck,
            String errorMessage,
            P parent
    ) {
        if (collection.stream().anyMatch(duplicateCheck)) {
            throw new DuplicateMemberWorkoutException(errorMessage);
        }
        collection.add(child);
        parentSetter.accept(child, parent);
    }

    public static <P, C> void removeBidirectional(
            Collection<C> collection,
            C child,
            BiConsumer<C, P> parentSetter
    ) {
        collection.remove(child);
        parentSetter.accept(child, null);
    }
}
