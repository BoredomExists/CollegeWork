package com.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Module {
    // Fields
    private String name;
    private ArrayList<Lesson> lessons;
    private ArrayList<String> bookmarkedLessons;
    private HashMap<String, ArrayList<String>> correctQuestions;
    private HashMap<String, ArrayList<String>> missedQuestions;
    private double score;
    private double progress;

    /**
     * Paramertized Constructor
     * 
     * @param name The name of the module
     */
    public Module(String name) {
        this.name = name;
        this.lessons = new ArrayList<>();
        this.bookmarkedLessons = new ArrayList<>();
        this.correctQuestions = new HashMap<>();
        this.missedQuestions = new HashMap<>();
        this.score = 0;
        this.progress = 0.0;

    }

    /**
     * The overloaded constructor of the module
     * 
     * @param name              The name of the module
     * @param lessons           The list of lessons in the module
     * @param bookmarkedLessons The bookmarked lessons in the module
     * @param correctQuestions  The questions that was answered correctly in the
     *                          module
     * @param missedQuestions   The questions that was answered incorrectly in the
     *                          module
     * @param score             The score of the module
     * @param progress          The progress of the module
     */
    public Module(String name, ArrayList<Lesson> lessons, ArrayList<String> bookmarkedLessons,
            HashMap<String, ArrayList<String>> correctQuestions, HashMap<String, ArrayList<String>> missedQuestions,
            double score, double progress) {
        this.name = name;
        this.lessons = lessons;
        this.bookmarkedLessons = bookmarkedLessons;
        this.correctQuestions = correctQuestions;
        this.missedQuestions = missedQuestions;
        this.score = score;
        this.progress = progress;
    }

    /**
     * Sets the name of the module
     * 
     * @param name A string that is the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a lesson the list of lessons in the module
     * 
     * @param lesson The lesson object to add
     */
    public void addLessons(ArrayList<Lesson> lessons) {
        for (int i = 0; i < lessons.size(); i++) {
            this.lessons.add(lessons.get(i));
        }
        updateProgress();
    }

    /**
     * Gets a specific lesson in the module
     * 
     * @param name The name of the lesson
     * @return A lesson object or null if not in the list
     */
    public Lesson getLesson(String name) {
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonName().equalsIgnoreCase(name)) {
                return lessons.get(i);
            }
        }
        return null;
    }

    /**
     * Gets the list of lessons in the module
     * 
     * @return An ArrayList of lessons
     */
    public ArrayList<Lesson> getLessons() {
        return this.lessons;
    }

    /**
     * Adds a lesson to the bookmarked lessons list
     * 
     * @param lesson The name of the lesson
     */
    public void addBookmarkedLesson(String lesson) {
        if (!this.bookmarkedLessons.contains(lesson)) {
            this.bookmarkedLessons.add(lesson);
        }
    }

    /**
     * Gets the list of bookmarked lessons
     * 
     * @return An ArrayList of strings that is the name of the lessons
     */
    public ArrayList<String> getBookmarkedLessons() {
        return this.bookmarkedLessons;
    }

    /**
     * Gets the lesson name and its missed questions in the module
     * 
     * @return A Hashmap of the lesson name as key and an ArrayList of strings as
     *         value
     */
    public HashMap<String, ArrayList<String>> getMissedQuestions() {
        return this.missedQuestions;
    }

    /**
     * Adds a missed question to the Hashmap of missed questions
     * 
     * @param lessonName The name of the lesson that the question is in
     * @param questions  The ArrayList of questions that was missed
     */
    public void addMissedQuestion(String lessonName, ArrayList<String> questions) {
        this.missedQuestions.put(lessonName, questions);
    }

    /**
     * Gets the lesson name and its correct questions in the module
     * 
     * @return A Hashmap of the lesson name as key and an ArrayList of strings as
     *         value
     */
    public HashMap<String, ArrayList<String>> getCorrectQuestions() {
        return this.correctQuestions;
    }

    /**
     * Adds a correct question to the Hashmap of correct questions
     * 
     * @param lessonName The name of the lesson that the question is in
     * @param questions  The ArrayList of questions that was correct
     */
    public void addCorrectQuestion(String lessonName, ArrayList<String> questions) {
        this.correctQuestions.put(lessonName, questions);
    }

    /**
     * Gets the progress of the module
     * 
     * @return The double value that is the progress
     */
    public double getProgress() {
        return this.progress;
    }

    /**
     * Gets the score of the module
     * 
     * @return A double value that is the score
     */
    public double getScore() {
        return this.score;
    }

    /**
     * Gets the name of the module
     * 
     * @return A string that is the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method recalculates the progress of the module.
     * Progress calculation assumes progress will be based on the number
     * of lessons completed out of the total number of lessons.
     * <p>
     * If there are no lessons, progress is set to 0.
     */
    public void updateProgress() {
        double totalLessonProgress = 0.0;
        for (int i = 0; i < lessons.size(); i++) {
            totalLessonProgress += lessons.get(i).getProgress();
        }
        this.progress = (totalLessonProgress / lessons.size());
    }

    /**
     * Updates the score of the module based on the missed questions and the total
     * questions in the lesson
     */
    public void updateScore() {
        double totalMissedQuestions = 0.0;
        double totalQuestions = 0.0;

        for (Lesson lesson : lessons) {
            totalQuestions += lesson.getLessonSize();

            ArrayList<String> correctForLesson = correctQuestions.getOrDefault(lesson.getLessonName(),
                    new ArrayList<>());
            totalMissedQuestions += correctForLesson.size();
        }

        if (totalQuestions > 0) {
            this.score = (totalMissedQuestions / totalQuestions)  * 100.0;
        } else {
            this.score = 0.0;
        }
    }

    /**
     * Prints the lessons in the module
     */
    public void printLessons() {
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println(lessons.get(i).getLessonName());
        }
    }
}
