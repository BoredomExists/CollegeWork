package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.*;
import com.model.Module;
import com.spanglishjourney.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;

public class ModulesController implements Initializable {

    @FXML
    private Button moduleone_btn;

    @FXML
    private ProgressIndicator moduleone_progress;

    @FXML
    private Button moduletwo_btn;

    @FXML
    private ProgressIndicator moduletwo_progress;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private Course currentCourse;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        currentCourse = lsf.getCurrentCourse();

        moduleone_btn.setText(currentCourse.getModules().get(0).getName().trim());
        moduletwo_btn.setText(currentCourse.getModules().get(1).getName().trim());

        moduleone_progress.setProgress(currentCourse.getModules().get(0).getProgress() / 100);
        moduletwo_progress.setProgress(currentCourse.getModules().get(1).getProgress() / 100);

        if (moduleone_progress.getProgress() >= 1.0) {
            moduletwo_btn.setDisable(false);
        } else {
            moduletwo_btn.setDisable(true);
        }
    }

    @FXML
    void goToExtras(MouseEvent event) throws IOException {
        App.setRoot("extras");
    }

    @FXML
    void goToLessons(MouseEvent event) throws IOException {
        App.setRoot("lessons");
    }

    @FXML
    void wentToLessons(ActionEvent event) {
        if (event.getSource() == moduleone_btn) {
            lsf.setCurrentModule(currentCourse.getModules().get(0));
        } else if (event.getSource() == moduletwo_btn) {
            lsf.setCurrentModule(currentCourse.getModules().get(1));
        }
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
