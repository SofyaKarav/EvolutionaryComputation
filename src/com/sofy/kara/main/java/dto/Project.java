package com.sofy.kara.main.java.dto;

/**
 * Класс для мапинга таблицы с информацией по проектам
 * Creation date 13.06.2019
 *
 * @author karavanova-sa
 */
public class Project {

    private Integer projectNumber;

    private Double codeLength;

    private Double difficultRating;

    public Project(Integer projectNumber, Double codeLength, Double difficultRating) {
        this.projectNumber = projectNumber;
        this.codeLength = codeLength;
        this.difficultRating = difficultRating;
    }

    public Project() {
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Double getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(Double codeLength) {
        this.codeLength = codeLength;
    }

    public Double getDifficultRating() {
        return difficultRating;
    }

    public void setDifficultRating(Double difficultRating) {
        this.difficultRating = difficultRating;
    }
}