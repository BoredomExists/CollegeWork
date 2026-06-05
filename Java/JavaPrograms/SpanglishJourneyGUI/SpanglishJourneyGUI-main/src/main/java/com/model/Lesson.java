package com.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates a new Lesson for the Module
 * 
 * @author Christian Biermann
 */

public class Lesson {
    private String name;
    private HashMap<String, ArrayList<Question>> questions;
    private ArrayList<String> allQuestionsTypes;
    private ArrayList<String> completedQuestions;
    private ArrayList<Word> wordPool;
    private double progress;

    /**
     * Paramertized Constructor
     * 
     * @param name      The name of the lesson
     * @param questions The question object
     */
    public Lesson(String name, HashMap<String, ArrayList<Question>> questions, ArrayList<Word> wordPool) {
        this.name = name;
        this.questions = questions;
        this.allQuestionsTypes = setAllQuestions();
        this.completedQuestions = new ArrayList<>();
        this.wordPool = wordPool;
        this.progress = 0.0;
    }

    /**
     * Gets the question
     * 
     * @return The question object
     */
    public HashMap<String, ArrayList<Question>> getQuestions() {
        return this.questions;
    }

    /**
     * Sets the all questions ArrayList to have all the questions in each lesson
     * 
     * @return an ArrayList of strings that is all the questions
     */
    private ArrayList<String> setAllQuestions() {
        ArrayList<String> allQuestions = new ArrayList<>();
        for (HashMap.Entry<String, ArrayList<Question>> entry : questions.entrySet()) {
            allQuestions.add(entry.getKey());
        }
        return allQuestions;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> allQuestions = new ArrayList<>();
        for (HashMap.Entry<String, ArrayList<Question>> entry : questions.entrySet()) {
            allQuestions.addAll(entry.getValue());
        }
        return allQuestions;
    }

    /**
     * Gets all the questions in each lesson
     * 
     * @return An ArrayList of strings that is all the questions
     */
    public ArrayList<String> getAllQuestionsType() {
        return this.allQuestionsTypes;
    }

    /**
     * Gets the completed questions in a lesson
     * 
     * @return An ArrayList of strings that is the question
     */
    public ArrayList<String> getCompletedQuestions() {
        return this.completedQuestions;
    }

    /**
     * Adds a completed question to the list of completed questions
     * 
     * @param question The question
     */
    public void addCompletedQuestion(String question) {
        // Prevent adding duplicates
        if (!this.completedQuestions.contains(question)) {
            this.completedQuestions.add(question);
            updateProgress(); // Update score after adding
        }
    }

    /**
     * Gets the word pool of words that are used in the lesson
     * 
     * @return An ArrayList of words
     */
    public ArrayList<Word> getWordPool() {
        return this.wordPool;
    }

    /**
     * Gets the lesson name
     * 
     * @return A String that is the lesson's name
     */
    public String getLessonName() {
        return this.name;
    }

    /**
     * Gets the progress of the lesson
     * 
     * @return A double value of the progress
     */
    public double getProgress() {
        return this.progress;
    }

    // Only used for testing purposes
    public void setProgress(double progress) {
        this.progress = progress;
    }

    /**
     * Updates the progress of the lesson
     */
    public void updateProgress() {
        int lessonSize = getLessonSize();
        this.progress = (lessonSize > 0) ? (((double) completedQuestions.size()) / lessonSize) * 100 : 0.0;
    }

    /**
     * Gets the amount of questions in a lesson
     * 
     * @return An integer value of the size of the lesson
     */
    public int getLessonSize() {
        int amount = 0;
        for (ArrayList<Question> q : questions.values()) {
            amount += q.size();
        }
        return amount;
    }
}
