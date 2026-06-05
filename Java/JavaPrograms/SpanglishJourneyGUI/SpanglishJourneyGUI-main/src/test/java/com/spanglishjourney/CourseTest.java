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

public class CourseTest {
    private DataLoader dl = new DataLoader();
    private CourseList courseList = CourseList.getInstance();
    private UserList userList = UserList.getInstance();
    private ArrayList<RegisteredUser> users = userList.getUsers();
    private ArrayList<Course> courses = courseList.getCourses();
    private LanguageSystemFacade lsf = new LanguageSystemFacade();

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

        courses.clear();
        courses.add(new Course("TestLanguage"));
        courses.add(new Course("TestLanguage"));
        courses.add(new Course("TestLanguage"));
        lsf.setCurrentCourse(courses.get(0));
    }

    @AfterEach
    public void tearDown() {
        users.clear();
        DataWriter.saveUsers();
    }

    @Test
    public void addNullModule() {
        lsf.getCurrentCourse().addModule(null);
        assertEquals(null, lsf.getCurrentCourse().getModule(null));
    }
}
