package com.model;

import java.util.ArrayList;

/**
 * Creates the CourseList singleton for the course
 */

public class CourseList {
    private static CourseList courseList;
    private ArrayList<Course> courses;

    /**
     * Private constructor to implement the Singleton pattern.
     * Initializes the course list by loading data from a data source.
     */
    private CourseList() {
        DataLoader dataLoader = new DataLoader();
        courses = dataLoader.getCourses(); // DataLoader fetches course data.
    }

    /**
     * Returns the single instance of CourseList. If it doesn't exist yet,
     * creates one. This method ensures only one instance of CourseList exists
     * (Singleton pattern).
     *
     * @return The single instance of CourseList.
     */
    public static CourseList getInstance() {
        if (courseList == null) {
            courseList = new CourseList();
        }
        return courseList;
    }

    /**
     * Checks whether a course for the given language exists in the course list.
     *
     * @param language The language of the course being checked.
     * @param username The username of the registered user
     * @return true if the course for the given language exists, false otherwise.
     */
    public boolean haveCourse(String language) {
        for (Course course : courses) {
            if (course.getLanguage().equals(language)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list of courses.
     *
     * @return An ArrayList of Course objects.
     */
    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    /**
     * Adds a new course to the course list
     * 
     * @param course A newly created course
     * @return True if the course was added otherwise false
     */
    public boolean addCourse(Course course) {
        for (int i = 0; i < getCourses().size(); i++) {
            if (checkValidCourse(getCourses().get(i), course)) {
                return false;
            }
        }
        courses.add(course);
        return true;
    }

    private boolean checkValidCourse(Course courseInCourseList, Course course) {
        return courseInCourseList.getLanguage().equalsIgnoreCase(course.getLanguage())
                || course.getLanguage() == null
                || course.getLanguage().equalsIgnoreCase("")
                || course.getLanguage() == null
                || course.getLanguage().equalsIgnoreCase("");
    }
}
