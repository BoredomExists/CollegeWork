package com.model;

import java.util.ArrayList;

/**
 * Creates a new Registered User
 * 
 * @author Christian Biermann
 */
public class RegisteredUser {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private int userScore;
    private ArrayList<String> friends;
    private Setting setting;
    private ArrayList<Course> courses;

    /**
     * Paramertized Constructor for the registered user
     * 
     * @param firstName The first name of the user
     * @param lastName  The last name of the user
     * @param username  The username of the user
     * @param password  The password of the user
     * @param email     The email of the user
     */
    public RegisteredUser(String firstName, String lastName,
            String username, String password,
            String email) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);

        this.setting = new Setting();
        this.friends = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    /**
     * Overloaded Constructor for the RegisteredUser
     * 
     * @param firstName    The first name of the user
     * @param lastName     The last name of the user
     * @param userName     The user name of the user
     * @param password     The password of the user
     * @param email        The email of the user
     * @param userScore    The user's score for the leaderboard
     * @param friends      The friends of the user
     * @param userProgress The user's progress within a module
     * @param setting      The setting's of the user
     */
    public RegisteredUser(String firstName, String lastName,
            String userName, String password,
            String email, int userScore, ArrayList<String> friends, Setting setting, ArrayList<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userScore = userScore;
        this.friends = friends;
        this.setting = setting;
        this.courses = courses;
    }

    /**
     * Sets the first name of the registerd user
     * 
     * @param firstName The first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    /**
     * Gets the first name of the registered user
     * 
     * @return A string of the user's first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the last name of the registered user
     * 
     * @param lastName The last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    /**
     * Gets the last name of the registered user
     * 
     * @return A string of the user's last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the username of the registered user
     * 
     * @param userName The username of the user
     */
    public void setUsername(String userName) {
        this.userName = userName.trim();
    }

    /**
     * Gets the username of the registered user
     * 
     * @return A string of the user's username
     */
    public String getUsername() {
        return this.userName;
    }

    /**
     * Sets the password of the registered user
     * 
     * @param password The password of the registered user
     */
    public void setPassword(String password) {
        this.password = password.trim();
    }

    /**
     * Gets the password the of registered user
     * 
     * @return A string of the user's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the email of the registered user
     * 
     * @param email The email of the user
     */
    public void setEmail(String email) {
        this.email = email.trim();
    }

    /**
     * Gets the email of the registered user
     * 
     * @return A string of the user's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the registered user's score of completing modules
     * 
     * @return The user's score
     */
    public int getUserScore() {
        return this.userScore;
    }

    /**
     * Adds a registered user to the arraylist of friends
     * 
     * @param user A registered user's username
     */
    public void addFriend(String userName) {
        friends.add(userName);
    }

    /**
     * Gets the registered user's friends
     * 
     * @return An arraylist of the user's friends
     */
    public ArrayList<String> getFriendsList() {
        return friends;
    }

    /**
     * Sets the settings of the user with new settings
     * 
     * @param setting A new set of settings
     */
    public void setSettings(Setting setting) {
        this.setting = setting;
    }

    /**
     * Gets the settings of the registered user
     * 
     * @return The settings object of the registered user
     */
    public Setting getSettings() {
        return this.setting;
    }

    /**
     * Adds a course to the list of courses for the user
     * 
     * @param course A newly created course
     */
    public void addCourse(Course course) {
        if (course != null) {
            courses.add(course);
        }
    }

    /**
     * Gets the courses that the user has
     * 
     * @return An ArrayList of courses
     */
    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    public void updateUserScore() {
        for (int i = 0; i < courses.size(); i++) {
            ArrayList<Module> modules = courses.get(i).getModules();
            for (int j = 0; j < modules.size(); j++) {
                userScore += modules.get(i).getScore();
            }
        }
    }
}