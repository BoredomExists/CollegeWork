package com.model;

import java.util.ArrayList;

/**
 * A class that creates a Multiple Choice question with a specified number of
 * choices, a timer, and answer that can be checked
 * 
 * @author Christian Lowery
 */
public class MultipleChoice extends Question {
    private Double timer;
    private String correctAnswer;
    private String question;
    private ArrayList<String> answers;

    /**
     * Intializes a Multiple Choice question with specified number of choices.
     * 
     * @param choiceNum Number of choices.
     */
    public MultipleChoice(double timer, String question, ArrayList<String> answers, String correctAnswer) {
        this.timer = timer;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
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
     * Sets the answer for the question
     * 
     * @param answer The answer being set.
     */
    public void setAnswer(String answer) {
        this.correctAnswer = answer;
    }

    /**
     * Retrieves the answer for the question
     */
    public String getCorrectAnswer() {
        return this.correctAnswer;
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
     * Retrieves the String of a question.
     * 
     * @return Returns the question
     */
    public String getQuestion() {
        return this.question;
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
     * Compares an answer to the correct answer.
     * 
     * @param answer The answer being compared to the correct answer
     */
    public boolean isCorrect(String answer) {
        return answer != null && answer.equals(getCorrectAnswer());
    }

    /**
     * Prints the question and its choices.
     */
    public String toString() {
        String result = getQuestion() + "\n";
        for (int i = 0; i < answers.size(); i++) {
            result += (i + 1) + ": " + answers.get(i) + "\n";
        }
        return result;
    }
}
