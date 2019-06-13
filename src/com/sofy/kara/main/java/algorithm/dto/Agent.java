package com.sofy.kara.main.java.algorithm.dto;

import com.sofy.kara.main.java.dto.Point;

/**
 * Класс, описывающий агента в роевом алгоритме (пчела)
 * Creation date: 13.06.2019
 *
 * @author karavanova-sa
 */
public class Agent {

    /**
     * Текущая позиция
     */
    private Point currentPosition;

    public Agent() {
        this.currentPosition = new Point(100, 2);
    }

    public Agent(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }
}
