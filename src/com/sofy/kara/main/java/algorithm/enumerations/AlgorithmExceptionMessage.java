package com.sofy.kara.main.java.algorithm.enumerations;

/**
 * Сообщения исключений алгоритма
 * Creation date: 13.06.2019
 *
 * @author karavanova-sa
 */
public enum AlgorithmExceptionMessage {
    EMPTY_TRAINING_SET("Обучающее множество не должно быть пустым."),
    INVALID_SCOPE("Недопустимая область определения."),
    INVALID_NUMBER_OF_ITERATION("Обучающее множество не должно быть пустым."),
    SWARM_SIZE_IS_TOO_SMALL("Обучающее множество не должно быть пустым."),
    INVALID_DENSITY_OF_SCOUTS("Обучающее множество не должно быть пустым.");

    private String message;

    AlgorithmExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}