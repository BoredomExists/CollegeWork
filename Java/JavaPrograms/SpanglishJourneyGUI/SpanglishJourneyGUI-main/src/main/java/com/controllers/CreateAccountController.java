package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.Course;
import com.model.*;
import com.spanglishjourney.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CreateAccountController implements Initializable {

    @FXML
    private Label label_err;

    @FXML
    private TextField txt_confirmpassword;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_firstname;

    @FXML
    private TextField txt_lastname;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_username;

    private LanguageSystemFacade lsf;

    @FXML
    void createAccountSubmit(MouseEvent event) throws IOException {
        String firstName = txt_firstname.getText();
        String lastName = txt_lastname.getText();
        String username = txt_username.getText();
        String password = txt_password.getText();
        String confirmPassword = txt_confirmpassword.getText();
        String email = txt_email.getText();

        if (firstName.equals("") || lastName.equals("")
                || username.equals("") || password.equals("")
                || email.equals("")) {
            label_err.setText("Sorry, You cannot leave fields blank");
            return;
        }

        if (lsf.createAccount(firstName, lastName, username, password, email) == null) {
            label_err.setText("This user cannot be created.");
            return;
        }
        if (password.trim().equals(confirmPassword.trim())) {
            lsf.createAccount(firstName, lastName, username, password, email);
            lsf.login(username, password);
            lsf.getCurrentUser().getCourses().add(new Course("English"));
            lsf.logout();
            App.setRoot("Splashpage");
        } else {
            label_err.setText("This user cannot be created.");
        }
    }

    @FXML
    private void backToLogin(MouseEvent event) throws IOException {
        App.setRoot("loginpage");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lsf = LanguageSystemFacade.getInstance();
    }

}
