package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import com.model.*;
import com.spanglishjourney.App;

public class LoginController implements Initializable {

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private Label label_err;

    private LanguageSystemFacade lsf;
    private RegisteredUser loggedUser;

    @FXML
    private void loginClicked(MouseEvent event) throws IOException {
        String username = txt_username.getText();
        String password = txt_password.getText();

        lsf = LanguageSystemFacade.getInstance();
        loggedUser = lsf.login(username, password);
        if (loggedUser == null) {
            label_err.setText("Invalid Login");
            return;
        }

        App.setRoot("splashpage");
    }

    @FXML
    private void createAccountClicked(MouseEvent event) throws IOException {
        App.setRoot("createaccount");
    }

    @FXML
    void resetpassword(MouseEvent event) throws IOException {
        App.setRoot("resetpassword");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
