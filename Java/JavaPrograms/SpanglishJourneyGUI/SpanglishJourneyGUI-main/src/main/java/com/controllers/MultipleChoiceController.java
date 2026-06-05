package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Random;

import com.model.*;
import com.model.Module;
import com.narration.Narriator;
import com.spanglishjourney.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MultipleChoiceController implements Initializable {

    @FXML
    private Label question_ID;

    @FXML
    private Button submit;

    @FXML
    private Button AChoice;

    @FXML
    private Button BChoice;

    @FXML
    private Button CChoice;

    @FXML
    private Button DChoice;

    @FXML
    private Pane PaneID;

    @FXML
    private Button speak;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private Course currentCourse;
    private Module currentModule;
    private Lesson currentLesson;
    ArrayList<Question> questions;
    ArrayList<String> correctQuestions;
    ArrayList<String> missedQuestions;

    private Random random;
    private int index;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        random = new Random();
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        currentCourse = lsf.getCurrentCourse();
        currentModule = lsf.getCurrentModule();
        currentLesson = lsf.getCurrentLesson();
        questions = lsf.getCurrentLesson().getQuestions().get("MultipleChoice");

        correctQuestions = currentModule.getCorrectQuestions().get(currentLesson.getLessonName());
        missedQuestions = currentModule.getMissedQuestions().get(currentLesson.getLessonName());

        if (correctQuestions == null) {
            correctQuestions = new ArrayList<String>();
        }
        if (missedQuestions == null) {
            missedQuestions = new ArrayList<String>();
        }

        question_ID.setWrapText(true);
        index = -1;

        try {
            setLabels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToExtras(MouseEvent event) throws IOException {
        App.setRoot("extras");
    }

    @FXML
    void goToProfile(MouseEvent event) throws IOException {
        App.setRoot("userprofile");
    }

    @FXML
    void goToSplashPage(MouseEvent event) throws IOException {
        App.setRoot("splashpage");
    }

    private void setLabels() throws IOException {
        index++;
        question_ID.setText(questions.get(index).getQuestion());
        AChoice.setText(questions.get(index).getAnswers().get(0));
        BChoice.setText(questions.get(index).getAnswers().get(1));
        CChoice.setText(questions.get(index).getAnswers().get(2));
        DChoice.setText(questions.get(index).getAnswers().get(3));
    }

    @FXML
    void submitAnswer(ActionEvent event) throws IOException {
        currentLesson.addCompletedQuestion(questions.get(index).getQuestion());

        Object eventtest = event.getSource();

        if (isCorrect(eventtest)) {
            correctQuestions.add(questions.get(index).getQuestion());
            currentModule.addCorrectQuestion(currentLesson.getLessonName(), correctQuestions);
        } else {
            missedQuestions.add(questions.get(index).getQuestion());
            currentModule.addMissedQuestion(currentLesson.getLessonName(), missedQuestions);
        }
        if (index > questions.size() - 2) {
            goToNext(event);
        } else {
            setLabels();
        }
    }

    @FXML
    void narrate(MouseEvent event) throws IOException {
        Narriator.playSound(question_ID.getText());
    }

    private boolean isCorrect(Object event) {
        if (event == AChoice) {
            if (questions.get(index).getCorrectAnswer().equals(AChoice.getText())) {
                return true;
            }
        } else if (event == BChoice) {
            if (questions.get(index).getCorrectAnswer().equals(BChoice.getText())) {
                return true;
            }
        } else if (event == CChoice) {
            if (questions.get(index).getCorrectAnswer().equals(CChoice.getText())) {
                return true;
            }
        } else {
            if (questions.get(index).getCorrectAnswer().equals(DChoice.getText())) {
                return true;
            }
        }
        return false;
    }

    private void goToNext(ActionEvent event) {
        try {
            Lesson currentLesson = lsf.getCurrentLesson();
            ArrayList<Question> allQuestions = currentLesson.getAllQuestions();
            ArrayList<String> completedQuestions = currentLesson.getCompletedQuestions();
    
            // Check if all questions have been answered
            if (completedQuestions.size() == allQuestions.size()) {
                App.setRoot("lessonresults");
                return;
            }
    
            // Iterate to find the first unanswered question
            for (Question question : allQuestions) {
                if (!completedQuestions.contains(question.getQuestion())) {
                    if (question instanceof MultipleChoice) {
                        App.setRoot("multiplechoice");
                    } else if (question instanceof FillinTheBlank) {
                        App.setRoot("fillintheblank");
                    } else if (question instanceof SentenceBuilder) {
                        App.setRoot("sentencebuilder");
                    } else if (question instanceof WordMatchGame) {
                        App.setRoot("wordmatch");
                    }
                    return; // Navigate to the next question
                }
            }
    
            // Fallback if no question is found
            App.setRoot("lessonresults");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
