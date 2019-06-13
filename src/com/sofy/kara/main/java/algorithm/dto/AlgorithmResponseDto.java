package com.sofy.kara.main.java.algorithm.dto;

import com.sofy.kara.main.java.dto.Point;

import java.util.List;

/**
 * Вспомогательный класс для представления
 * объекта результатов работы алгоритма
 * Creation date: 13.06.2019
 *
 * @author karavanova-sa
 */
public class AlgorithmResponseDto {
    /**
     * Наилучшая найденная точка
     */
    private Point point;

    /**
     * Итоговое значение фитнесс-функции
     */
    private Double fitnessValue;

    /**
     * Модельные точки
     */
    private List<Point> points;

    /**
     * Время выполнения
     */
    private Long duration;

    public AlgorithmResponseDto(Point point, List<Point> points, long duration, Double fitnessValue) {
        this.point = point;
        this.points = points;
        this.duration = duration;
        this.fitnessValue = fitnessValue;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Double getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(Double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }
}