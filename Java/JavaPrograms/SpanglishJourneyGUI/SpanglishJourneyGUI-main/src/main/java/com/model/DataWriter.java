package com.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.net.URI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Creates a DataWriter
 * 
 * @author Matthew Johnson
 */

public class DataWriter extends DataConstants {

    /**
     * Save users and their respective user details into json.
     */
    @SuppressWarnings("unchecked")
    public static void saveUsers() {
        ArrayList<RegisteredUser> users = UserList.getInstance().getUsers();
        try {
            String path = getFileWritingPath(USER_FILE_NAME, USER_FILE_NAME_JUNIT);
            FileWriter writer = new FileWriter(path);
            JSONArray peopleJSON = new JSONArray();
            for (int i = 0; i < users.size(); i++) {
                RegisteredUser registeredUser = users.get(i);

                JSONObject details = saveUser(registeredUser);

                peopleJSON.add(details);

            }

            writer.write(peopleJSON.toJSONString());

            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the user
     * 
     * @param registeredUser given registered user to save
     * @return JSONObject of all user data.
     */
    @SuppressWarnings("unchecked")
    private static JSONObject saveUser(RegisteredUser registeredUser) {
        // Overall JSON object for everything to be contained within
        JSONObject details = new JSONObject();

        details.put("FirstName", registeredUser.getFirstName());
        details.put("LastName", registeredUser.getLastName());
        details.put("Username", registeredUser.getUsername());
        details.put("Password", registeredUser.getPassword());
        details.put("Email", registeredUser.getEmail());
        details.put("UserScore", registeredUser.getUserScore());

        JSONArray UserProgress = new JSONArray();
        JSONObject progress = new JSONObject();

        JSONArray friends = new JSONArray();
        ArrayList<String> friendsLs = registeredUser.getFriendsList();

        // Iterate over FriendLS and put into friends
        iterateStrings(friendsLs, friends);

        JSONArray setting = new JSONArray();
        JSONObject settingObject = new JSONObject();
        Setting userSettings = registeredUser.getSettings();

        settingObject.put("Darkmode", userSettings.getDarkMode());
        settingObject.put("Text_to_Speech", userSettings.getTextToSpeech());
        settingObject.put("Font_Size", userSettings.getFontSize());
        settingObject.put("Notification", userSettings.getNotifications());
        settingObject.put("Lesson Timer", userSettings.getLessonTimer());

        // Add objects to their respective JSONArray
        setting.add(settingObject);
        UserProgress.add(progress);

        // put JSON arrays into "details"
        details.put("Friends", friends);
        details.put("Setting", setting);

        JSONArray courses = new JSONArray();
        JSONObject courseObject = new JSONObject();
        ArrayList<Course> userCourses = registeredUser.getCourses();
        JSONArray moduleInfo = saveCourseInformation(courseObject, userCourses);

        courseObject.put("Modules", moduleInfo);
        courses.add(courseObject);
        details.put("Courses", courses);

        return details;
    }

    /**
     * Function to cut down on code and to iterate through courses and modules,
     * and gets their info to add to the course object for the Users JSON
     * 
     * @param courseObject JSONObject to add the course and module information to
     * @param userCourses  ArrayList<Course> that is the user courses to iterate
     *                     through
     * @return JSONArray of all modules and lessons
     */
    @SuppressWarnings("unchecked")
    private static JSONArray saveCourseInformation(JSONObject courseObject, ArrayList<Course> userCourses) {
        JSONArray moduleInfo = new JSONArray();

        for (int i = 0; i < userCourses.size(); i++) {
            courseObject.put("Language", userCourses.get(i).getLanguage());
            courseObject.put("Progress", userCourses.get(i).getProgress());

            ArrayList<Module> courseModules = userCourses.get(i).getModules();

            for (int j = 0; j < courseModules.size(); j++) {
                JSONObject moduleInfoObject = new JSONObject();
                moduleInfoObject.put("Name", courseModules.get(j).getName());
                moduleInfoObject.put("Score", courseModules.get(j).getScore());
                moduleInfoObject.put("Progress", courseModules.get(j).getProgress());
                moduleInfoObject.put("BookmarkedLessons", courseModules.get(j).getBookmarkedLessons());

                JSONArray missedQuestionsJsonArray = new JSONArray();
                for (HashMap.Entry<String, ArrayList<String>> entry : courseModules.get(j).getMissedQuestions()
                        .entrySet()) {
                    JSONObject missedQuestion = new JSONObject();
                    missedQuestion.put(entry.getKey(), entry.getValue());
                    missedQuestionsJsonArray.add(missedQuestion);
                }
                JSONArray correctQuestionsJsonArray = new JSONArray();
                for (HashMap.Entry<String, ArrayList<String>> entry : courseModules.get(j).getCorrectQuestions()
                        .entrySet()) {
                    JSONObject correctQuestion = new JSONObject();
                    correctQuestion.put(entry.getKey(), entry.getValue());
                    correctQuestionsJsonArray.add(correctQuestion);
                }

                moduleInfoObject.put("CorrectQuestions", correctQuestionsJsonArray);
                moduleInfoObject.put("MissedQuestions", missedQuestionsJsonArray);

                moduleInfo.add(moduleInfoObject);
            }
        }

        return moduleInfo;
    }

    @SuppressWarnings("unchecked")
    public void saveLeaderBoard() {
        LeaderBoard lb = LeaderBoard.getInstance();
        try {
            String path = getFileWritingPath(LEADERBOARD_FILE_NAME, LEADERBOARD_FILE_NAME_JUNIT);
            FileWriter writer = new FileWriter(path);

            JSONArray leaderboardDetails = new JSONArray();
            JSONObject leaderboardOBJ = new JSONObject();
            JSONArray leaderboardJSONArray = new JSONArray();

            HashMap<Integer, HashMap<String, Integer>> allUsers = lb.retrieveAllUsers();

            // Iterate through all ranks and users
            for (Map.Entry<Integer, HashMap<String, Integer>> rankEntry : allUsers.entrySet()) {
                int rank = rankEntry.getKey();
                HashMap<String, Integer> usersInRank = rankEntry.getValue();

                for (Map.Entry<String, Integer> userEntry : usersInRank.entrySet()) {
                    JSONObject userOBJ = new JSONObject();
                    userOBJ.put("Rank", rank);
                    userOBJ.put("Name", userEntry.getKey());
                    userOBJ.put("UserScore", userEntry.getValue());
                    leaderboardJSONArray.add(userOBJ);
                }
            }
            leaderboardOBJ.put("LeaderBoard", leaderboardJSONArray);
            leaderboardDetails.add(leaderboardOBJ);

            // Write the JSON to the file
            writer.write(leaderboardDetails.toJSONString());
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while saving the leaderboard.");
        }
    }

    /**
     * For any String object in the array. Add to the JSONArray
     * 
     * @param iteratingArray iterate through this array
     * @param array          add object from iterating array to
     */
    private static void iterateStrings(ArrayList<String> iteratingArray, JSONArray array) {
        for (String object : iteratingArray) {
            array.add(object);
        }
    }

    private static String getFileWritingPath(String PATH_NAME, String JUNIT_PATH_NAME) {
        try {
            if (isJUnitTest()) {
                URI url = DataWriter.class.getResource(JUNIT_PATH_NAME).toURI();
                return url.getPath();
            } else {
                return PATH_NAME;
            }
        } catch (Exception e) {
            System.out.println("Difficulty getting resource path");
            return "";
        }
    }
}