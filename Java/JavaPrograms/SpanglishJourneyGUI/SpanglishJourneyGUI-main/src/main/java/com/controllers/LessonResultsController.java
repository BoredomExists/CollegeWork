package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.*;
import com.model.Module;
import com.spanglishjourney.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class LessonResultsController implements Initializable {

    @FXML
    private PieChart results_piechart;

    @FXML
    private Label score;

    private LanguageSystemFacade lsf;
    private Module currentModule;
    private Lesson currentLesson;
    private ArrayList<String> correctQuestions;
    private ArrayList<String> missedQuestions;

    private int totalQuestions;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        totalQuestions = 0;
        lsf = LanguageSystemFacade.getInstance();
        currentModule = lsf.getCurrentModule();
        currentLesson = lsf.getCurrentLesson();
        correctQuestions = currentModule.getCorrectQuestions().get(currentLesson.getLessonName());
        missedQuestions = currentModule.getMissedQuestions().get(currentLesson.getLessonName());

        if (correctQuestions == null) {
            totalQuestions += 0;
        } else {
            totalQuestions += correctQuestions.size();
        }
        if (missedQuestions == null) {
            totalQuestions += 0;
        } else {
            totalQuestions += missedQuestions.size();
        }

        if (correctQuestions == null)
            score.setText(0 + "/" + totalQuestions);
        else
            score.setText(correctQuestions.size() + "/" + totalQuestions);
        updatePieChart();
        lsf.updateProgressAndScore(currentModule, currentLesson);
        lsf.logout();
    }

    private void updatePieChart() {
        results_piechart.getData().clear();

        PieChart.Data correctQuestionsData;
        PieChart.Data missedQuestionsData;

        if (correctQuestions == null) {
            correctQuestionsData = new PieChart.Data("Correct Questions", 0);
        } else {
            correctQuestionsData = new PieChart.Data("Correct Questions", correctQuestions.size());
        }
        if (missedQuestions == null) {
            missedQuestionsData = new PieChart.Data("Missed Questions", 0);
        } else {
            missedQuestionsData = new PieChart.Data("Missed Questions", missedQuestions.size());
        }

        results_piechart.getData().addAll(correctQuestionsData, missedQuestionsData);
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

}
