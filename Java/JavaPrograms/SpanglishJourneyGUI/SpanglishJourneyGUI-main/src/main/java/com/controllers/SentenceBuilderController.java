package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Random;

import com.model.*;
import com.model.Module;
import com.spanglishjourney.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class SentenceBuilderController implements Initializable {

    @FXML
    private Label answer_label;

    @FXML
    private FlowPane buttoncontainer;

    @FXML
    private Pane pane_ID;

    @FXML
    private Label question;

    @FXML
    private Button speak;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private Course currentCourse;
    private Module currentModule;
    private Lesson currentLesson;
    ArrayList<Question> questions;
    ArrayList<String> answers;
    ArrayList<String> correctQuestions;
    ArrayList<String> missedQuestions;
    ArrayList<Button> answerButtons;

    private Random random;
    private String answer;
    private int index;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        random = new Random();
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        currentCourse = lsf.getCurrentCourse();
        currentModule = lsf.getCurrentModule();
        currentLesson = lsf.getCurrentLesson();
        questions = lsf.getCurrentLesson().getQuestions().get("Sentence Builder");
        answers = questions.get(0).getAnswers();
        answerButtons = new ArrayList<>();

        correctQuestions = currentModule.getCorrectQuestions().get(currentLesson.getLessonName());
        missedQuestions = currentModule.getMissedQuestions().get(currentLesson.getLessonName());

        if (correctQuestions == null) {
            correctQuestions = new ArrayList<>();
        }
        if (missedQuestions == null) {
            missedQuestions = new ArrayList<>();
        }

        answer = "";
        answer_label.setText("_____");
        answer_label.setWrapText(true);
        index = -1;

        // Ensure the button container is cleared initially
        buttoncontainer.getChildren().clear();
        answerButtons.clear();

        try {
            setLabels();
        } catch (Exception e) {
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
    void resetLabel(MouseEvent event) {
        answer = "";
        answer_label.setText("______");
        // Optionally reset button states
        for (Button button : answerButtons) {
            button.setDisable(false);
        }
    }

    @FXML
    void narrate(MouseEvent event) throws IOException {
        Narriator.playSound(question.getText());
    }

    @FXML
    void addToAnswer(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        answer += clickedButton.getText() + " ";
        answer_label.setText(answer);
    }

    @FXML
    void checkAnswer(ActionEvent event) {
        currentLesson.addCompletedQuestion(questions.get(0).getQuestion());
        if (questions.get(0).isCorrect(answer_label.getText())) {
            correctQuestions.add(questions.get(0).getQuestion());
            currentModule.addCorrectQuestion(currentLesson.getLessonName(), correctQuestions);
        } else {
            missedQuestions.add(questions.get(0).getQuestion());
            currentModule.addMissedQuestion(currentLesson.getLessonName(), missedQuestions);
        }

        // Disable all buttons after checking the answer
        for (Button button : answerButtons) {
            button.setDisable(true);
        }
    }

    @FXML
    void goToNextQuestion(MouseEvent event) throws IOException {
        goToNext(event);
    }

    public void setLabels() {
        // Increment the index for the next question
        index++;
    
        // Ensure the button container and answer buttons are cleared
        buttoncontainer.getChildren().clear();
        answerButtons.clear();
    
        // Set the question text
        question.setText(questions.get(index).getQuestion());
    
        // Get answers from the current question
        answers = new ArrayList<>(questions.get(index).getAnswers());
    
        // Shuffle answers using LanguageSystemFacade's shuffleAnswers method
        lsf.shuffleAnswers(answers);
    
        // Create buttons dynamically for the shuffled answers
        for (String answer : answers) {
            Button answerButton = new Button(answer);
            answerButton.setOnAction(event -> addToAnswer(answer));
            answerButtons.add(answerButton);
            buttoncontainer.getChildren().add(answerButton);
        }
    
        // Reset the answer label
        answer = "";
        answer_label.setText("_____");
    }

    public void addToAnswer(String selectedAnswer) {
        // Append the selected answer to the current answer string with a space
        if (answer.isEmpty()) {
            answer = selectedAnswer; // First word, no space needed
        } else {
            answer += " " + selectedAnswer; // Append with a space
        }
    
        // Update the answer label to show the current state
        answer_label.setText(answer);
    
        // Optionally disable the button to prevent selecting the same answer twice
        for (Button button : answerButtons) {
            if (button.getText().equals(selectedAnswer)) {
                button.setDisable(true);
            }
        }
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
