package com.model;
import java.util.ArrayList;

/**
 * A class that creates a Fill in the Blank question with a broken sentence, the
 * correct sentence and the missing word as the answer.
 * 
 * @author Christian Lowery
 */
public class FillinTheBlank extends Question {
    private double timer;
    private String correctAnswer;
    private String question;
    ArrayList<String> answers;

    /**
     * Paramertized Constructor
     * 
     * @param timer         A double that is the timer on how long to a user has to
     *                      complete the question
     * @param question      A string of the question
     * @param answers       An ArrayList of all answer choices
     * @param correctAnswer A string of the correct answer
     */
    public FillinTheBlank(double timer, String question, ArrayList<String> answers, String correctAnswer) {
        this.timer = timer;
        this.correctAnswer = correctAnswer;
        this.question = question;
        this.answers = answers;
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
     * Retrieves the broken sentence string.
     * 
     * @return the broken sentence
     */
    public String getQuestion() {
        return this.question;
    }

    public ArrayList<String> getAnswers() {
        return this.answers;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    /**
     * Checks if the answers equals the correct answer.
     * 
     * @param answer the answer being compared to the correct answer
     */
    public boolean isCorrect(String answer) {
        return answer.equals(getCorrectAnswer());
    }

    /**
     * Prints the broken sentence for the user to finish
     */
    public String toString() {
        String result = getQuestion() + "\n";
        for (int i = 0; i < getAnswers().size(); i++) {
            result += (i + 1) + ": " + getAnswers().get(i) + "\n";
        }
        return result;
    }
}
