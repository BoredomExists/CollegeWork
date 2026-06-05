package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.*;
import com.spanglishjourney.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ResetPasswordController implements Initializable {

    @FXML
    private TextField resetpassword_txt;

    @FXML
    private TextField email_txt;

    @FXML
    private Label confirm_label;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private UserList users;
    private String newPassword;
    private String userEmail;

    @FXML
    void resetpassword(MouseEvent event) throws IOException {
        newPassword = resetpassword_txt.getText();
        userEmail = email_txt.getText();

        for (int i = 0; i < users.getUsers().size(); i++) {
            if (users.getUsers().get(i).getEmail().equalsIgnoreCase(userEmail)) {
                lsf.setCurrentUser(users.getUsers().get(i));
                currentUser = lsf.getCurrentUser();
            }
        }

        if (currentUser == null || !currentUser.getEmail().equalsIgnoreCase(userEmail)) {
            confirm_label.setText("User email was not found.");
        } else if (newPassword == null || newPassword.isEmpty()) {
            confirm_label.setText("New password cannot be null or empty.");
        }
        if (currentUser.getEmail().equalsIgnoreCase(userEmail)
                && !(newPassword == null || newPassword.isEmpty())) {
            lsf.resetPassword(currentUser.getPassword(), newPassword);
            lsf.logout();
            App.setRoot("loginpage");
        }

    }

    @FXML
    private void goToLogin(MouseEvent event) throws IOException {
        App.setRoot("loginpage");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lsf = LanguageSystemFacade.getInstance();
        users = lsf.getUserList();
    }

}
