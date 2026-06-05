package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Random;

import com.spanglishjourney.App;
import com.model.*;
import com.model.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;

public class LessonsController implements Initializable {

    @FXML
    private Button lessonfive_btn;

    @FXML
    private ProgressIndicator lessonfive_progress;

    @FXML
    private Button lessonfour_btn;

    @FXML
    private ProgressIndicator lessonfour_progress;

    @FXML
    private Button lessonone_btn;

    @FXML
    private ProgressIndicator lessonone_progress;

    @FXML
    private Button lessonthree_btn;

    @FXML
    private ProgressIndicator lessonthree_progress;

    @FXML
    private Button lessontwo_btn;

    @FXML
    private ProgressIndicator lessontwo_progress;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private Course currentCourse;
    private Module currentModule;
    ArrayList<Lesson> lessons;
    Random random;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        currentCourse = lsf.getCurrentCourse();
        currentModule = lsf.getCurrentModule();
        lessons = lsf.getCurrentModule().getLessons();

        random = new Random();

        setLabels();
        setProgress();
        setButtons();
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

    @FXML
    void goToQuestion(MouseEvent event) throws IOException {
        try {
            int number = random.nextInt(4);
            Lesson currentLesson = lsf.getCurrentLesson();
            ArrayList<String> allQuestions = currentLesson.getAllQuestionsType();
            if (number == 0 && allQuestions.contains("MultipleChoice")) {
                App.setRoot("multiplechoice");
            } else if (number == 1 && allQuestions.contains("Fill in The Blank")) {
                App.setRoot("fillintheblank");
            } else if (number == 2 && allQuestions.contains("Sentence Builder")) {
                App.setRoot("sentencebuilder");
            } else if (number == 3 && allQuestions.contains("WordMatchGame")) {
                App.setRoot("wordmatch");
            } else {
                goToQuestion(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void retrieveQuestion(ActionEvent event) {
        if (event.getSource() == lessonone_btn) {
            lsf.setCurrentLesson(currentModule.getLessons().get(0));
        } else if (event.getSource() == lessontwo_btn) {
            lsf.setCurrentLesson(currentModule.getLessons().get(1));
        } else if (event.getSource() == lessonthree_btn) {
            lsf.setCurrentLesson(currentModule.getLessons().get(2));
        } else if (event.getSource() == lessonfour_btn) {
            lsf.setCurrentLesson(currentModule.getLessons().get(3));
        } else if (event.getSource() == lessonfive_btn) {
            lsf.setCurrentLesson(currentModule.getLessons().get(4));
        }
    }

    private void setProgress() {
        lessonone_progress.setProgress(lessons.get(0).getProgress() / 100);
        lessontwo_progress.setProgress(lessons.get(1).getProgress() / 100);
        lessonthree_progress.setProgress(lessons.get(2).getProgress() / 100);
        lessonfour_progress.setProgress(lessons.get(3).getProgress() / 100);
        lessonfive_progress.setProgress(lessons.get(4).getProgress() / 100);
    }

    private void setLabels() {
        lessonone_btn.setText(lessons.get(0).getLessonName());
        lessontwo_btn.setText(lessons.get(1).getLessonName());
        lessonthree_btn.setText(lessons.get(2).getLessonName());
        lessonfour_btn.setText(lessons.get(3).getLessonName());
        lessonfive_btn.setText(lessons.get(4).getLessonName());
    }

    private void setButtons() {
        if (lessonone_progress.getProgress() >= 1.0) {
            lessontwo_btn.setDisable(false);
        } else {
            lessontwo_btn.setDisable(true);
        }

        if (lessontwo_progress.getProgress() >= 1.0) {
            lessonthree_btn.setDisable(false);
        } else {
            lessonthree_btn.setDisable(true);
        }

        if (lessonthree_progress.getProgress() >= 1.0) {
            lessonfour_btn.setDisable(false);
        } else {
            lessonfour_btn.setDisable(true);
        }

        if (lessonfour_progress.getProgress() >= 1.0) {
            lessonfive_btn.setDisable(false);
        } else {
            lessonfive_btn.setDisable(true);
        }
    }
}
