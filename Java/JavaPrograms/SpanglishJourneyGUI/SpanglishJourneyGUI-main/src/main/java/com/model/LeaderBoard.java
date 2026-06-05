package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderBoard {
    private static LeaderBoard leaderBoard;
    private HashMap<Integer, HashMap<String, Integer>> allUsers;
    private HashMap<Integer, HashMap<String, Integer>> filteredUsers;
    private String filterValue;

    /**
     * Constructor that initializes the leaderboard by loading user data.
     */
    public LeaderBoard() {
        DataLoader dataLoader = new DataLoader();
        this.allUsers = dataLoader.getLeaderBoard();
        this.filteredUsers = new HashMap<>();
        this.filterValue = "global";
        sortAndAssignRanks(allUsers);
    }

    /**
     * Singleton pattern to get the single instance of the LeaderBoard.
     */
    public static LeaderBoard getInstance() {
        if (leaderBoard == null) {
            leaderBoard = new LeaderBoard();
        }
        return leaderBoard;
    }

    /**
     * Adds a new user to the leaderboard if they don't already exist.
     *
     * @param username  the user's username.
     * @param userScore the user's score.
     */
    public void addUser(String username, int userScore) {
        for (HashMap.Entry<Integer, HashMap<String, Integer>> rankEntry : allUsers.entrySet()) {
            if (!rankEntry.getValue().containsKey(username)) {
                rankEntry.getValue().put(username, userScore);
                break;
            }
        }
        sortAndAssignRanks(allUsers);
    }

    /**
     * Updates the score of a user and refreshes the leaderboard.
     *
     * @param username the user's username.
     * @param newScore the user's new score.
     */
    public void updateUserScore(String username, int newScore) {
        for (Map.Entry<Integer, HashMap<String, Integer>> rankEntry : allUsers.entrySet()) {
            HashMap<String, Integer> usersInRank = rankEntry.getValue();
            if (usersInRank.containsKey(username)) {
                usersInRank.put(username, newScore);
                sortAndAssignRanks(allUsers);
                break;
            }
        }
    }

    /**
     * Filters the leaderboard based on the filter value.
     *
     * @param filterValue the filter criteria being either global or friends.
     * @param friendsList the list of friends' usernames.
     */
    public void filterLeaderboard(String filterValue, ArrayList<String> friendsList) {
        this.filterValue = filterValue;

        if (filterValue.equals("friends")) {
            // Filter for friends
            filteredUsers.clear();
            int rank = 1;

            for (Map.Entry<Integer, HashMap<String, Integer>> entry : allUsers.entrySet()) {
                for (Map.Entry<String, Integer> user : entry.getValue().entrySet()) {
                    if (friendsList.contains(user.getKey())) {
                        HashMap<String, Integer> userMap = new HashMap<>();
                        userMap.put(user.getKey(), user.getValue());
                        filteredUsers.put(rank++, userMap);
                    }
                }
            }
        }
    }

    /**
     * Retrieves the current leaderboard (global or friends).
     *
     * @return a HashMap of all users and their ranks.
     */
    public HashMap<Integer, HashMap<String, Integer>> retrieveAllUsers() {
        return filterValue.equals("friends") ? filteredUsers : allUsers;
    }

    /**
     * Retrieves the sorted list of users based on their scores in descending order.
     *
     * @return a sorted list of users (name and score).
     */
    public ArrayList<Map.Entry<String, Integer>> retrieveSortedUsers() {
        ArrayList<Map.Entry<String, Integer>> usersList = new ArrayList<>();

        HashMap<Integer, HashMap<String, Integer>> currentUsers = filterValue.equals("friends") ? filteredUsers
                : allUsers;

        for (Map.Entry<Integer, HashMap<String, Integer>> rankEntry : currentUsers.entrySet()) {
            for (Map.Entry<String, Integer> userEntry : rankEntry.getValue().entrySet()) {
                usersList.add(userEntry);
            }
        }

        // Sort the list of users by their score, in descending order
        usersList.sort((user1, user2) -> {
            if (user1.getValue() == 0)
                return 1;
            if (user2.getValue() == 0)
                return -1;
            return user2.getValue().compareTo(user1.getValue());
        });

        return usersList;
    }

    /**
     * Prints a specified number of top users from the leaderboard.
     *
     * @param amount the number of top users to print.
     */
    public ArrayList<Map.Entry<String, Integer>> getLeaderboardAmount(int amount) {
        ArrayList<Map.Entry<String, Integer>> limitedUsers = new ArrayList<>();
        ArrayList<Map.Entry<String, Integer>> sortedUsers = retrieveSortedUsers();
        int count = 0;

        for (Map.Entry<String, Integer> user : sortedUsers) {
            if (count >= amount)
                break;
            limitedUsers.add(user);
            count++;
        }
        return limitedUsers;
    }

    /**
     * Sorts the users by their scores in descending order and reassigns ranks.
     */
    private void sortAndAssignRanks(HashMap<Integer, HashMap<String, Integer>> users) {
        ArrayList<Map.Entry<String, Integer>> userList = new ArrayList<>();
        for (HashMap<String, Integer> rankMap : users.values()) {
            userList.addAll(rankMap.entrySet());
        }

        userList.sort((user1, user2) -> user2.getValue().compareTo(user1.getValue()));

        users.clear();
        int rank = 1;
        for (Map.Entry<String, Integer> user : userList) {
            HashMap<String, Integer> userMap = new HashMap<>();
            userMap.put(user.getKey(), user.getValue());
            users.put(rank++, userMap);
        }
    }

    /**
     * Saves the leaderboard data.
     */
    public void save() {
        DataWriter dw = new DataWriter();
        dw.saveLeaderBoard();
    }
}
