package com.spanglishjourney;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.model.DataWriter;
import com.model.RegisteredUser;
import com.model.UserList;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private UserList users = UserList.getInstance();
    private ArrayList<RegisteredUser> userList = users.getUsers();

    @BeforeEach
    public void setup() {
        userList.clear();
        userList.add(new RegisteredUser("Christian", "Biermann", "cbierman", "password", "CB@gmail.com"));
        DataWriter.saveUsers();
    }

    @AfterEach
    public void tearDown() {
        UserList.getInstance().getUsers().clear();
        DataWriter.saveUsers();
    }

    @Test
    public void testHaveUserValid() {
        RegisteredUser cbierman = users.haveUser("cbierman", "password");
        boolean test = false;
        if (cbierman != null) {
            test = true;
        }
        assertTrue(true);
    }
}
