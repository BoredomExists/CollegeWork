package com.model;

public abstract class DataConstants {
    protected static final String USER_FILE_NAME = "src/main/java/data/Users.json";
    protected static final String USER_FILE_NAME_JUNIT = "/data/Users.json";

    protected static final String COURSE_FILE_NAME = "src/main/java/data/Courses.JSON";
    protected static final String COURSE_FILE_NAME_JUNIT = "/data/Courses.JSON";

    protected static final String LESSON_FILE_NAME = "src/main/java/data/Lesson.JSON";
    protected static final String LESSON_FILE_NAME_JUNIT = "/data/Lesson.JSON";

    protected static final String WORDMASTERY_FILE_NAME = "src/main/java/data/WordsMastery.Json";
    protected static final String WORDMASTERY_FILE_NAME_JUNIT = "/data/WordsMastery.Json";

    protected static final String LEADERBOARD_FILE_NAME = "src/main/java/data/Leaderboard.JSON";
    protected static final String LEADERBOARD_FILE_NAME_JUNIT = "/data/Leaderboard.JSON";

    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}
