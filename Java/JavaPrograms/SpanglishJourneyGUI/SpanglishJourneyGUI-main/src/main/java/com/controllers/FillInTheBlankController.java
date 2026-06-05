package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.input.MouseEvent;

public class FillInTheBlankController implements Initializable {

    @FXML
    private Label question;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button speak;

    private LanguageSystemFacade lsf;
    private RegisteredUser currentUser;
    private Course currentCourse;
    private Module currentModule;
    private Lesson currentLesson;
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<String> missedQuestions;
    ArrayList<String> correctQuestions;
    private Question currQuestion;

    private ArrayList<String> spanishWords = new ArrayList<>();
    private ArrayList<String> englishWords = new ArrayList<>();
    private ArrayList<Button> answeredButtons = new ArrayList<>();

    private String userSpanishWord;
    private String userEnglishWords;

    private HashMap<String, String> userSelections = new HashMap<>();
    private HashMap<String, String> correctPairs = new HashMap<>();

    private int index;
    private int selections;
    private int answers;
    private Random random;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        random = new Random();
        lsf = LanguageSystemFacade.getInstance();
        currentUser = lsf.getCurrentUser();
        currentCourse = lsf.getCurrentCourse();
        currentModule = lsf.getCurrentModule();
        currentLesson = lsf.getCurrentLesson();
        questions = lsf.getCurrentLesson().getQuestions().get("Fill in The Blank");

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

    private void setLabels() throws IOException {
        index++;
        currQuestion = questions.get(index);
        question.setText(currQuestion.getQuestion());

        button1.setText(currQuestion.getAnswers().get(0));
        button2.setText(currQuestion.getAnswers().get(1));
        button3.setText(currQuestion.getAnswers().get(2));
        button4.setText(currQuestion.getAnswers().get(3));
    }

    @FXML
    void narrate(MouseEvent event) throws IOException {
        Narriator.playSound(question.getText());
    }

    @FXML
    void submitQuestion(MouseEvent event) throws IOException, InterruptedException {
        currentLesson.addCompletedQuestion(questions.get(index).getQuestion());
        Button b = (Button) event.getSource();
        if (currQuestion.isCorrect((String) ((Button) event.getSource()).getText().trim())) {
            correctQuestions.add(questions.get(0).getQuestion());
            currentModule.addCorrectQuestion(currentLesson.getLessonName(), correctQuestions);
            b.setStyle("-fx-opacity: 1;-fx-background-color: green;");

            goToNext(event);
        } else {
            missedQuestions.add(questions.get(0).getQuestion());
            currentModule.addMissedQuestion(currentLesson.getLessonName(), missedQuestions);
            question.setText("Incorrect");
            b.setStyle("-fx-opacity: 1;-fx-background-color: red;");

            goToNext(event);
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
