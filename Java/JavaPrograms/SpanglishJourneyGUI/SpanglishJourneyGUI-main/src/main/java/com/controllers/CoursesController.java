package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.*;
import com.spanglishjourney.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;

public class CoursesController implements Initializable {

    @FXML
    private ProgressIndicator englishcourse_progress;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private Course currentCourse;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();

        englishcourse_progress.setProgress(currentUser.getCourses().get(0).getProgress() / 100);

    }

    @FXML
    void goToExtras(MouseEvent event) throws IOException {
        App.setRoot("extras");
    }

    @FXML
    void goToModules(MouseEvent event) throws IOException {
        App.setRoot("modules");
    }

    @FXML
    void wentToModules(ActionEvent event) {
        currentCourse = currentUser.getCourses().get(0);
        lsf.setCurrentCourse(currentCourse);
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