package com.spanglishjourney;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.model.Course;
import com.model.DataLoader;
import com.model.DataWriter;
import com.model.LanguageSystemFacade;
import com.model.RegisteredUser;
import com.model.Setting;
import com.model.UserList;

// Tested By Christian Biermann

public class FacadeTest {
    private DataLoader dl = new DataLoader();
    private UserList userList = UserList.getInstance();
    private ArrayList<RegisteredUser> users = userList.getUsers();
    private LanguageSystemFacade lsf = new LanguageSystemFacade();

    @BeforeEach
    public void setup() {
        users.clear();
        Course course = new Course("English");
        users.add(new RegisteredUser("Christian", "Biermann", "biermanc", "Password", "Email@gmail.com"));
        users.get(0).addCourse(course);
        DataWriter.saveUsers();
        lsf.setCurrentUser(users.get(0));
        lsf.setCurrentCourse(users.get(0).getCourses().get(0));
    }

    @AfterEach
    public void tearDown() {
        userList.getUsers().clear();
        DataWriter.saveUsers();
    }

    @Test
    public void testCreatingNullUsers() {
        assertEquals(null, lsf.createAccount(null, null, null, null, null));
    }

    @Test
    public void testCreatingEmptyUsers() {
        assertEquals(null, lsf.createAccount("", "", "", "", ""));
    }

    @Test
    public void testLogin() {
        RegisteredUser login = lsf.login("BIERMANC", "Password");
        assertEquals("biermanc", login.getUsername());
    }

    @Test
    public void addNewUser() {
        Course course = new Course("English");
        RegisteredUser newUser = new RegisteredUser("New", "User", "NewUser", "NewPassword", "NewUser@email.com");
        newUser.addCourse(course);
        users.add(newUser);
        DataWriter.saveUsers();
        assertEquals("NewUser", dl.getUsers().get(1).getUsername());
    }

    @Test
    public void testNewPassword() {
        DataWriter.saveUsers();
        lsf.setCurrentUser(users.get(0));
        lsf.resetPassword("Password", "NewPassword");
        assertEquals("NewPassword", users.get(0).getPassword());
    }

    @Test
    public void testAddNullCourse() {
        Course course = new Course(null, null);
        assertEquals(false, lsf.addCourses(course.getLanguage()));
    }

    @Test
    public void testAddEmptyCourse() {
        Course course = new Course("");
        assertEquals(false, lsf.addCourses(course.getLanguage()));
    }

    @Test
    public void testAddFriend() {
        assertTrue(lsf.addFriends("NewFriend"));
        assertTrue(lsf.getCurrentUser().getFriendsList().contains("NewFriend"));
    }

    @Test
    public void testNullFriend() {
        assertEquals(false, lsf.addFriends(null));
        assertEquals(false, lsf.getCurrentUser().getFriendsList().contains(null));
    }

    @Test
    public void testEmptyFriend() {
        assertFalse(lsf.addFriends(""));
        assertFalse(lsf.getCurrentUser().getFriendsList().contains(""));
    }

    @Test
    public void testChangeSettings() {
        Setting currentSettings = lsf.getCurrentUser().getSettings();
        Setting newSettings = new Setting();
        newSettings.setDarkMode(false);
        newSettings.setLessonTimer(false);
        newSettings.setTextToSpeech(false);
        newSettings.setFontSize(28);
        newSettings.setNotifications(false);
        lsf.getCurrentUser().setSettings(newSettings);
        assertEquals(28, lsf.getCurrentUser().getSettings().getFontSize());
        assertNotEquals(currentSettings, lsf.getCurrentUser().getSettings());
    }

    @Test
    public void testShuffleAnswers() {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        ArrayList<String> original = new ArrayList<>(list1);
        lsf.shuffleAnswers(list1);
        assertNotEquals(original, list1);
    }
}
