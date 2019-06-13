package com.sofy.kara.main.java.dto;

/**
 * Класс, описыващий точку в двумерном пространстве
 * Creation date 13.06.2019
 *
 * @author karavanova-sa
 */
public class Point {

    private Double x;

    private Double y;

    public Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x: " + getX() + " y: " + getY();
    }
}