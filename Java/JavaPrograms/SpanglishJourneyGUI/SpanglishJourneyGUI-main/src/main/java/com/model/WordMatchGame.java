package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/**
 * Creates a Word Match game where the user must match the English word with the
 * word in their language.
 * 
 * @author Christian Biermann
 */
public class WordMatchGame extends Question {

    private double timer;
    private String answer;
    private String question;
    private HashMap<String, String> correctPairs;
    private double score;
    private int totalCount;
    private int incorrectCount;

    /**
     * Intializes a new Word Match game with a timer and a Hashmap of the correct
     * answer pairs.
     * 
     * @param timer        The timer for the question.
     * @param correctPairs The correct pair of the English word and it's counterpart
     *                     in the users language.
     */
    public WordMatchGame(double timer, String question, HashMap<String, String> correctPairs) {
        this.timer = timer;
        this.question = question;
        this.score = 0;
        this.totalCount = 0;
        this.incorrectCount = 0;
        this.correctPairs = correctPairs;
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
        this.answer = answer;
    }

    /**
     * Retrieves the answer for the question
     */
    public String getCorrectAnswer() {
        return this.answer;
    }

    /**
     * Retrieves the users score for the question.
     * 
     * @return The users score
     */
    public double getScore() {
        return this.score;
    }

    /**
     * Retrieves the Hashmap containing the correct word pairs.
     * 
     * @return The Hashmap containing the correct word pairs.
     */
    public HashMap<String, String> getCorrectPairs() {
        return this.correctPairs;
    }

    /**
     * Compares the keys and values of the usersPairs and correctPairs Hashmaps to
     * determine if they're all equal. This is an overloaded method.
     * 
     * @return True or False
     */
    public boolean isCorrect(HashMap<String, String> userPairs) {
        for (HashMap.Entry<String, String> uEntry : userPairs.entrySet()) {
            String uKey = uEntry.getKey();
            String uValue = uEntry.getValue();
            for (HashMap.Entry<String, String> sEntry : this.correctPairs.entrySet()) {
                String sKey = sEntry.getKey();
                String sValue = sEntry.getValue();

                if (!uKey.equalsIgnoreCase(sKey) && !uValue.equalsIgnoreCase(sValue)) {
                    incorrectCount++;
                }
                totalCount++;
            }
        }

        score += ((double) incorrectCount / totalCount);

        if (getScore() == 4.0) {
            return true;
        }
        return false;
    }

    /**
     * Prints out the words in English and the users language for them to match.
     */
    public String toString() {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        for (HashMap.Entry<String, String> uEntry : this.correctPairs.entrySet()) {
            keys.add(uEntry.getKey());
            values.add(uEntry.getValue());
        }
        Collections.shuffle(keys);
        Collections.shuffle(values);
        return "Match the Spanish Word to their English Definitions\n" + "Spanish Words: " + keys
                + "\nEnglish Words: " + values;
    }

    /**
     * Gets the question
     * 
     * @return A string that is the question
     */
    @Override
    public String getQuestion() {
        return this.question;
    }

    /**
     * Gets the answer choices for the question
     * 
     * @return An ArrayList of the correct pair's hashmap values
     */
    @Override
    public ArrayList<String> getAnswers() {
        return (ArrayList<String>) correctPairs.values();
    }

    /**
     * Here for the abstract class implementation
     * Checks if the inputted answer is the correct answer
     * 
     * @return true
     */
    @Override
    public boolean isCorrect(String answer) {
        return true;
    }
}
