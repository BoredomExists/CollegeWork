package com.spanglishjourney;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.model.Course;
import com.model.DataLoader;
import com.model.DataWriter;
import com.model.RegisteredUser;
import com.model.Setting;
import com.model.UserList;

public class DataWriterTest {
    private DataLoader dl = new DataLoader();

    private UserList userList = UserList.getInstance();
    private ArrayList<RegisteredUser> users = userList.getUsers();

    @BeforeEach
    public void setup() {
        users.clear();
        DataWriter.saveUsers();
    }

    @AfterEach
    public void tearDown() {
        users.clear();
        DataWriter.saveUsers();
    }

    @Test
    public void testWritingZeroUsers() {
        users.clear();
        DataWriter.saveUsers();
        assertEquals(0, users.size());
    }

    @Test
    public void testWritingOneUser() {
        Course course = new Course("English");
        users.add(new RegisteredUser("Christian", "Biermann", "cbierman", "Password", "CB@gmail.com"));
        users.get(0).addCourse(course);
        DataWriter.saveUsers();
        assertEquals("cbierman", dl.getUsers().get(0).getUsername());
    }

    @Test
    public void testWritingMultipleUsers() {
        Course course = new Course("English");
        users.add(new RegisteredUser("Christian", "Biermann", "cbierman", "Password", "CB@gmail.com"));
        users.add(new RegisteredUser("Madelyn", "Johnson", "mjohnson", "Password2", "MJ@gmail.com"));
        users.add(new RegisteredUser("Ariannah", "D", "dAriannah", "Password23", "AD@gmail.com"));
        users.get(0).addCourse(course);
        users.get(1).addCourse(course);
        users.get(2).addCourse(course);

        DataWriter.saveUsers();
        // Testing range of users
        assertEquals("cbierman", dl.getUsers().get(0).getUsername());
        assertEquals("mjohnson", dl.getUsers().get(1).getUsername());
        assertEquals("dAriannah", dl.getUsers().get(2).getUsername());
    }

    @Test
    public void testWritingEmptyUser() {
        Course course = new Course("English");
        users.add(new RegisteredUser("", "", "", "", ""));
        users.get(0).addCourse(course);
        DataWriter.saveUsers();
        assertNotEquals("", dl.getUsers().get(0).getUsername());
    }

    @Test
    public void testWritingWhiteSpaceUser() {
        Course course = new Course("English");
        users.add(new RegisteredUser("", "", "    ", "", ""));
        users.get(0).addCourse(course);

        DataWriter.saveUsers();
        assertNotEquals("    ", dl.getUsers().get(0).getUsername());
    }

    @Test
    public void testWritingNullUser() {
        // Should throw out of bounds as user shouldn't be written
        assertThrows(NullPointerException.class, () -> users.add(new RegisteredUser(null, null, null, null, null)));
        DataWriter.saveUsers();
        assertThrows(IndexOutOfBoundsException.class, () -> users.get(0));
    }

    @Test
    public void testWritingSettings() {
        ArrayList<Course> userCourses = new ArrayList<>();
        userCourses.add(new Course("English"));
        Setting settings = new Setting(false, false, 0, false, true);
        users.add(new RegisteredUser("Madelyn", "Johnson", "mjohnson", "Password2", "MJ@gmail.com", 0,
                new ArrayList<String>(), settings, userCourses));
        DataWriter.saveUsers();

        assertEquals(false, dl.getUsers().get(0).getSettings().getDarkMode());
        assertEquals(false, dl.getUsers().get(0).getSettings().getTextToSpeech());
        assertEquals(0, dl.getUsers().get(0).getSettings().getFontSize());
        assertEquals(false, dl.getUsers().get(0).getSettings().getNotifications());
        assertEquals(true, dl.getUsers().get(0).getSettings().getLessonTimer());
    }

    @Test
    public void testWritingFriends() {
        ArrayList<Course> userCourses = new ArrayList<>();
        userCourses.add(new Course("English"));
        ArrayList<String> friends = new ArrayList<String>();
        ArrayList<String> friendsList = new ArrayList<String>();
        friends.add("TestUser123");
        friends.add("testabc");
        users.add(new RegisteredUser("Madelyn", "Johnson", "mjohnson", "Password2", "MJ@gmail.com", 0, friends, new Setting(false, false, 0, false, true), userCourses));
        DataWriter.saveUsers();

        friendsList = dl.getUsers().get(0).getFriendsList();

        assertEquals("TestUser123", friendsList.get(0));
        assertEquals("testabc", friendsList.get(1));
    }

    @Test
    public void testWritingCourses() {
        users.clear();
        Course course = new Course("Test");
        users.add(new RegisteredUser("Test First", "Test Last", "Test Username", "Test Password", "Test@email.com"));
        users.get(0).addCourse(course);
        assertNotEquals(null, users.get(0).getCourses().get(0));
        assertEquals("Test", users.get(0).getCourses().get(0).getLanguage());
    }

    @Test
    public void testDefaultModule() {
        // Test if modules aren't null
        Course course = new Course("English");
        assertNotEquals(null, course.getModule("Basic English Essentials"));
        assertNotEquals(null, course.getModule("Everyday English and Basic Grammer"));

        // Test that progress is defaulted
        assertEquals(0.0, course.getModule("Basic English Essentials").getProgress());
        assertEquals(0.0, course.getModule("Everyday English and Basic Grammer").getProgress());
    }

    @Test
    public void testWritingBookmarkedLessons() {
        Course course = new Course("English");
        users.add(new RegisteredUser("Christian", "Biermann", "biermanc", "Password", "Email@gmail.com"));
        users.get(0).addCourse(course);
        users.get(0).getCourses().get(0).getModule("Basic English Essentials").addBookmarkedLesson("Test1");
        users.get(0).getCourses().get(0).getModule("Basic English Essentials").addBookmarkedLesson("Test2");
        users.get(0).getCourses().get(0).getModule("Basic English Essentials").addBookmarkedLesson("Test3");

        // Test if modules aren't null, test both ends of the data
        assertEquals("Test1",
                users.get(0).getCourses().get(0).getModule("Basic English Essentials").getBookmarkedLessons().get(0));

        assertEquals("Test3",
                users.get(0).getCourses().get(0).getModule("Basic English Essentials").getBookmarkedLessons().get(2));
    }

    @Test
    public void testWritingMissedQuestions() {
        Course course = new Course("English");
        users.add(new RegisteredUser("Christian", "Biermann", "biermanc", "Password", "Email@gmail.com"));
        users.get(0).addCourse(course);
        String lessonName = "TestLesson";
        ArrayList<String> missedQuestions = new ArrayList<>();
        missedQuestions.add("Test1");
        missedQuestions.add("Test2");
        missedQuestions.add("Test3");
        users.get(0).getCourses().get(0).getModule("Basic English Essentials").addMissedQuestion(lessonName,
                missedQuestions);

        // Test if modules aren't null, test both ends of the data
        assertEquals("Test1",
                users.get(0).getCourses().get(0).getModule("Basic English Essentials").getMissedQuestions()
                        .get(lessonName).get(0));

        assertEquals("Test3",
                users.get(0).getCourses().get(0).getModule("Basic English Essentials").getMissedQuestions()
                        .get(lessonName).get(2));
    }

    @Test
    public void testWritingCorrectQuestions() {
        Course course = new Course("English");
        users.add(new RegisteredUser("Christian", "Biermann", "biermanc", "Password", "Email@gmail.com"));
        users.get(0).addCourse(course);
        String lessonName = "TestLesson";
        ArrayList<String> correctQuestions = new ArrayList<>();
        correctQuestions.add("Test1");
        correctQuestions.add("Test2");
        correctQuestions.add("Test3");
        users.get(0).getCourses().get(0).getModule("Basic English Essentials").addCorrectQuestion(lessonName,
                correctQuestions);

        // Test if modules aren't null, test both ends of the data
        assertEquals("Test1",
                users.get(0).getCourses().get(0).getModule("Basic English Essentials").getCorrectQuestions()
                        .get(lessonName).get(0));

        assertEquals("Test3",
                users.get(0).getCourses().get(0).getModule("Basic English Essentials").getCorrectQuestions()
                        .get(lessonName).get(2));
    }
}
