/*
 * Christian Biermann
 */
import java.util.Scanner;

public class RobotFE {
    public static void main(String[] args) {
        RobotBE commands = new RobotBE();
        Scanner boardIn = new Scanner(System.in);
        Scanner commandIn = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        String boardInput, commandInput, againInput;
        boolean again = true;

        System.out.println("Welcome to the Robot Simulator");

        while (again) {
            again = false;
            System.out.println("Enter the file for the Board");
            boardInput = boardIn.nextLine();
            System.out.println("Enter the file for the Robot Commands");
            commandInput = commandIn.nextLine();

            commands.readBoard(boardInput);
            System.out.println();
            commands.queueCommands(commandInput);
            System.out.println();
            commands.readCommands();
            System.out.println();

            System.out.println("Would you like to play again (Y/N)");
            againInput = in.nextLine();
            if (againInput.equalsIgnoreCase("Y")) {
                again = true;
            } else
                again = false;
        }

    }
}
