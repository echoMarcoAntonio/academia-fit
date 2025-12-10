package com.academia.fit.exception;

public final class ExceptionMessages {
    private ExceptionMessages() {}

    // MEMBER
    public static final String MEMBER_NOT_FOUND = "Aluno com ID %d não encontrado.";
    public static final String DUPLICATE_MEMBER = "Já existe um aluno com este email ou cpf";
    public static final String DUPLICATE_EMAIL = "Já existe um aluno com este e-mail.";
    public static final String DUPLICATE_CPF = "Já existe um aluno com este CPF.";
    public static final String CPF_CHANGE_NOT_ALLOWED = "Não é permitido alterar o CPF de um membro. Entre em contato com o suporte.";
    public static final String BIRTHDATE_CHANGE_NOT_ALLOWED = "Não é permitido alterar a data de nascimento de um membro. Entre em contato com o suporte.";
    public static final String INVALID_STATUS = "Status inválido informado para o membro.";


    // EXERCISE
    public static final String EXERCISE_NOT_FOUND = "Exercício não encontrado.";
    public static final String DUPLICATE_EXERCISE = "Já existe um exercício com este nome.";

    // WORKOUT
    public static final String WORKOUT_NOT_FOUND = "Treino com ID %d não encontrado.";
    public static final String DUPLICATE_WORKOUT = "Já existe um treino com este nome e duração.";
    public static final String INVALID_MUSCLE_GROUP = "Grupo muscular inválido: %s";

    // MEMBER_WORKOUT
    public static final String MEMBER_WORKOUT_NOT_FOUND = "Treino do aluno não encontrado ID: %d";
    public static final String DUPLICATE_MEMBER_WORKOUT = "O aluno já possui este treino atribuído.";
    public static final String MEMBER_WORKOUT_VALIDATION_ERROR = "Vínculo Membro/Treino não encontrado para o Membro %d e Treino %d";

    // WORKOUT_ITEM
    public static final String WORKOUT_ITEM_NOT_FOUND = "Item de treino não encontrado ID: %d";
    public static final String DUPLICATE_WORKOUT_ITEM = "Exercício já adicionado para este treino.";

    // GENÉRICO
    public static final String RESOURCE_NOT_FOUND = "Recurso não encontrado.";
    public static final String INTERNAL_ERROR = "Erro interno no servidor.";
    public static final String VALIDATION_ERROR = "Erro de validação nos campos enviados.";
}
