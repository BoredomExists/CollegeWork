package com.spanglishjourney;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.model.Course;
import com.model.CourseList;
import com.model.DataLoader;
import com.model.DataWriter;
import com.model.LanguageSystemFacade;
import com.model.RegisteredUser;
import com.model.Setting;
import com.model.UserList;

// Tested By Christian Biermann

public class CourseListTest {
    private DataLoader dl = new DataLoader();
    private DataWriter dw = new DataWriter();
    private CourseList courseList = CourseList.getInstance();
    private LanguageSystemFacade lsf = new LanguageSystemFacade();
    private ArrayList<RegisteredUser> users = UserList.getInstance().getUsers();
    private ArrayList<Course> courses = courseList.getCourses();

    @BeforeEach
    public void setup() {
        ArrayList<Course> coursesList = new ArrayList<>();
        coursesList.add(new Course("English"));
        users.clear();
        Setting s = new Setting(false, false, 12, false, false);
        users.add(new RegisteredUser("Madelyn", "Johnson", "mjohnson", "Password2", "MJ@gmail.com", 0,
                new ArrayList<String>(), s, coursesList));
        users.add(new RegisteredUser("Christian", "Biermann", "cbierman", "Password", "CB@gmail.com"));
        users.add(new RegisteredUser("Ariannah", "D", "dAriannah", "Password23", "AD@gmail.com"));
        DataWriter.saveUsers();
    }

    @AfterEach
    public void tearDown() {
        users.clear();
        DataWriter.saveUsers();
    }

    // Testing any nullHaveCourse and emptyHaveCourse is based
    // on it already being in the Courses.json which depends on the addCourse
    // method.
    // So this will be tested with that method
    @Test
    public void testValidHaveCourse() {
        assertEquals(true, courseList.haveCourse(courses.get(0).getLanguage()));
    }

    @Test
    public void addValidCourse() {
        Course course = new Course("Test-English");
        assertEquals(true, courseList.addCourse(course));
    }

    @Test
    public void addNullCourse() {
        Course course = new Course(null, null);
        assertEquals(false, courseList.addCourse(course));
    }

    // Simple Returns a null error. So counting it as a bug
    @Test
    public void addEmptyCourse() {
        Course course = new Course("");
        assertEquals(false, courseList.addCourse(course));
    }

}
