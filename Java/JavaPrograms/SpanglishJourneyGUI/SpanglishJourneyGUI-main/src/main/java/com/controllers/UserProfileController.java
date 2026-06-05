package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.LanguageSystemFacade;
import com.model.RegisteredUser;
import com.spanglishjourney.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class UserProfileController implements Initializable {

    @FXML
    private Label email;

    @FXML
    private TextArea friends;

    @FXML
    private Label name;

    @FXML
    private Label username;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    ArrayList<String> friendsList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        friendsList = currentUser.getFriendsList();
        username.setText(currentUser.getUsername());
        email.setText(currentUser.getEmail());
        name.setText(currentUser.getFirstName() + " " + currentUser.getLastName());

        listFriends();
    }

    private void listFriends() {
        String allFriends = "";
        for (int i = 0; i < friendsList.size(); i++) {
            allFriends += friendsList.get(i) + "\n";
        }
        friends.setText(allFriends);
    }

    @FXML
    void goToExtras(MouseEvent event) throws IOException {
        App.setRoot("extras");
    }

    @FXML
    void goToSettings(MouseEvent event) throws IOException {
        App.setRoot("userSettings");
    }

    @FXML
    void goToSplashPage(MouseEvent event) throws IOException {
        App.setRoot("splashpage");
    }
}
