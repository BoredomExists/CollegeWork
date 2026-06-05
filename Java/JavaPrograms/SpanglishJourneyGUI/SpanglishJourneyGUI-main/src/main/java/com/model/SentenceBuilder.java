package com.model;

import java.util.ArrayList;

/**
 * A class that creates a Sentence Builder question.
 * 
 * @author Christian Lowery
 */
public class SentenceBuilder extends Question {
    private Double timer;
    private String question;
    private String correctSentence;
    private ArrayList<String> answers;

    public SentenceBuilder(double timer, String question, ArrayList<String> answers, String correctSentence) {
        this.timer = timer;
        this.question = question;
        this.answers = answers;
        this.correctSentence = correctSentence;
    }

    /**
     * Sets the timer for the question.
     * 
     * @param timer Value for the timer.
     */
    public void setTimer(double timer) {
        this.timer = timer;
    }

    /**
     * Retrieves the timer for the question
     */
    public Double getTimer() {
        return this.timer;
    }

    /**
     * Sets the correct sentence for the question
     * 
     * @param answer The answer being set.
     */
    public void setAnswer(String answer) {
        this.correctSentence = answer;
    }

    /**
     * Retrieves the correct sentence for the question
     */
    public String getCorrectAnswer() {
        return this.correctSentence;
    }

    /**
     * Sets the question.
     * 
     * @param question The String of the question being set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Retrieves the String of a question.
     * 
     * @return Returns the question
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Gets the answers for the question
     * 
     * @return An ArrayList of the answers
     */
    public ArrayList<String> getAnswers() {
        return this.answers;
    }

    /**
     * Compares an answer to the correct answer.
     * 
     * @param answer The answer being compared to the correct answer
     */
    public boolean isCorrect(String answer) {
        return answer != null && answer.equals(getCorrectAnswer());
    }

    /**
     * Prints the question.
     */
    public String toString() {
        String result = getQuestion() + "\n";
        result += "Words: " + getAnswers();
        return result;
    }
}
