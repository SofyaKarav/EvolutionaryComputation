package com.sofy.kara.main.java.algorithm.enumerations;

/**
 * Ограничивающие значения
 * Creation date: 13.06.2019
 *
 * @author karavanova-sa
 */
public enum BoundaryParameters {

    MINIMUM_NUMBER_OF_ITERATIONS(0),
    MINIMUM_POPULATION_SIZE(100),
    POPULATION_DIVISOR(4);

    private int value;

    BoundaryParameters(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}