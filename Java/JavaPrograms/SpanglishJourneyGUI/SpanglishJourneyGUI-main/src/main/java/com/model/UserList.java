package com.model;

import java.util.ArrayList;

/**
 * Creates the list of users for the program
 * 
 * @author Christian Biermann
 */

public class UserList {
    private static UserList rUser;
    private ArrayList<RegisteredUser> rUsers;

    /**
     * Private Constructor that loads the users from DataLoader
     */
    private UserList() {
        DataLoader dataLoader = new DataLoader();
        rUsers = dataLoader.getUsers();
    }

    /**
     * Creates a single instance of the UserList class
     * 
     * @return The instance of the UserList class
     */
    public static UserList getInstance() {
        if (rUser == null) {
            rUser = new UserList();
        }
        return rUser;
    }

    /**
     * Checks to see if the program already has the user in the system
     * 
     * @param userName The registered user's username
     * @param password The registered user's password
     * @return The registered user that is in the system otherwise returns a null
     *         value
     */
    public RegisteredUser haveUser(String userName, String password) {
        for (RegisteredUser rUser : rUsers) {
            if (rUser.getUsername().equalsIgnoreCase(userName) && rUser.getPassword().equals(password)) {
                return rUser;
            }
        }
        return null;
    }

    /**
     * Adds a newly created registered user to the system
     * 
     * @param user The newly created registered user
     * @return true if the newly created user is valid otherwise false
     */
    public boolean addUser(RegisteredUser user) {
        if (user != null && !contains(user.getUsername())) {
            rUsers.add(user);
            return true;
        }
        return false;
    }

    /**
     * Chcks to see if the system already has the user by their username
     * 
     * @param userName The registered user's username
     * @return True if the system contains the username, otherwise false
     */
    public boolean contains(String userName) {
        for (RegisteredUser rUser : rUsers) {
            if (rUser.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the registered users ArrayList
     * 
     * @return The ArrayList of registered users
     */
    public ArrayList<RegisteredUser> getUsers() {
        return rUsers;
    }

    /**
     * Saves the userlist to the Data Writer
     */
    public void save() {
        DataWriter.saveUsers();
    }
}
