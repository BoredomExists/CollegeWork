package com.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The System Facade that controls a large portion of the objects within the
 * project
 * 
 * @author Christian Biermann
 */

public class LanguageSystemFacade {
    private UserList userList;
    private CourseList courseList;
    private RegisteredUser user;
    private Course course;
    private Module module;
    private Lesson lesson;
    private WordList wordList;
    private LeaderBoard leaderBoard;
    private static LanguageSystemFacade lsf;

    /**
     * Constructor for LanguageSystemFacade.
     * Initializes instances of UserList, CourseList, WordList, and Leaderboard.
     */
    public LanguageSystemFacade() {
        userList = UserList.getInstance();
        courseList = CourseList.getInstance();
        wordList = WordList.getInstance();
        leaderBoard = LeaderBoard.getInstance();
    }

    public static LanguageSystemFacade getInstance() {
        if (lsf == null) {
            lsf = new LanguageSystemFacade();
        }
        return lsf;
    }

    /**
     * Creates a new user account if the username does not already exist.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param username  The desired username.
     * @param email     The user's email address.
     * @return The created RegisteredUser if successful, null otherwise.
     */
    public RegisteredUser createAccount(String firstName, String lastName, String username, String password,
            String email) {
        if (checkNullAndEmptyUserInfo(firstName, lastName, username, password, email)
                || userList.contains(username.trim())) {
            return null;
        }

        RegisteredUser rUser = new RegisteredUser(firstName.trim(), lastName.trim(), username.trim(), password.trim(),
                email.trim());
        userList.addUser(rUser);
        leaderBoard.addUser(rUser.getUsername(), 0);
        return rUser;
    }

    /**
     * Gets the UserList
     * 
     * @return The UserList singleton
     */
    public UserList getUserList() {
        return this.userList;
    }

    /**
     * Sets the user in the Facade to the currently logged in user
     * 
     * @param user The logged in Registered User
     */
    public void setCurrentUser(RegisteredUser user) {
        this.user = user;
    }

    /**
     * Gets the current user
     * 
     * @return The registered user
     */
    public RegisteredUser getCurrentUser() {
        return this.user;
    }

    /**
     * Sets the current course based on the user
     * 
     * @param course The course that is tied to the logged in user
     */
    public void setCurrentCourse(Course course) {
        this.course = course;
    }

    /**
     * Gets the current course
     * 
     * @return A course object of the current course
     */
    public Course getCurrentCourse() {
        return this.course;
    }

    /**
     * Sets the current module based on the course
     * 
     * @param module The module that is tied to the course
     */
    public void setCurrentModule(Module module) {
        this.module = module;
    }

    /**
     * Gets the current module
     * 
     * @return A module object of the current module
     */
    public Module getCurrentModule() {
        return this.module;
    }

    /**
     * Sets the current lesson based on the module
     * 
     * @param lesson The lesson that is tied to the current module
     */
    public void setCurrentLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    /**
     * Gets the current lesson
     * 
     * @return A lesson object of the current lesson
     */
    public Lesson getCurrentLesson() {
        return this.lesson;
    }

    /**
     * Logs in a user with the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password for the user.
     * @return The RegisteredUser if login is successful, null otherwise.
     */
    public RegisteredUser login(String username, String password) {
        this.user = userList.haveUser(username, password);
        return userList.haveUser(username, password);
    }

    /**
     * Resets the user's password if the current password matches.
     *
     * @param password The current password.
     * @param in       Scanner object for input.
     * @return The new password if reset is successful, an empty string otherwise.
     */
    public void resetPassword(String currentPassword, String newPassword) {
        if (currentPassword.equals(user.getPassword())) { // Correctly compares passwords
            user.setPassword(newPassword);
        }
    }

    /**
     * Adds a new course for the specified language if the language is valid.
     *
     * @param language The language of the new course.
     * @return true if the course is added successfully, false otherwise.
     */
    public boolean addCourses(String language) {
        if (language != null && !language.isEmpty()) { // Corrects logical condition
            courseList.getCourses()
                    .add(new Course(language, course.getModules()));
            return true;
        }
        return false;
    }

    /**
     * Retrieves the course list.
     *
     * @return The CourseList associated with the facade.
     */
    public CourseList getCourseList() {
        return this.courseList;
    }

    /**
     * Gets the list of friends for the currently logged-in user.
     *
     * @return An ArrayList of RegisteredUser representing the user's friends.
     */
    public ArrayList<String> getFriends() {
        return user.getFriendsList();
    }

    /**
     * Adds a friend to the currently logged-in user's friend list.
     *
     * @param friend The RegisteredUser to add as a friend.
     * @return true if the friend is added successfully, false otherwise.
     */
    public boolean addFriends(String friend) {
        if (friend == null || friend.equalsIgnoreCase("".trim())) {
            return false;
        }
        return user.getFriendsList().add(friend);
    }

    /**
     * Retrieves the bookmarked lessons for a specific module.
     *
     * @param moduleName The name of the module.
     * @return An ArrayList of Lesson objects that are bookmarked.
     */
    public ArrayList<String> getBookmarkedLessons(String moduleName) {
        return course.getModule(moduleName).getBookmarkedLessons();
    }

    /**
     * Gets the module's correct questions
     * 
     * @param module The current module
     * @return An ArrayList of the correct questions
     */

    public HashMap<String, ArrayList<String>> getModuleCorrectQuestions(Module module) {
        return module.getCorrectQuestions();
    }

    /**
     * Gets the missed questions in the module
     * 
     * @param module The current module
     * @return An ArrayList of the missed questions
     */

    public HashMap<String, ArrayList<String>> getModuleMissedQuestions(Module module) {
        return module.getMissedQuestions();
    }

    /**
     * Retrieves the settings for the currently logged-in user.
     *
     * @return The user's settings.
     */
    public Setting getSetting() {
        return user.getSettings();
    }

    /**
     * Edits the settings for the currently logged-in user.
     *
     * @param setting The new settings to apply.
     */
    public void editSetting(Setting setting) {
        user.setSettings(setting);
    }

    /**
     * Retrieves the leaderboard that is a singleton for the system
     *
     * @return The system's leaderboard.
     */
    public LeaderBoard getLeaderBoard() {
        return this.leaderBoard;
    }

    /**
     * Retrieves a specific module by its name from the course.
     *
     * @param moduleName The name of the module.
     * @return The Module if found, otherwise null.
     */
    public Module getModule(String moduleName) {
        return course.getModule(moduleName);
    }

    /**
     * Gets all the modules within the current course
     * 
     * @return An ArrayList of all the modules
     */
    public ArrayList<Module> getModules() {
        return course.getModules();
    }

    /**
     * Gets the word pool that is in the lesson
     * 
     * @param moduleName The name of the current module
     * @param lessonName The name of the lesson
     * @return An ArrayList of the words
     */
    public ArrayList<Word> getLessonWordList(String moduleName, String lessonName) {
        return course.getModule(moduleName).getLesson(lessonName).getWordPool();
    }

    /**
     * Gets the WordList singleton
     * 
     * @return The WordList object
     */
    public WordList getWordList() {
        return this.wordList;
    }

    /**
     * Shuffles the answers for questions like sentence builder
     * 
     * @param answers An ArrayList of the question answers
     */
    public void shuffleAnswers(ArrayList<String> answers) {
        Collections.shuffle(answers);
    }

    /**
     * Creates a list fo flashcards of the lesson's word pool
     * 
     * @param lesson The current lesson
     * @return An ArrayList of flashcards
     */
    public ArrayList<Flashcard> getLessonFlashcards(Lesson lesson) {
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        for (int i = 0; i < lesson.getWordPool().size(); i++) {
            flashcards.add(new Flashcard(lesson.getWordPool().get(i).getWord(),
                    lesson.getWordPool().get(i).getDefinition()));
        }
        return flashcards;
    }

    /**
     * Gets a specific flashcard from a list of flashcards
     * 
     * @param flashcards An ArrayList of flashcards
     * @param word       The target word to look for
     * @return A flashcard of the target word
     */
    public Flashcard getSpecificFlashcard(ArrayList<Flashcard> flashcards, String word) {
        for (int i = 0; i < flashcards.size(); i++) {
            if (flashcards.get(i).getWord().equalsIgnoreCase(word)) {
                return flashcards.get(i);
            }
        }
        return null;
    }

    /**
     * Gets the questions within a Lesson based on their type
     * 
     * @param lesson The current lesson
     * @param type   The type of question
     * @return An ArrayList of the question based on type
     */
    public ArrayList<Question> getQuestions(Lesson lesson, String type) {
        return new ArrayList<>(lesson.getQuestions().get(type));
    }

    /**
     * Adds a correct question to the module's correct questions list
     * 
     * @param lesson   The current lesson
     * @param question The question to add
     */

    public void addCorrectQuestion(Module module, String lessonName, ArrayList<String> questions) {
        module.addCorrectQuestion(lessonName, questions);
        for (int i = 0; i < questions.size(); i++) {
            module.getLesson(lessonName).addCompletedQuestion(questions.get(i));
        }
    }

    /**
     * Adds a missed question to the module's missed questions list
     * 
     * @param lesson   The current question
     * @param question The question to add
     */

    public void addMissedQuestion(Module module, String lessonName, ArrayList<String> questions) {
        module.addMissedQuestion(lessonName, questions);
        for (int i = 0; i < questions.size(); i++) {
            module.getLesson(lessonName).addCompletedQuestion(questions.get(i));
        }
    }

    /**
     * Prints all the modules within the current course
     */
    public void printCourseModules() {
        ArrayList<Module> courseModules = course.getModules();
        for (int i = 0; i < courseModules.size(); i++) {
            System.out.println(courseModules.get(i).getName() + ": " + courseModules.get(i).getProgress());
        }
    }

    /**
     * Prints all the lessons within a module
     * 
     * @param moduleName The name of the currentModule
     */
    public void printModuleLessons(String moduleName) {
        ArrayList<Lesson> modulesLessons = course.getModule(moduleName).getLessons();
        for (int i = 0; i < modulesLessons.size(); i++) {
            System.out.println(modulesLessons.get(i).getLessonName() + ": " + modulesLessons.get(i).getProgress());
        }
    }

    /**
     * Prints the questions types and makes use of each question's toString method
     * 
     * @param questions An ArrayList of questions
     * @return The string of the questions toString method
     */
    public String printQuestions(ArrayList<Question> questions) {
        String result = "";
        for (int i = 0; i < questions.size(); i++) {
            result += questions.get(i) + "\n";
        }
        return result;
    }

    /**
     * Updates the module's score and progress and the lesson's progress
     * 
     * @param module The current module
     * @param lesson The current Lesson
     */
    public void updateProgressAndScore(Module module, Lesson lesson) {
        lesson.updateProgress();
        module.updateProgress();
        module.updateScore();
        course.updateCourseProgress();
        user.updateUserScore();
        leaderBoard.updateUserScore(user.getUsername(), user.getUserScore());
    }

    /**
     * Saves the users individually
     */
    public void saveUsers() {
        userList.save();
    }

    /**
     * Logs out the currently logged-in user and saves all data.
     */
    public void logout() {
        userList.save();
        leaderBoard.save();
    }

    private boolean checkNullAndEmptyUserInfo(String firstName, String lastName, String username, String password,
            String email) {
        return firstName == null || firstName.trim().equalsIgnoreCase("")
                || lastName == null || lastName.trim().equalsIgnoreCase("")
                || username == null || username.trim().equalsIgnoreCase("")
                || password == null || password.trim().equalsIgnoreCase("")
                || email == null || email.trim().equalsIgnoreCase("");
    }
}
