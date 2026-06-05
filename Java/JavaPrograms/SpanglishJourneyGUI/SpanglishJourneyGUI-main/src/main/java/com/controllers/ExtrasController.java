package com.controllers;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;

import com.model.*;
import com.spanglishjourney.App;

public class ExtrasController {

    @FXML
    void switchToDictionary(MouseEvent event) throws IOException {
        App.setRoot("dictionary");
    }

    @FXML
    void switchToExtras(MouseEvent event) throws IOException {
        App.setRoot("extras");
    }

    @FXML
    void switchToFlashcard(MouseEvent event) throws IOException {
        App.setRoot("flashcards");
    }

    @FXML
    void switchToProfile(MouseEvent event) throws IOException {
        App.setRoot("userprofile");
    }

    @FXML
    void switchToSplash(MouseEvent event) throws IOException {
        App.setRoot("splashpage");
    }

}
