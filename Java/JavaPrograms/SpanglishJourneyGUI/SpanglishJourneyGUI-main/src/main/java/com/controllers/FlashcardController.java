package com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.*;
import com.spanglishjourney.App;
import com.narration.Narriator;

public class FlashcardController implements Initializable {

    @FXML
    private Label definition;

    @FXML
    private Label word;

    @FXML
    private Label knownWord;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private Course currentCourse;
    private ArrayList<Flashcard> flashcards;
    private WordList wordList;
    private Word wordOBJ;

    private int index;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lsf = LanguageSystemFacade.getInstance();
        currentCourse = lsf.getCurrentUser().getCourses().get(0);
        wordList = lsf.getWordList();
        lsf.setCurrentLesson(currentCourse.getModules().get(0).getLessons().get(0));
        flashcards = lsf.getLessonFlashcards(lsf.getCurrentLesson());

        index = 0;

        getWord(flashcards.get(index).getWord());

        word.setText(flashcards.get(index).getWord());
        knownWord.setText(wordOBJ.getWordInOtherLanguage());
        definition.setText(flashcards.get(index).getDefinition());
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
    void goToSplashPage(MouseEvent event) throws IOException {
        App.setRoot("splashpage");
    }

    @FXML
    void goToNext(MouseEvent event) {
        index++;
        getWord(flashcards.get(index).getWord());
        word.setText(flashcards.get(index).getWord());
        knownWord.setText(wordOBJ.getWordInOtherLanguage());
        definition.setText(flashcards.get(index).getDefinition());
    }

    @FXML
    void goToPrev(MouseEvent event) {
        index--;
        getWord(flashcards.get(index).getWord());
        word.setText(flashcards.get(index).getWord());
        knownWord.setText(wordOBJ.getWordInOtherLanguage());
        definition.setText(flashcards.get(index).getDefinition());
    }

    @FXML
    void playSound(MouseEvent event) {
        Narriator.playSound(word.getText());
        Narriator.playSound(definition.getText());
    }

    private Word getWord(String searchWord) {
        for (int i = 0; i < wordList.getWords().size(); i++) {
            if (wordList.getWords().get(i).getWord().equals(searchWord)) {
                wordOBJ = wordList.getWords().get(i);
            }
        }
        return null;
    }
}
