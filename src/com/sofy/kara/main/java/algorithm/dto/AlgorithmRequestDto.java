package com.sofy.kara.main.java.algorithm.dto;

import com.sofy.kara.main.java.algorithm.enumerations.AlgorithmExceptionMessage;
import com.sofy.kara.main.java.algorithm.enumerations.BoundaryParameters;
import com.sofy.kara.main.java.algorithm.exceptions.AlgorithmException;
import com.sofy.kara.main.java.dto.Project;

import java.util.List;

/**
 * Вспомогательный класс для представления
 * объекта параметровб представляемых на вход алгоритму
 * Creation date: 13.06.2019
 *
 * @author karavanova-sa
 */
public class AlgorithmRequestDto {

    private int populationSize;

    private int numberOfIteration;

    private int numberOfScouts;

    private Double max;

    private Double min;

    private List<Project> list;

    public AlgorithmRequestDto(int populationSize, int numberOfIteration, int numberOfScouts, Double max, Double min, List<Project> list)
            throws AlgorithmException {
        this.populationSize = populationSize;
        this.numberOfIteration = numberOfIteration;
        this.numberOfScouts = numberOfScouts;
        this.max = max;
        this.min = min;
        this.list = list;
        checkParams();
    }

    private void checkParams() throws AlgorithmException {
        if (list == null || list.isEmpty()) {
            throw new AlgorithmException(AlgorithmExceptionMessage.EMPTY_TRAINING_SET.getMessage());
        }
        if (max <= min) {
            throw new AlgorithmException(AlgorithmExceptionMessage.INVALID_SCOPE.getMessage());
        }
        if (numberOfIteration <= BoundaryParameters.MINIMUM_NUMBER_OF_ITERATIONS.getValue()) {
            throw new AlgorithmException(AlgorithmExceptionMessage.INVALID_NUMBER_OF_ITERATION.getMessage());
        }
        if (populationSize <= BoundaryParameters.MINIMUM_POPULATION_SIZE.getValue()) {
            throw new AlgorithmException(AlgorithmExceptionMessage.SWARM_SIZE_IS_TOO_SMALL.getMessage());
        }
        if (numberOfScouts >= populationSize / BoundaryParameters.POPULATION_DIVISOR.getValue()) {
            throw new AlgorithmException(AlgorithmExceptionMessage.INVALID_DENSITY_OF_SCOUTS.getMessage());
        }
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getNumberOfIteration() {
        return numberOfIteration;
    }

    public void setNumberOfIteration(int numberOfIteration) {
        this.numberOfIteration = numberOfIteration;
    }

    public int getNumberOfScouts() {
        return numberOfScouts;
    }

    public void setNumberOfScouts(int numberOfScouts) {
        this.numberOfScouts = numberOfScouts;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public List<Project> getList() {
        return list;
    }

    public void setList(List<Project> list) {
        this.list = list;
    }
}