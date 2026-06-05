/*
 * Christian Biermann
 */

import java.util.Scanner;
import java.io.*;

public class VideoGameDatabase {
    public static VideoGameList vgList = new VideoGameList();
    public static final int HEADERS_AMT = 2;
    public static String console = "";

    public static void main(String[] args) {
        System.out.println("Welcome to the Video Game Database");
        while (true) {
            printOptions();
            Scanner in = new Scanner(System.in);
            int userInput = in.nextInt();
            System.out.println();
            switch (userInput) {
                case 0:
                    input00();
                    break;

                case 1:
                    input01();
                    break;

                case 2:
                    input02();
                    break;

                case 3:
                    input03();
                    break;

                case 4:
                    input04();
                    break;

                default:
                    System.out.println("Incorrect Value, Select a number 0-4\n");
                    break;
            }
        }
    }

    // Exits the program if user inputs 0
    public static void input00() {
        System.out.println("Program exiting...");
        System.exit(0);
    }

    // Reads a file and adds the name and console to the Linked List
    public static void input01() {
        System.out.println("Enter the file name");
        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();
        try {
            Scanner fileReader = new Scanner(new File(fileName));
            while (fileReader.hasNextLine()) {
                String fileLine = fileReader.nextLine();
                String[] columns = fileLine.split("\t");

                // Checks the format of the file
                if (columns.length != HEADERS_AMT) {
                    continue;
                }
                String gameName = columns[0];
                String gameConsole = columns[1];
                VideoGame vg = new VideoGame(gameName, gameConsole);
                vgList.addVideoGame(vg);
            }
            fileReader.close();
            System.out.println("Database Loaded");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Has the user input key words for the VideoGame name and console and searches
    // the database for matching key words
    public static void input02() {
        Scanner in = new Scanner(System.in);

        // Checks if there is a file loaded or not
        if (vgList.isEmpty()) {
            System.out.println("You must load a file first before searching.");
        } else {

            // Asks for the key words
            System.out.println("Enter the name of the game or '*' to show all games.");
            String nameInput = in.nextLine();
            System.out.println("Enter the name of the console or '*' to show all consoles.");
            String consoleInput = in.nextLine();

            // Creates a new VideoGame with the keywords
            VideoGame vg = new VideoGame(nameInput, consoleInput);

            // If both inputs is * then print everything in the List
            if (nameInput.equals("*") && consoleInput.equals("*")) {
                System.out.println();
                console = processMethod(() -> vgList.showVideoGames()); // Assigns console output to a string for File
                                                                        // I/O
                System.out.println(console);

                // If the key word for name is * and not for console name, then prints every
                // VideoGame that is on said console
            } else if (nameInput.equals("*")) {
                System.out.println();
                console = processMethod(() -> vgList.showAllNames(vg));
                System.out.println(console);

                // If the key word for console is * and not for name, then prints every
                // VideoGame with the key word for Name and all consoles it is on
            } else if (consoleInput.equals("*")) {
                System.out.println();
                console = processMethod(() -> vgList.showAllConsoles(vg));
                System.out.println(console);

                // Checks if user key words are in the list, and prints VideoGame name and
                // console
            } else {
                console = processMethod(() -> vgList.contains(vg));
                System.out.println(console);
            }
        }
        System.out.println();
    }

    // Prints the currents results (Variable console) to the console.
    public static void input03() {
        if (vgList.isEmpty()) {
            System.out.println("You must load a file first before searching.");
        } else
            System.out.println("Printing Current Results To Console: \n");
        System.out.println(console);
        System.out.println();
    }

    // Prints the current results (Variable console) to a file (Either overwriting
    // the file or appending the file)
    public static void input04() {
        Scanner in = new Scanner(System.in);
        if (vgList.isEmpty()) {
            System.out.println("You must load a file first before searching.");
        } else {
            try {
                System.out.print("Enter the file name: \n");
                String fileName = in.nextLine();
                System.out.println("Would you like to append the file? (yes/y or no/n)");
                String userInput = in.nextLine();

                // Appends the results to the file
                if (userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
                    PrintWriter printer = new PrintWriter(new FileOutputStream(new File(fileName), true));
                    printer.write(console);
                    printer.close();
                    System.out.println();
                    System.out.println("File: " + fileName + " has been appended.");

                    // Overwrites the results to the file
                } else if (userInput.equalsIgnoreCase("no") || userInput.equalsIgnoreCase("n")) {
                    PrintWriter printer = new PrintWriter(new FileOutputStream(new File(fileName)));
                    printer.write(console);
                    printer.close();
                    System.out.println();
                    System.out.print("File: " + fileName + " has been written.\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Prints greeting
    public static void printOptions() {
        System.out.println(
                "Enter 1 to load the video game database\n" +
                        "Enter 2 to search the database\n" +
                        "Enter 3 to print current results to the console\n" +
                        "Enter 4 to print current results to a file\n" +
                        "Enter 0 to quit");
    }

    // Gets the console's output and assigns it to a string for when using search
    // command to consoleOutput for File I/O uses
    public static String processMethod(Runnable object) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream print = new PrintStream(stream);
        PrintStream old = System.out;
        System.setOut(print);
        object.run();
        System.setOut(old);
        String consoleOutput = stream.toString();
        return consoleOutput;
    }
}
