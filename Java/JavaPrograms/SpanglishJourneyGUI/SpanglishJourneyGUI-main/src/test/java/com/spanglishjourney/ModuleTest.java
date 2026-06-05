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
import com.model.Lesson;
import com.model.RegisteredUser;
import com.model.UserList;
import com.model.Module;


public class ModuleTest {
    static DataLoader dl = new DataLoader();
    private UserList userList = UserList.getInstance();
    private ArrayList<RegisteredUser> users = userList.getUsers();
    private static ArrayList<Module> modules = dl.getCourses().get(0).getModules();
    private ArrayList<Lesson> lessons = modules.get(0).getLessons();

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
        // users.clear();
        DataWriter.saveUsers();
    }

    @Test
    public void testValidGetLesson() {
        Lesson lesson = modules.get(0).getLesson("Greetings and Introductions");
        assertEquals("Greetings and Introductions", lesson.getLessonName());

    }

    @Test
    public void testInvalidGetLesson() {
        Lesson lesson = null;
        ArrayList<Lesson> lessons = modules.get(0).getLessons();

        // Tests invalid lesson
        lesson = modules.get(0).getLesson("Test Lesson");
        assertEquals(false, lessons.contains(lesson));

        // Tests exisiting lesson
        // lesson = modules.get(0).getLesson("Greetings and Introductions");
        // assertEquals(false, lessons.contains(lesson));
    }

    @Test
    public void testNullGetLesson() {
        Module module = modules.get(0);
        assertEquals(null, module.getLesson(null));

    }

    @Test
    public void testNoLessonUpdateProgress() {
        Module module = modules.get(0);
        module.updateProgress();
        assertEquals(0.0, module.getProgress());
    }

    // Works
    @Test
    public void testValidUpdateProgress() {
        Module module = new Module("Test Module");

        // Create mock lessons
        Lesson lesson1 = new Lesson("Lesson 1", new HashMap<>(), new ArrayList<>());
        lesson1.setProgress(0.5);

        Lesson lesson2 = new Lesson("Lesson 2", new HashMap<>(), new ArrayList<>());
        lesson2.setProgress(1.0);

        Lesson lesson3 = new Lesson("Lesson 3", new HashMap<>(), new ArrayList<>());
        lesson3.setProgress(0.3);

        // Add lessons to the module
        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        module.addLessons(lessons);

        // Update progress
        module.updateProgress();
        assertEquals(60.0, module.getProgress());
    }

    @Test
    public void testNoLessonUpdateScore() {
        Module module = modules.get(0);

        assertEquals(0.0, module.getScore());
    }

    // Works
    @Test
    public void testValidUpdateScore() {
        Module module = modules.get(0);
        module.updateScore();
        assertEquals(100.0, module.getScore());
    }
}