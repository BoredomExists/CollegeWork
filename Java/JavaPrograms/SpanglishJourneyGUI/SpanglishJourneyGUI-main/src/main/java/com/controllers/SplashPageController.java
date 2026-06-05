package com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import com.model.*;
import com.spanglishjourney.App;

public class SplashPageController implements Initializable {

    @FXML
    private Label firstlb;

    @FXML
    private Label secondlb;

    @FXML
    private Label thirdlb;

    @FXML
    private Label leaderboard_label;

    @FXML
    private Label welcome_label;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private LeaderBoard lb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        lb = lsf.getLeaderBoard();

        welcome_label.setText("Welcome " + currentUser.getUsername() + "!");
        leaderboard_label.setText("Los Tres Mejores");

        ArrayList<Map.Entry<String, Integer>> lbUsers = lb.retrieveSortedUsers();
        firstlb.setText(lbUsers.get(0).getKey() + ": " + lbUsers.get(0).getValue());
        secondlb.setText(lbUsers.get(1).getKey() + ": " + lbUsers.get(1).getValue());
        thirdlb.setText(lbUsers.get(2).getKey() + ": " + lbUsers.get(2).getValue());

    }

    @FXML
    void goToLeaderboard(MouseEvent event) throws IOException {
        App.setRoot("leaderboard");
    }

    @FXML
    void goToCourses(MouseEvent event) throws IOException {
        App.setRoot("courses");
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
    void logout(MouseEvent event) throws IOException {
        lsf.logout();
        App.setRoot("loginpage");
    }
}
