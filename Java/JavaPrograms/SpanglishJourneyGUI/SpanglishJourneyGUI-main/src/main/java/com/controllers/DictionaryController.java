package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.*;
import com.model.Module;
import com.spanglishjourney.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class DictionaryController implements Initializable {

    @FXML
    private Label err_label;

    @FXML
    private Label definition;

    @FXML
    private Label pronunciation;

    @FXML
    private Label wordInDictionary;

    @FXML
    private Label otherLanguageWord;

    @FXML
    private TextField word_search;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private Course currentCourse;
    private Module currentModule;
    private Lesson currentLesson;
    private WordList wordList;
    private Word wordOBJ;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        currentCourse = lsf.getCurrentCourse();
        currentModule = lsf.getCurrentModule();
        currentLesson = lsf.getCurrentLesson();
        wordList = lsf.getWordList();
    }

    @FXML
    void findWordInDictionary(MouseEvent event) {
        if (wordList.haveWord(word_search.getText()) == null) {
            definition.setText("");
            err_label.setText("Word not found in Dictionary");
        } else {
            wordOBJ = wordList.haveWord(word_search.getText());
            wordInDictionary.setText(wordOBJ.getWordInOtherLanguage());
            otherLanguageWord.setText(wordOBJ.getWord());
            definition.setText(wordOBJ.getDefinition());
            pronunciation.setText(wordOBJ.getPronunciation());
            err_label.setText("");
        }
    }

    @FXML
    void switchToExtras(MouseEvent event) throws IOException {
        App.setRoot("extras");
    }

    @FXML
    void switchToSplash(MouseEvent event) throws IOException {
        App.setRoot("splashpage");
    }

    @FXML
    void switchToProfile(MouseEvent event) throws IOException {
        App.setRoot("userprofile");
    }

}
