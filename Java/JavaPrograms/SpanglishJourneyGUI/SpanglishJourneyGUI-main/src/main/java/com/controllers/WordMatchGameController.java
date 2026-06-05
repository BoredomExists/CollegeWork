package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Random;

import com.model.*;
import com.model.Module;
import com.narration.Narriator;
import com.spanglishjourney.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class WordMatchGameController implements Initializable {

    @FXML
    private Label question;

    @FXML
    private Button eng1;

    @FXML
    private Button eng2;

    @FXML
    private Button eng3;

    @FXML
    private Button eng4;

    @FXML
    private Button spa1;

    @FXML
    private Button spa2;

    @FXML
    private Button spa3;

    @FXML
    private Button spa4;

    @FXML
    private Button speak;

    private TextField answer;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private Course currentCourse;
    private Module currentModule;
    private Lesson currentLesson;
    ArrayList<Question> questions;
    ArrayList<String> correctQuestions;
    ArrayList<String> missedQuestions;
    private WordMatchGame currQuestion;

    private ArrayList<String> spanishWords = new ArrayList<>();
    private ArrayList<String> englishWords = new ArrayList<>();
    private ArrayList<Button> answeredButtons = new ArrayList<>();

    private String userSpanishWord;
    private String userEnglishWords;

    private HashMap<String, String> userSelections = new HashMap<>();
    private HashMap<String, String> correctPairs = new HashMap<>();

    Random random;

    private int index;
    private int selections;
    private int answers;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        random = new Random();
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        currentCourse = lsf.getCurrentCourse();
        currentModule = lsf.getCurrentModule();
        currentLesson = lsf.getCurrentLesson();
        questions = lsf.getCurrentLesson().getQuestions().get("WordMatchGame");

        correctQuestions = currentModule.getCorrectQuestions().get(currentLesson.getLessonName());
        missedQuestions = currentModule.getMissedQuestions().get(currentLesson.getLessonName());

        if (correctQuestions == null) {
            correctQuestions = new ArrayList<String>();
        }
        if (missedQuestions == null) {
            missedQuestions = new ArrayList<String>();
        }

        question.setWrapText(true);
        index = -1;

        try {
            setLabels();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    void narrate(MouseEvent event) throws IOException {
        Narriator.playSound(question.getText());
    }

    private void setLabels() throws IOException {
        index++;
        currQuestion = (WordMatchGame) questions.get(index);
        for (String key : currQuestion.getCorrectPairs().keySet()) {
            spanishWords.add(key);
            englishWords.add(currQuestion.getCorrectPairs().get(key));
        }

        Collections.shuffle(spanishWords);
        Collections.shuffle(englishWords);

        question.setText(currQuestion.getQuestion());
        spa1.setText(spanishWords.get(0));
        spa1.setWrapText(true);
        spa2.setText(spanishWords.get(1));
        spa2.setWrapText(true);
        spa3.setText(spanishWords.get(2));
        spa3.setWrapText(true);
        spa4.setText(spanishWords.get(3));
        spa4.setWrapText(true);
        eng1.setText(englishWords.get(0));
        eng1.setWrapText(true);
        eng2.setText(englishWords.get(1));
        eng2.setWrapText(true);
        eng3.setText(englishWords.get(2));
        eng3.setWrapText(true);
        eng4.setText(englishWords.get(3));
        eng4.setWrapText(true);

    }

    @FXML
    void madeSelection(MouseEvent event) throws IOException {
        selections++;
        answeredButtons.add((Button) event.getSource());
        correctPairs = currQuestion.getCorrectPairs();
        String selection = (String) ((Button) event.getSource()).getText().trim();

        if (spanishWords.contains(selection)) {
            userSpanishWord = selection;
        } else {
            userEnglishWords = selection;
        }

        if (selections == 2) {
            userSelections.put(userSpanishWord, selection);
            answers++;
            selections = 0;
            if (correctPairs.get(userSpanishWord) == userEnglishWords) {
                for (Button button : answeredButtons) {
                    button.setDisable(true);
                    button.setStyle("-fx-opacity: 1;-fx-background-color: green;");
                }
            } else {
                for (Button button : answeredButtons) {
                    button.setDisable(true);
                    button.setStyle("-fx-opacity: 1; -fx-background-color: red;");
                }
            }
            answeredButtons.clear();

        }

        if (answers == 4) {
            currentLesson.addCompletedQuestion(questions.get(index).getQuestion());
            if (isCorrect()) {
                correctQuestions.add(questions.get(0).getQuestion());
                currentModule.addCorrectQuestion(currentLesson.getLessonName(), correctQuestions);
            } else {
                missedQuestions.add(questions.get(0).getQuestion());
                currentModule.addMissedQuestion(currentLesson.getLessonName(), missedQuestions);
            }

            if (index > questions.size() - 2) {
                goToNext(event);
            } else {
                setLabels();
            }
        }
    }

    private boolean isCorrect() {
        return currQuestion.isCorrect(userSelections);
    }

    private void goToNext(MouseEvent event) {
        try {
            Lesson currentLesson = lsf.getCurrentLesson();
            ArrayList<Question> allQuestions = currentLesson.getAllQuestions();
            ArrayList<String> completedQuestions = currentLesson.getCompletedQuestions();
    
            // Check if all questions have been answered
            if (completedQuestions.size() == allQuestions.size()) {
                App.setRoot("lessonresults");
                return;
            }
    
            // Iterate to find the first unanswered question
            for (Question question : allQuestions) {
                if (!completedQuestions.contains(question.getQuestion())) {
                    if (question instanceof MultipleChoice) {
                        App.setRoot("multiplechoice");
                    } else if (question instanceof FillinTheBlank) {
                        App.setRoot("fillintheblank");
                    } else if (question instanceof SentenceBuilder) {
                        App.setRoot("sentencebuilder");
                    } else if (question instanceof WordMatchGame) {
                        App.setRoot("wordmatch");
                    }
                    return; // Navigate to the next question
                }
            }
    
            // Fallback if no question is found
            App.setRoot("lessonresults");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
