package com.spanglishjourney;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.model.Lesson;
import com.model.Question;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LessonTest {
    private Lesson lesson;
    private HashMap<String, ArrayList<Question>> questions;

    @BeforeEach
    public void setup() {
        questions = new HashMap<>();
        lesson = new Lesson("Test Lesson", questions, new ArrayList<>());
    }

    @Test
    public void testGetCompletedQuestions() {
        lesson.addCompletedQuestion("Question 1");
        lesson.addCompletedQuestion("Question 2");
        assertEquals(2, lesson.getCompletedQuestions().size());
    }

    @Test
    public void testAddCompletedQuestion() {
        lesson.addCompletedQuestion("Question 1");
        assertEquals(1, lesson.getCompletedQuestions().size());
    }

    @Test
    public void testGetLessonName() {
        assertEquals("Test Lesson", lesson.getLessonName());
    }

    @Test
    public void testGetWordPool() {
        assertEquals(0, lesson.getWordPool().size());
    }

   @Test
    public void testUpdateProgressWithZeroQuestions() {
        lesson.updateProgress();
        assertEquals(0.0, lesson.getProgress(), 0.01); // Assuming no questions were added yet
    }

    @Test
    public void testAddDuplicateCompletedQuestion() {
        lesson.addCompletedQuestion("Question 1");
        lesson.addCompletedQuestion("Question 1"); // Adding the same question again
        assertEquals(1, lesson.getCompletedQuestions().size()); // Should still be 1
    }

}
