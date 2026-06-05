package com.model;

import java.util.ArrayList;
import java.util.HashMap;

public class UI {
    public static void main(String[] args) {
        // scenario1();
        // scenario2();
        // scenario3();
        // scenario4();
    }

    public static void scenario4() {
        LanguageSystemFacade lsf = LanguageSystemFacade.getInstance();
        LeaderBoard lb = lsf.getLeaderBoard();
        lsf.login("LGarcia", "SimplePassword");
        RegisteredUser loggedUser = lsf.getCurrentUser();

        System.out.println(lb.getLeaderboardAmount(3));
    }

    /**
     * Scenario 1
     * A user attempts to create an account with a username that already exists.
     * The system denies the account creation and then the user changes the username
     * to a unique username
     * and creates a new account
     */
    public static void scenario1() {
        LanguageSystemFacade lsf = new LanguageSystemFacade();
        // Creates an account with an existing username
        lsf.createAccount("Liliana", "Blanca", "ABlanca", "TheBestPassword", "LBlanca@gmail.com");

        // Creates an account with a new username
        lsf.createAccount("Liliana", "Blanca", "LBlanca", "TheBestPassword", "LBlanca@gmail.com");
        System.out.println("Lilana Blanca has created a new account.");
        lsf.logout();
        RegisteredUser loggedUser = lsf.login("LBlanca", "TheBestPassword");
        if (loggedUser != null) {
            System.out.println(loggedUser.getUsername() + " has logged in.");
        }
        System.out.println("Select a Course: \nSpanish\nEnglish\nFrench");
        String language = "English";
        System.out.println(language + " has been selected.\n");
        Course newCourse = new Course(language);
        loggedUser.addCourse(newCourse);
        lsf.logout();
    }

    /**
     * Scenario 2
     * The newly created user logs in and creates a new English course. The user
     * then goes into a module and lesson and view some flashcards.
     * Then the user uses the Narration to read a flashcard aloud. The user then
     * answers 5 questions and get 3 right. The system then updates
     * the user's course information such as the score and progress and updates the
     * course and user JSON. The user views the missed questions
     * and then logs out.
     */
    public static void scenario2() {
        LanguageSystemFacade lsf = new LanguageSystemFacade();
        ArrayList<Course> courseList = lsf.getCourseList().getCourses();
        ArrayList<Word> words = lsf.getWordList().getWords();

        // Logs in with the newly created user
        RegisteredUser loggedUser = lsf.login("LBlanca", "TheBestPassword");
        String username = loggedUser.getUsername();
        if (loggedUser != null) {
            System.out.println(loggedUser.getUsername() + " has logged in.");
            System.out.println();
        }
        lsf.setCurrentUser(loggedUser);
        lsf.setCurrentCourse(loggedUser.getCourses().get(0));

        // Prints the modules in the course
        System.out.println(username + "'s Modules: ");
        lsf.printCourseModules();
        System.out.println();
        System.out.println(loggedUser.getUsername() + " goes into module: "
                + lsf.getCurrentCourse().getModule("Basic English Essentials").getName());
        Module currentModule = lsf.getCurrentCourse().getModule("Basic English Essentials");

        // Prints the lessons
        System.out.println(currentModule.getName() + "'s Lessons: ");
        lsf.printModuleLessons(currentModule.getName());
        System.out.println();

        System.out.println(loggedUser.getUsername() + " has selected lesson: "
                + currentModule.getLesson("Greetings and Introductions").getLessonName());
        Lesson currentLesson = currentModule.getLesson("Greetings and Introductions");
        System.out.println();

        // Views the lesson's flashcards
        System.out.println(username + "decides to view the lesson's flashcards\n");
        ArrayList<Flashcard> flashcards = lsf.getLessonFlashcards(currentLesson);
        System.out.println("Flashcards");
        for (int i = 0; i < flashcards.size(); i++) {
            System.out.println(flashcards.get(i).toString());
            System.out.println();
        }

        // Makes first use of the narrator
        System.out.println(loggedUser.getUsername() + " chooses to hear how to say Good morning");
        // Narriator.playSound(flashcards.get(3).getWord());
        // Narriator.playSound(flashcards.get(3).getDefinition());
        System.out.println();

        System.out.println(loggedUser.getUsername() + " chooses to answer some questions.");
        HashMap<String, ArrayList<Question>> questions = currentLesson.getQuestions();

        // Creates ArrayLists for each question time
        String userAnswer = "";
        ArrayList<Question> mc = lsf.getQuestions(currentLesson, "MultipleChoice");
        ArrayList<Question> fitb = lsf.getQuestions(currentLesson, "Fill in The Blank");
        ArrayList<Question> sb = lsf.getQuestions(currentLesson, "Sentence Builder");
        ArrayList<Question> wm = lsf.getQuestions(currentLesson, "WordMatchGame");
        ArrayList<String> correctQuestions = new ArrayList<>();
        ArrayList<String> missedQuestions = new ArrayList<>();

        // First multiple choice question
        System.out.println("Question: " + (1) + ": " + mc.get(0));
        // Narriator.playSound(mc.get(0).getQuestion());
        userAnswer = mc.get(0).getAnswers().get(2);
        System.out.println(loggedUser.getUsername() + "'s Answer: " + userAnswer);
        System.out.println("Correct Answer: " + mc.get(0).getCorrectAnswer());
        if (mc.get(0).isCorrect(userAnswer)) {
            correctQuestions.add(mc.get(0).getQuestion());
        } else {
            missedQuestions.add(mc.get(0).getQuestion());
        }
        System.out.println();

        // Second multiple choice question
        System.out.println("Question: " + (2) + ": " + mc.get(1));
        // Narriator.playSound(mc.get(1).getQuestion());
        userAnswer = mc.get(1).getAnswers().get(0);
        System.out.println(loggedUser.getUsername() + "'s Answer: " + userAnswer);
        System.out.println("Correct Answer: " + mc.get(1).getCorrectAnswer());
        if (mc.get(1).isCorrect(userAnswer)) {
            correctQuestions.add(mc.get(1).getQuestion());
        } else {
            missedQuestions.add(mc.get(1).getQuestion());
        }
        System.out.println();

        // Fill in the blank question
        System.out.println("Question: " + (3) + ": " + fitb.get(0));
        // Narriator.playSound(fitb.get(0).getQuestion());
        userAnswer = fitb.get(0).getAnswers().get(1);
        System.out.println(loggedUser.getUsername() + "'s Answer: " + userAnswer);
        System.out.println("Correct Answer: " + fitb.get(0).getCorrectAnswer());
        if (fitb.get(0).isCorrect(userAnswer)) {
            correctQuestions.add(fitb.get(0).getQuestion());
        } else {
            missedQuestions.add(fitb.get(0).getQuestion());
        }
        System.out.println();

        // Word Match Game question
        HashMap<String, String> userAnswers = new HashMap<>();
        System.out.println("Question: " + (4) + ": " + wm.get(0));
        WordMatchGame wmg = (WordMatchGame) wm.get(0);
        userAnswers.put("¿Cómo estás?", "How are you?");
        userAnswers.put("Hasta luego", "See you later");
        userAnswers.put("Encantado de conocerte", "Nice to meet you");
        userAnswers.put("Que tengas un buen día", "Have a great day");
        System.out.println();
        System.out.println(loggedUser.getUsername() + "'s Answer: " + userAnswers);
        System.out.println("Correct Answer: " + wmg.getCorrectPairs());
        if (wmg.isCorrect(userAnswer)) {
            correctQuestions.add(wmg.getQuestion());
        } else {
            missedQuestions.add(wmg.getQuestion());
        }
        System.out.println();

        // Sentence Builder question
        lsf.shuffleAnswers(sb.get(0).getAnswers());
        System.out.println("Question: " + (5) + ": " + sb.get(0));
        userAnswer = "It's nice so see to you again!";
        System.out.println(loggedUser.getUsername() + "'s Answer: " + userAnswer);
        System.out.println("Correct Answer: " + sb.get(0).getCorrectAnswer());
        if (sb.get(0).isCorrect(userAnswer)) {
            correctQuestions.add(sb.get(0).getQuestion());
        } else {
            missedQuestions.add(sb.get(0).getQuestion());
        }
        System.out.println();

        lsf.addCorrectQuestion(currentModule, currentLesson.getLessonName(), correctQuestions);
        lsf.addMissedQuestion(currentModule, currentLesson.getLessonName(), missedQuestions);

        // Updates the Lesson, Module, and User's progress and score
        lsf.updateProgressAndScore(currentModule, currentLesson);
        System.out.println(
                lsf.getCurrentCourse().getLanguage() + "'s Progress Updated: " + lsf.getCurrentCourse().getProgress());
        System.out.println(currentLesson.getLessonName() + "'s Progress Updated: " + currentLesson.getProgress());
        System.out.println(currentModule.getName() + "'s Score Updated: " + currentModule.getScore());
        System.out.println(currentModule.getName() + "'s Progress Updated: " + currentModule.getProgress());
        System.out.println();

        // Prints the current module name and the missed questions from each lesson
        System.out.println("Current module: " + currentModule.getName());
        System.out.println(loggedUser.getUsername() + "'s Missed Questions: ");
        for (HashMap.Entry<String, ArrayList<String>> entry : currentModule.getMissedQuestions().entrySet()) {
            System.out.println("Lesson Name: " + entry.getKey() + ":\n" + "Questions: " + entry.getValue() + "\n");
        }

        // User logs out and sets the progress
        System.out.println();
        System.out.println(loggedUser.getUsername() + " has logged out.");

        lsf.logout();
    }

    /**
     * Scenario 3
     * An already existing user logs in and checks their progress and score in a
     * module. Then the user see what question he missed
     * and uses flashcards to help solve his question. The user then attempts the
     * question and gets it right and the system updates the Course json file.
     * The user no longer has any missed questions and their score is updated. The
     * user then logs out.
     */
    public static void scenario3() {
        LanguageSystemFacade lsf = new LanguageSystemFacade();
        ArrayList<Course> courseList = lsf.getCourseList().getCourses();

        // Existing user logs in
        RegisteredUser loggedUser = lsf.login("LGarcia", "SimplePassword");
        lsf.setCurrentUser(loggedUser);
        String username = loggedUser.getUsername();

        // Gets the user's course
        lsf.setCurrentCourse(loggedUser.getCourses().get(0));
        if (loggedUser != null) {
            System.out.println(loggedUser.getUsername() + " has logged in.");
            System.out.println();
        }

        // User views his progress
        System.out.println(username + " views his progress");
        System.out.println("Course Progress: " + lsf.getCurrentCourse().getProgress());

        // Get the current module and lesson
        Module currentModule = lsf.getCurrentCourse().getModule("Basic English Essentials");
        Lesson currentLesson = lsf.getCurrentCourse().getModule("Basic English Essentials")
                .getLesson("Greetings and Introductions");

        // Views the module's score
        System.out.println(currentModule.getName() + " Score: " + currentModule.getScore());
        System.out.println("Lesson: " + currentLesson.getLessonName() + " \nMissed Questions \n"
                + currentModule.getMissedQuestions());
        System.out.println();

        // User reviews the missed questions in the module
        System.out.println(username + " chooses to review the question he missed.");
        System.out.println("Question: " + currentModule.getMissedQuestions().get(currentLesson.getLessonName()));

        // User uses a flashcard to help solve the question
        System.out.println(username + " chooses to use a flashcard to help solve the problem.\n");
        ArrayList<Flashcard> flashcards = lsf.getLessonFlashcards(currentLesson);
        Flashcard hello = lsf.getSpecificFlashcard(flashcards, "Hello");
        System.out.println("Flashcard: \n" + hello.getWord() + " - " + hello.getDefinition());

        ArrayList<String> correctQuestions = currentModule.getCorrectQuestions().get(currentLesson.getLessonName());
        ArrayList<String> missedQuestions = currentModule.getMissedQuestions().get(currentLesson.getLessonName());

        // User solves the missed question
        System.out.println(username + " attemps to solve the missed question.\n");
        HashMap<String, ArrayList<Question>> questions = currentLesson.getQuestions();
        ArrayList<Question> mc = lsf.getQuestions(currentLesson, "MultipleChoice");

        String userAnswer = "";
        System.out.println("Question: " + (1) + ": " + mc.get(0));
        // Narriator.playSound(mc.getQuestion());
        userAnswer = mc.get(0).getAnswers().get(2);
        System.out.println(loggedUser.getUsername() + "'s Answer: " + userAnswer);
        System.out.println("Correct Answer: " + mc.get(0).getCorrectAnswer());
        if (mc.get(0).isCorrect(userAnswer)) {
            correctQuestions.add(mc.get(0).getQuestion());
            if (missedQuestions.contains(mc.get(0).getQuestion())) {
                missedQuestions.remove(mc.get(0).getQuestion());
            }
        } else {
            if (missedQuestions.contains(mc.get(0).getQuestion())) {
                return;
            }
            missedQuestions.add(mc.get(0).getQuestion());
        }
        System.out.println();

        // Adds all already completed questions to the correct questions to solve any
        // incosistencies with the module's score
        lsf.addCorrectQuestion(currentModule, currentLesson.getLessonName(), correctQuestions);
        lsf.addMissedQuestion(currentModule, currentLesson.getLessonName(), missedQuestions);

        // User views that the missed questions is no longer there
        System.out.println("Lesson: " + currentLesson.getLessonName() + " \nMissed Questions \n"
                + currentModule.getMissedQuestions());

        // Updates the modules, lesson, and user's score and the user logs out
        lsf.updateProgressAndScore(currentModule, currentLesson);
        lsf.logout();
    }
}