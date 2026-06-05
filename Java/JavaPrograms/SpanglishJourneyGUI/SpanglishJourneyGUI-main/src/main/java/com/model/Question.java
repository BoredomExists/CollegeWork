package com.model;

import java.util.ArrayList;

public abstract class Question {

    public abstract void setTimer(double timer);

    public abstract Double getTimer();

    public abstract String getQuestion();

    public abstract String getCorrectAnswer();

    public abstract ArrayList<String> getAnswers();

    public abstract boolean isCorrect(String answer);

    public abstract String toString();

}
