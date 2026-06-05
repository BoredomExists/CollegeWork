/*
 * Christian Biermann
 */
import java.util.Scanner;

public class ShowcaseDriver {

    public static void main(String[] args) {
        ShowcaseMethods methods = new ShowcaseMethods();
        Scanner guessInput = new Scanner(System.in);
        Scanner userLoops = new Scanner(System.in);
        int userGuess;
        String userInput;
        boolean goAgain = true;

        // Start of program
        System.out.println("Welcome to Showcase Showdown!\n"
                + "You must guess the total cost of the prizes without going over and within $1,300 of its actual price.");
        System.out.println();

        // Loops so multiple games can be played
        while (goAgain) {
            goAgain = false;
            System.out.println("Here are the list of prizes: ");
            methods.showPrizes(); // Lists the 5 unique prizes
            System.out.println();
            System.out.println("Enter your guess: "); // User guesses the total value of prizes
            userGuess = guessInput.nextInt();
            methods.comparePrices(userGuess); // Checks if the user guessed the total amount or came close
            System.out.println();
            System.out.println("Would you like to play again. Enter (y/n)"); // Input to start the loop again
            userInput = userLoops.nextLine();
            if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("yes")) {
                goAgain = true;
            } else if (userInput.equalsIgnoreCase("n") || userInput.equalsIgnoreCase("no")) {
                System.out.println("Program Closing....");
                System.exit(0);
            }

        }
    }
}
