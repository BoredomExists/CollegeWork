package com.spanglishjourney;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.model.Course;
import com.model.DataLoader;
import com.model.DataWriter;
import com.model.LanguageSystemFacade;
import com.model.RegisteredUser;
import com.model.UserList;

// Tested By Christian Biermann

public class UserListTest {
    private DataLoader dl = new DataLoader();
    private UserList userList = UserList.getInstance();
    private LanguageSystemFacade lsf = new LanguageSystemFacade();
    private ArrayList<RegisteredUser> users = userList.getUsers();

    @BeforeEach
    public void setup() {
        users.clear();
        users.add(new RegisteredUser("Christian", "Biermann", "cbierman", "Password", "cbierman@gmail.com"));
        users.add(new RegisteredUser("Matthew", "Johnson", "mjohnson", "NewPassword", "mjohnson@gmail.com"));
        users.add(new RegisteredUser("Keaton", "Hill", "khill", "SimplePassword", "khill@gmail.com"));
        users.get(0).addCourse(new Course("English"));
        users.get(1).addCourse(new Course("English"));
        users.get(2).addCourse(new Course("English"));
        DataWriter.saveUsers();
    }

    @AfterEach
    public void tearDown() {
        users.clear();
        DataWriter.saveUsers();
    }

    @Test
    public void testValidHaveUser() {
        String username = "cbierman";
        String password = "Password";
        assertNotEquals(null, userList.haveUser(username, password));
    }

    @Test
    public void testNullHaveUser() {
        String username = null;
        String password = null;
        assertEquals(null, userList.haveUser(username, password));
    }

    @Test
    public void testEmptyHaveUser() {
        String username = "";
        String password = "null";
        assertEquals(null, userList.haveUser(username, password));
    }

    @Test
    public void testValidContains() {
        assertEquals(true, userList.contains("cbierman"));
    }

    // NOTE: Does pass the test, but only if a null user does not
    // exists within the users.json. So simply put, this test is reliant
    // on another test.
    @Test 
    public void testNullContains() {
        assertEquals(false, userList.contains(null));
    }

    // NOTE: Same thing as testNullContains is applied here
    @Test 
    public void testEmptyContains() {
        assertEquals(false, userList.contains(""));
    }

}
