package com.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.model.*;
import com.spanglishjourney.App;

public class LeaderboardController implements Initializable {

    @FXML
    private TableView<Map.Entry<String, Integer>> leaderboard;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> position;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> username;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> userscore;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private LeaderBoard leaderBoardInstance;

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        leaderBoardInstance = lsf.getLeaderBoard();

        position.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(leaderboard.getItems().indexOf(cellData.getValue()) + 1)
                        .asObject());
        username.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        userscore.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());

        leaderboard.getItems().setAll(leaderBoardInstance.retrieveSortedUsers());
    }

}
