package com.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Creates a DataLoader
 * 
 * @author Matthew Johnson
 */
public class DataLoader extends DataConstants {

    /**
     * Gets the users from JSON
     * 
     * @return user converted to an ArrayList
     */
    public ArrayList<RegisteredUser> getUsers() {
        ArrayList<RegisteredUser> users = new ArrayList<RegisteredUser>();

        try {
            BufferedReader reader = getReaderFromFile(USER_FILE_NAME, USER_FILE_NAME_JUNIT);
            JSONArray peopleJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < peopleJSON.size(); i++) {
                ArrayList<String> friends = new ArrayList<String>();
                HashMap<String, Double> userProgress = new HashMap<>();
                JSONObject personJSON = (JSONObject) peopleJSON.get(i);
                String userName = (String) personJSON.get("Username");
                String firstName = (String) personJSON.get("FirstName");
                String lastName = (String) personJSON.get("LastName");
                String email = (String) personJSON.get("Email");
                String password = (String) personJSON.get("Password");
                long userScore = (long) personJSON.get("UserScore");

                double courseProgress = 0.0;
                String courseLanguage = "";

                // Iterate friendsJsonArray and get all friends into friends ArrayList
                JSONArray friendJsonArray = (JSONArray) personJSON.get("Friends");
                for (int j = 0; j < friendJsonArray.size(); j++) {
                    friends.add((String) friendJsonArray.get(j));
                }

                JSONArray settingsJsonArray = (JSONArray) personJSON.get("Setting");
                JSONObject settingJsonObject = (JSONObject) settingsJsonArray.get(0);

                boolean darkmode = (boolean) settingJsonObject.get("Darkmode");
                boolean TTSenabled = (boolean) settingJsonObject.get("Text_to_Speech");
                long fontSize = (long) settingJsonObject.get("Font_Size");
                boolean notificationEnabled = (boolean) settingJsonObject.get("Notification");
                boolean timerEnabled = (boolean) settingJsonObject.get("Lesson Timer");

                JSONArray coursesJSONArray = (JSONArray) personJSON.get("Courses");
                ArrayList<Course> courses = new ArrayList<>();

                for (int j = 0; j < coursesJSONArray.size(); j++) {
                    JSONObject courseJSONObject = (JSONObject) coursesJSONArray.get(j);
                    courseProgress = (double) courseJSONObject.get("Progress");
                    courseLanguage = (String) courseJSONObject.get("Language");

                    JSONArray modulesJSONArray = (JSONArray) courseJSONObject.get("Modules");
                    ArrayList<Module> modules = getUserCourseModuleInfo(modulesJSONArray);

                    courses.add(new Course(courseLanguage, modules));
                }

                Setting settings = new Setting(darkmode, TTSenabled, (int) fontSize, notificationEnabled, timerEnabled);
                users.add(new RegisteredUser(firstName, lastName, userName, password, email, (int) userScore, friends,
                        settings, courses));
            }
            reader.close();
            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Module> getUserCourseModuleInfo(JSONArray modulesJSONArray) {
        ArrayList<Module> modules = new ArrayList<>();
        for (int i = 0; i < modulesJSONArray.size(); i++) {
            JSONObject modulesJSONObject = (JSONObject) modulesJSONArray.get(i);
            String moduleName = (String) modulesJSONObject.get("Name");
            Double moduleScore = (Double) modulesJSONObject.get("Score");
            Double moduleProgress = (Double) modulesJSONObject.get("Progress");
            JSONArray bookmarkedLessonsJSONArray = (JSONArray) modulesJSONObject.get("BookmarkedLessons");
            JSONArray correctQuestionsJSONArray = (JSONArray) modulesJSONObject.get("CorrectQuestions");
            JSONArray missedQuestionsJSONArray = (JSONArray) modulesJSONObject.get("MissedQuestions");

            ArrayList<String> bookmarkedLessons = new ArrayList<>();
            for (int l = 0; l < bookmarkedLessonsJSONArray.size(); l++) {
                bookmarkedLessons.add((String) bookmarkedLessonsJSONArray.get(i));
            }

            HashMap<String, ArrayList<String>> correctQuestions = new HashMap<>();
            HashMap<String, ArrayList<String>> missedQuestions = new HashMap<>();

            iterateMap(correctQuestions, correctQuestionsJSONArray);
            iterateMap(missedQuestions, missedQuestionsJSONArray);

            ArrayList<Lesson> moduleLessons = new ArrayList<>();
            for (int j = 0; j < getCourses().size(); j++) {
                moduleLessons = getCourses().get(j).getModule(moduleName).getLessons();
                Module module = new Module(moduleName, moduleLessons, bookmarkedLessons, correctQuestions,
                        missedQuestions, moduleScore, moduleProgress);
                modules.add(module);
            }
        }
        return modules;
    }

    /**
     * First For Loop - Users
     * Second For Loop - Courses
     * Tie Course information i.e. modules whatnot with Name in User and Name in
     * course
     * 
     * @return ArrayList of courses
     */
    public ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        try {
            BufferedReader reader = getReaderFromFile(COURSE_FILE_NAME, COURSE_FILE_NAME_JUNIT);
            JSONArray coursesJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < coursesJSON.size(); i++) {
                JSONObject courseJSON = (JSONObject) coursesJSON.get(i);
                String language = (String) courseJSON.get("Name");
                JSONArray modulesJSONArray = (JSONArray) courseJSON.get("Modules");
                ArrayList<Module> modules = new ArrayList<>();

                for (int j = 0; j < modulesJSONArray.size(); j++) {
                    String moduleName = "";
                    JSONObject modulesObj = (JSONObject) modulesJSONArray.get(j);
                    moduleName = (String) modulesObj.get("Name");
                    JSONArray moduleLessons = (JSONArray) modulesObj.get("Lessons");

                    Module module = new Module(moduleName);

                    ArrayList<Lesson> lessons = getCourseModulesLessons(moduleLessons);
                    module.addLessons(lessons);
                    modules.add(module);
                }
                Course course = new Course(language, modules);
                courses.add(course);
            }

            reader.close();
            return courses;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<Lesson> getCourseModulesLessons(JSONArray moduleLessonJSON) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        ArrayList<Word> words = getWords();
        for (int i = 0; i < moduleLessonJSON.size(); i++) {
            JSONObject lessonObj = (JSONObject) moduleLessonJSON.get(i);
            String lessonName = (String) lessonObj.get("Name");
            ArrayList<Word> lessonWords = new ArrayList<>();
            JSONArray questions = (JSONArray) lessonObj.get("Questions");
            JSONArray wordPool = (JSONArray) lessonObj.get("WordPool");

            lessonWords = getWordList(words, wordPool, lessonWords);

            HashMap<String, ArrayList<Question>> lessonQuestions = getLessonQuestions(questions);
            Lesson lesson = new Lesson(lessonName, lessonQuestions, lessonWords);

            lessons.add(lesson);
        }
        return lessons;
    }

    private HashMap<String, ArrayList<Question>> getLessonQuestions(JSONArray questions) {
        HashMap<String, ArrayList<Question>> lessonQuestions = new HashMap<>();
        ArrayList<String> spanishWords = new ArrayList<String>();
        ArrayList<String> englishWords = new ArrayList<String>();
        ArrayList<Question> questionArray = new ArrayList<>();
        for (int j = 0; j < questions.size(); j++) {
            ArrayList<String> lessonAnswers = new ArrayList<>();
            HashMap<ArrayList<String>, ArrayList<String>> wordMatchPairs = new HashMap<>();
            HashMap<String, String> wordMatchCorrectPairs = new HashMap<>();
            JSONObject questionsObject = (JSONObject) questions.get(j);
            String type = (String) questionsObject.get("Type");
            Double timer = (Double) questionsObject.get("Timer");
            String question = (String) questionsObject.get("Question");
            JSONArray answers = null;

            getLessonAnswers(type, questionsObject, answers, lessonAnswers, spanishWords, englishWords,
                    wordMatchPairs);

            String correctAnswer = "";
            if (!(type.compareTo("WordMatchGame") == 0)) {
                // if not wordmatchgame any correctAnswer is the same
                correctAnswer = (String) questionsObject.get("CorrectAnswer");
            } else {
                // else it needs to be a hashmap of key and value of what the correct pair is
                JSONObject answersCorrectAnswer = (JSONObject) questionsObject.get("CorrectAnswers");
                for (Object key : answersCorrectAnswer.keySet()) {
                    wordMatchCorrectPairs.put((String) key, (String) answersCorrectAnswer.get(key));
                }
            }
            lessonQuestions = addToQuestionArray(type, timer, question, lessonAnswers, correctAnswer,
                    wordMatchCorrectPairs, questionArray, lessonQuestions);
        }
        return lessonQuestions;
    }

    /**
     * Add question to HashMap of all lessonQuestions
     * 
     * @param type                  Question type in string
     * @param timer                 Double time
     * @param question              String question to ask
     * @param lessonAnswers         Answers to the question if not WordMatchGame
     * @param correctAnswer         correct answer if not WordMatchGame
     * @param wordMatchCorrectPairs CorrectAnswers if WordMatchGame
     * @param questionArray         ArrayList of Question type
     * @param lessonQuestions       Hashmap to return
     * @return lessonQuestions with new hashmap info
     */
    private HashMap<String, ArrayList<Question>> addToQuestionArray(String type, Double timer, String question,
            ArrayList<String> lessonAnswers, String correctAnswer, HashMap<String, String> wordMatchCorrectPairs,
            ArrayList<Question> questionArray, HashMap<String, ArrayList<Question>> lessonQuestions) {
        Question q = null;
        // Switch checks type then create instance of type. Then add it to the
        // questionArray at key: type, value: Question
        switch (type) {
            case "MultipleChoice":
                q = new MultipleChoice(timer, question, lessonAnswers, correctAnswer);
                questionArray.add((MultipleChoice) q);
                break;
            case "Fill in The Blank":
                q = new FillinTheBlank(timer, question, lessonAnswers, correctAnswer);
                questionArray.add((FillinTheBlank) q);
                break;
            case "Sentence Builder":
                q = new SentenceBuilder(timer, question, lessonAnswers, correctAnswer);
                questionArray.add((SentenceBuilder) q);
                break;
            case "WordMatchGame":
                q = new WordMatchGame(timer, question, wordMatchCorrectPairs);
                questionArray.add((WordMatchGame) q);
                break;
            default:
                break;
        }
        lessonQuestions.computeIfAbsent(type, k -> new ArrayList<>()).add(q);
        return lessonQuestions;
    }

    /**
     * getWords gets the words from JSON and returns it as an ArrayList of Word
     * 
     * @return Word ArrayList
     */
    public ArrayList<Word> getWords() {
        ArrayList<Word> words = new ArrayList<Word>();
        try {
            BufferedReader reader = getReaderFromFile(WORDMASTERY_FILE_NAME, WORDMASTERY_FILE_NAME_JUNIT);
            JSONObject lessonJSON = (JSONObject) new JSONParser().parse(reader);
            JSONArray lessonJsonArray = (JSONArray) lessonJSON.get("words");
            for (int i = 0; i < lessonJsonArray.size(); i++) {
                words.add(getWord((JSONObject) lessonJsonArray.get(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }

    /**
     * gets the leaderboard
     * 
     * @return HashMap<Integer, HashMap<String, Integer>> type
     */
    public HashMap<Integer, HashMap<String, Integer>> getLeaderBoard() {
        HashMap<Integer, HashMap<String, Integer>> users = new HashMap<>();
        try {
            BufferedReader reader = getReaderFromFile(LEADERBOARD_FILE_NAME, LEADERBOARD_FILE_NAME_JUNIT);
            JSONArray lbJSON = (JSONArray) new JSONParser().parse(reader);
            for (int i = 0; i < lbJSON.size(); i++) {
                long rank = 0;
                long userScore = 0;
                String userName = "";
                JSONObject lbJsonObject = (JSONObject) lbJSON.get(i);
                JSONArray lbJSONArray = (JSONArray) lbJsonObject.get("LeaderBoard");
                if (lbJSONArray != null) {
                    // Loop through each entry in "LeaderBoard"
                    for (int j = 0; j < lbJSONArray.size(); j++) {
                        JSONObject userObjects = (JSONObject) lbJSONArray.get(j);
                        
                        // Extracting the rank, user score, and username
                        rank = (long) userObjects.get("Rank");
                        userScore = (long) userObjects.get("UserScore");
                        userName = (String) userObjects.get("Name");
                        
                        // If the rank is not yet in the map, add it with an empty user map
                        if (!users.containsKey((int) rank)) {
                            users.put((int) rank, new HashMap<>());
                        }
                        
                        // Add the user and their score to the map for that rank
                        users.get((int) rank).put(userName, (int) userScore);
                    }
                }
            }
            reader.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Private helper functions
    /**
     * 
     * @param wordJsonObject the object to get the word data from
     * @return a new Word object
     */
    private Word getWord(JSONObject wordJsonObject) {
        String word = (String) wordJsonObject.get("word");
        String wordInOtherLanguage = (String) wordJsonObject.get("wordInOtherLanguage");
        String definition = (String) wordJsonObject.get("definition");
        String pronunciation = (String) wordJsonObject.get("pronunciation");
        return new Word(word, wordInOtherLanguage, definition, pronunciation);
    }

    /**
     * 
     * @param map       The hashmap to add to
     * @param jsonArray What its being taken from
     */
    private void iterateMap(HashMap<String, ArrayList<String>> map, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject lesson_questions = (JSONObject) jsonArray.get(i);
            String lessonName = (String) lesson_questions.keySet().toArray()[0];
            ArrayList<String> questions = (ArrayList<String>) lesson_questions.get(lessonName);
            map.put(lessonName, questions);
        }
    }

    /**
     * 
     * @param words       arrayList of words
     * @param wordPool    the wordPool taken from JSON
     * @param lessonWords arrayList of lessonWords
     * @return ArrayList lessonWords
     */
    private ArrayList<Word> getWordList(ArrayList<Word> words, JSONArray wordPool, ArrayList<Word> lessonWords) {
        // For each word in wordpool check if the word matches. if so then it is added
        // to lessonWords
        for (int j = 0; j < words.size(); j++) {
            for (int k = 0; k < wordPool.size(); k++) {
                String word = (String) wordPool.get(k);
                if (words.get(j).getWord().equals(word)) {
                    lessonWords.add(words.get(j));
                }
            }
        }
        return lessonWords;
    }

    /**
     * get answers for the lesson
     * 
     * @param type            string
     * @param questionsObject object of question
     * @param answers         JSONArray of answers
     * @param lessonAnswers   An arrayList of lessonAnswers
     * @param spanishWords    ArrayList of string that contains spanish words
     * @param englishWords    ArrayList of string that contains english words
     * @param wordMatchPairs  contains both englishWords and spanishWords arrayList
     */
    private void getLessonAnswers(String type, JSONObject questionsObject, JSONArray answers,
            ArrayList<String> lessonAnswers, ArrayList<String> spanishWords, ArrayList<String> englishWords,
            HashMap<ArrayList<String>, ArrayList<String>> wordMatchPairs) {
        // Check if type is not wordMatch game then if it is not then add whatever is in
        // the JSONArray as its universal for all other lessons
        if (!(type.compareTo("WordMatchGame") == 0)) {
            answers = (JSONArray) questionsObject.get("Answers");
            for (int k = 0; k < answers.size(); k++) {
                lessonAnswers.add((String) answers.get(k));
            }
        } else {
            // if type is word match game get each key and the value then put it into the
            // ArrayLists and then into the hashmap.
            JSONObject answersWordPairs = (JSONObject) questionsObject.get("WordPairs");
            for (Object key : answersWordPairs.keySet()) {
                JSONArray answer = (JSONArray) answersWordPairs.get(key);
                String language = (String) key;
                for (int k = 0; k < answer.size(); k++) {
                    if (language.equalsIgnoreCase("English")) {
                        englishWords.add((String) answer.get(k));
                    } else {
                        spanishWords.add((String) answer.get(k));
                    }
                }
            }
            wordMatchPairs.put(spanishWords, englishWords);
        }
    }

    private static BufferedReader getReaderFromFile(String fileName, String jsonFileName) {
        try {
            if (isJUnitTest()) {
                InputStream inputStream = DataLoader.class.getResourceAsStream(jsonFileName);
                InputStreamReader InputStreamReader = new InputStreamReader(inputStream);
                return new BufferedReader(InputStreamReader);
            } else {
                FileReader reader = new FileReader(fileName);
                return new BufferedReader(reader);
            }
        } catch (Exception e) {
            System.out.println("Can't load");
            return null;
        }
    }
}
