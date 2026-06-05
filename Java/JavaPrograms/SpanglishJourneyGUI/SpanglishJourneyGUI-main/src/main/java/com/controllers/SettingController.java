package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

import com.model.*;
import com.spanglishjourney.App;

public class SettingController implements Initializable {

    @FXML
    private ToggleButton darkmode_btn;

    @FXML
    private Label font_size_label;

    @FXML
    private Label fontSize;

    @FXML
    private Slider font_size_slider;

    @FXML
    private Button homebtn;

    @FXML
    private ToggleButton lessontimer_btn;

    @FXML
    private ToggleButton notifications_btn;

    @FXML
    private ToggleButton text_to_speech_btn;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private Setting userSetting;
    private int sliderValue;

    ArrayList<ToggleButton> togglebtns;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        addAllArrayList();
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        userSetting = currentUser.getSettings();
        font_size_slider.setValue((double) userSetting.getFontSize());
        font_size_label.setText(Integer.toString((int) font_size_slider.getValue()));

        setSettings();

        font_size_slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                sliderValue = (int) font_size_slider.getValue();
                font_size_label.setText(Integer.toString(sliderValue));
                userSetting.setFontSize(sliderValue);

                fontSize.setStyle(String.format("-fx-font: %d arial;", sliderValue));
            }
        });
    }

    @FXML
    void goToExtras(MouseEvent event) throws IOException {
        lsf.saveUsers();
        App.setRoot("extras");
    }

    @FXML
    void goToProfile(MouseEvent event) throws IOException {
        lsf.saveUsers();
        App.setRoot("userprofile");
    }

    @FXML
    void goToSplashPage(MouseEvent event) throws IOException {
        lsf.saveUsers();
        App.setRoot("splashpage");
    }

    @FXML
    private void toggleSettings(MouseEvent event) throws IOException {
        if (event.getSource() == notifications_btn) {
            userSetting.setNotifications(notifications_btn.isSelected());
        } else if (event.getSource() == darkmode_btn) {
            userSetting.setDarkMode(darkmode_btn.isSelected());
        } else if (event.getSource() == text_to_speech_btn) {
            userSetting.setTextToSpeech(text_to_speech_btn.isSelected());
        } else if (event.getSource() == lessontimer_btn) {
            userSetting.setLessonTimer(lessontimer_btn.isSelected());
        }
        setSettings();
    }

    @FXML
    void goToHome(MouseEvent event) throws IOException {
        App.setRoot("homepage");
    }

    private void setSettings() {
        if (!currentUser.getSettings().getNotifications())
            togglebtns.get(0).setStyle("-fx-background-color: #FF0000");
        else
            togglebtns.get(0).setStyle("-fx-background-color: #00FF00");

        if (!currentUser.getSettings().getTextToSpeech())
            togglebtns.get(1).setStyle("-fx-background-color: #FF0000");
        else
            togglebtns.get(1).setStyle("-fx-background-color: #00FF00");

        if (!currentUser.getSettings().getDarkMode())
            togglebtns.get(2).setStyle("-fx-background-color: #FF0000");
        else
            togglebtns.get(2).setStyle("-fx-background-color: #00FF00");

        if (!currentUser.getSettings().getLessonTimer())
            togglebtns.get(3).setStyle("-fx-background-color: #FF0000");
        else
            togglebtns.get(3).setStyle("-fx-background-color: #00FF00");
    }

    private void addAllArrayList() {
        togglebtns = new ArrayList<>();
        togglebtns.add(notifications_btn);
        togglebtns.add(text_to_speech_btn);
        togglebtns.add(darkmode_btn);
        togglebtns.add(lessontimer_btn);
    }
}
