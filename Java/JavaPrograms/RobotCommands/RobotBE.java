
/*
 * Christian Biermann
 */
import java.util.Scanner;
import java.io.*;

public class RobotBE {
    Scanner boardReader, commandReader;
    char[][] boardCharacters = new char[10][10];
    char player = 'O';
    char replace = '_';
    LLQueue<String> queue;

    // Reads the board and stores it in a 2-D Char array of rows and columns
    public void readBoard(String fileName) {
        try {
            boardReader = new Scanner(new File(fileName));
            while (boardReader.hasNextLine()) {
                for (int i = 0; i < 10; i++) {
                    String board = boardReader.nextLine();
                    for (int j = 0; j < boardCharacters.length; j++) {
                        boardCharacters[i][j] = board.charAt(j);
                    }
                }
            }
            boardReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // reads the commands file and stores it in a queue
    public void queueCommands(String fileName) {
        try {
            queue = new LLQueue<String>();
            commandReader = new Scanner(new File(fileName));
            while (commandReader.hasNextLine()) {
                String commandLine = commandReader.nextLine();
                queue.enqueue(commandLine);
            }
            commandReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Removes and reads the command and moves the robot
    public void readCommands() {
        int indexR = 0, indexC = 0;
        String command;
        int commandCounter = 0;
        boardCharacters[0][0] = player;
        printBoard();
        System.out.println("Simulation Begins");
        while (queue.size() > 0) {
            System.out.println("Command: " + commandCounter);
            boardCharacters[indexR][indexC] = replace;
            command = queue.dequeue();
            // Switch statement to check move command
            switch (command) {
                case "Move Right":
                    // Checks if the character that the robot is moving to is an 'X' | Done for all
                    // move commands
                    if (checkCollision(indexR, indexC + 1)) {
                        System.out.println("CRASH!!!");
                        queue.clearQueue(); // Clears the queue, so no further command is done
                    } else {
                        boardCharacters[indexR][++indexC] = player;
                        printBoard();
                    }
                    break;

                case "Move Left":
                    if (checkCollision(indexR, indexC - 1)) {
                        System.out.println("CRASH!!!");
                        queue.clearQueue();
                    } else {
                        boardCharacters[indexR][--indexC] = player;
                        printBoard();
                    }
                    break;

                case "Move Up":
                    if (checkCollision(indexR - 1, indexC)) {
                        System.out.println("CRASH!!!");
                        queue.clearQueue();
                    } else {
                        boardCharacters[--indexR][indexC] = player;
                        printBoard();
                    }
                    break;

                case "Move Down":
                    if (checkCollision(indexR + 1, indexC)) {
                        System.out.println("CRASH!!!");
                        queue.clearQueue();
                    } else {
                        boardCharacters[++indexR][indexC] = player;
                        printBoard();
                    }
                    break;

                default:
                    System.out.println("Incorrect Command");
                    break;
            }
            commandCounter++;
        }
        System.out.println("Simulation End");
    }

    // Checks if the robot lands on an 'X'
    public boolean checkCollision(int r, int c) {
        if (boardCharacters[r][c] == 'X')
            return true;
        else
            return false;
    }

    // Prints the board
    public void printBoard() {
        for (char[] row : boardCharacters) {
            for (char col : row) {
                System.out.print(col);
            }
            System.out.println();
        }
    }

    // Prints the Queue
    public void printQueue() {
        queue.print();
    }

}
