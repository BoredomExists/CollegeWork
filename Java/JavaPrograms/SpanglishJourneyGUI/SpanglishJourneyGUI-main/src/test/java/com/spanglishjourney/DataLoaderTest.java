package com.spanglishjourney;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

public class DataLoaderTest {
    private static DataLoader dl = new DataLoader();

    private static UserList userList = UserList.getInstance();
    private static ArrayList<RegisteredUser> users = userList.getUsers();

    @BeforeEach
    public void setup() {
        users.clear();
        users.add(new RegisteredUser("Christian", "Biermann", "cbierman", "Password", "cbierman@gmail.com"));
        users.add(new RegisteredUser("Matthew", "Johnson", "mjohnson", "NewPassword", "mjohnson@gmail.com"));
        users.add(new RegisteredUser("Keaton", "Hill", "khill", "SimplePassword", "khill@gmail.com"));
        users.get(0).addCourse(new Course("English"));
        users.get(1).addCourse(new Course("English"));
        users.get(2).addCourse(new Course("English"));

        Setting setting = new Setting(false, false, 12, false, false);
        users.get(0).setSettings(setting);

        ArrayList<String> missedQuestions = new ArrayList<>();
        ArrayList<String> correctQuestions = new ArrayList<>();

        correctQuestions.add("Q1");
        correctQuestions.add("Q2");
        missedQuestions.add("Q3");
        missedQuestions.add("Q4");

        users.get(0).getCourses().get(0).getModule("Basic English Essentials").addBookmarkedLesson("Test Bookmarked Lesson");
        users.get(0).getCourses().get(0).getModule("Basic English Essentials").addCorrectQuestion("Test Correct Questions", correctQuestions);
        users.get(0).getCourses().get(0).getModule("Basic English Essentials").addMissedQuestion("Test Missed Questions", missedQuestions);
        DataWriter.saveUsers();
    }

    @AfterEach
    public void tearDown() {
        //users.clear();
        DataWriter.saveUsers();
    }

    @Test
    public void testUserSize() {
        assertEquals(3, dl.getUsers().size());
    }

    @Test
    public void testGetFirstAndLastUser() {
        assertEquals("Christian", dl.getUsers().get(0).getFirstName());
        assertEquals("Keaton", dl.getUsers().get(2).getFirstName());
    }

    @Test
    public void testGetEmpty() {
        UserList.getInstance().getUsers().clear();
        DataWriter.saveUsers();
        assertEquals(0, dl.getUsers().size());
    }

    @Test
    public void testGettingEmptyFriendList() {
        assertEquals(0, dl.getUsers().get(0).getFriendsList().size());
    }

    @Test
    public void testGettingSettings() {
        assertEquals(false, dl.getUsers().get(0).getSettings().getDarkMode());
        assertEquals(false, dl.getUsers().get(0).getSettings().getTextToSpeech());
        assertEquals(12, dl.getUsers().get(0).getSettings().getFontSize());
        assertEquals(false, dl.getUsers().get(0).getSettings().getNotifications());
        assertEquals(false, dl.getUsers().get(0).getSettings().getLessonTimer());
    }

    @Test
    public void testGettingCourses() {
        assertEquals(1, dl.getCourses().size());
    }

    @Test
    public void testGettingDefaultModules() {
        assertEquals(2, dl.getCourses().get(0).getDefaultModules().size());
    }

    @Test
    public void testGettingLessons() {
        assertEquals(5, dl.getCourses().get(0).getModule("Basic English Essentials").getLessons().size());
        assertEquals(5, dl.getCourses().get(0).getModule("Everyday English and Basic Grammer").getLessons().size());
    }

    @Test
    public void testGettingBookmarkedLessons() {
        ArrayList<String> bookmarkedLessons = new ArrayList<>();
        bookmarkedLessons.add("Test Bookmarked Lesson");
        assertEquals(bookmarkedLessons, dl.getUsers().get(0).getCourses().get(0).getModule("Basic English Essentials").getBookmarkedLessons());
    }

    @Test
    public void testGettingMissedQuestions() {
        ArrayList<String> missedQuestions = new ArrayList<>();
        missedQuestions.add("Q3");
        missedQuestions.add("Q4");
        assertEquals(missedQuestions,
                dl.getUsers().get(0).getCourses().get(0).getModule("Basic English Essentials").getMissedQuestions()
                        .get("Test Missed Questions"));

    }

    @Test
    public void testGettingCompletedQuestions() {
        ArrayList<String> correctQuestions = new ArrayList<>();
        correctQuestions.add("Q1");
        correctQuestions.add("Q2");
        assertEquals(correctQuestions,
                dl.getUsers().get(0).getCourses().get(0).getModule("Basic English Essentials").getCorrectQuestions()
                        .get("Test Correct Questions"));

    }

    @Test
    public void testGetModuleSize() {
        assertEquals(2, dl.getUsers().get(0).getCourses().get(0).getModules().size());

    }

    @Test
    public void testGettingLessonSize() {
        assertEquals(5, dl.getUsers().get(0).getCourses().get(0).getModules().get(0).getLessons().size());
    }

    @Test
    public void testGettingLessonWordPool() {
        assertEquals(5,
                dl.getUsers().get(0).getCourses().get(0).getModules().get(0).getLessons().get(0).getWordPool().size());
    }

    @Test
    public void testGettingLessonQuestions() {
        assertEquals(4,
                dl.getUsers().get(0).getCourses().get(0).getModules().get(0).getLessons().get(0).getQuestions().size());
        assertEquals(2, dl.getUsers().get(0).getCourses().get(0).getModules().get(0).getLessons().get(0).getQuestions()
                .get("MultipleChoice").size());
        assertEquals(1, dl.getUsers().get(0).getCourses().get(0).getModules().get(0).getLessons().get(0).getQuestions()
                .get("WordMatchGame").size());
    }

    @Test
    public void testGettingFirstQuestion() {
        assertEquals("¿Cuál es la frase en inglés para 'Hola'?",
                dl.getUsers().get(0).getCourses().get(0).getModules().get(0).getLessons().get(0).getQuestions()
                        .get("MultipleChoice").get(0).getQuestion());
        assertEquals(4, dl.getUsers().get(0).getCourses().get(0).getModules().get(0).getLessons().get(0).getQuestions()
                .get("MultipleChoice").get(0).getAnswers().size());
        assertEquals(300.0, dl.getUsers().get(0).getCourses().get(0).getModules().get(0).getLessons().get(0)
                .getQuestions().get("MultipleChoice").get(0).getTimer());
        assertEquals("Hello", dl.getUsers().get(0).getCourses().get(0).getModules().get(0).getLessons().get(0)
                .getQuestions().get("MultipleChoice").get(0).getCorrectAnswer());
    }

    @Test
    public void testGettingWordsSize() {
        assertEquals(12, dl.getWords().size());
    }

    @Test
    public void testGettingFirstWord() {
        assertEquals("Hello", dl.getWords().get(0).getWord());
        assertEquals("Hola", dl.getWords().get(0).getWordInOtherLanguage());
        assertEquals("A greeting used when meeting someone or starting a conversation.",
                dl.getWords().get(0).getDefinition());
        assertEquals("Heh-loh", dl.getWords().get(0).getPronunciation());
    }

    @Test
    public void testGettingLastWord() {
        assertEquals("Recommend", dl.getWords().get(11).getWord());
        assertEquals("Recomendar", dl.getWords().get(11).getWordInOtherLanguage());
        assertEquals("To suggest something as being particularly good.", dl.getWords().get(11).getDefinition());
        assertEquals("Reh-kuh-mend", dl.getWords().get(11).getPronunciation());
    }

}
