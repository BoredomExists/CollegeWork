package Homework03;

import java.util.Scanner;
import java.io.*;

public class TaskLLFE {
    public static TaskLL[] organizedTask;
    final public static int HEADERS_AMT = 2;
    public static String console = "";
    public static Scanner intInput;
    public static Scanner strInput;

    public static void main(String[] args) {
        organizedTask = new TaskLL[5];
        for (int i = 0; i < organizedTask.length; i++) {
            organizedTask[i] = new TaskLL();
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the Task Organizer!");
        while (true) {
            printOpening();
            switch (in.nextInt()) {
                case 9:
                    quit();
                    break;

                case 1:
                    case01();
                    break;

                case 2:
                    case02();
                    break;

                case 3:
                    case03();
                    break;

                case 4:
                    case04();
                    break;

                case 5:
                    case05();
                    break;
                default:
                    System.out.println("Incorrect Value. Enter a Value 1-5 or 9");
                    break;
            }
        }
    }

    //Loads a task file, if file already loaded, then adds a task to the list.
    public static void case01() {
        intInput = new Scanner(System.in);
        strInput = new Scanner(System.in);
        if (organizedTask[0].isEmpty()) {
            System.out.println("Enter the File Name: ");
            String fileName = intInput.nextLine();
            try {
                Scanner fileReader = new Scanner(new File(fileName));
                while (fileReader.hasNextLine()) {
                    String fileLine = fileReader.nextLine();
                    String[] columns = fileLine.split("\t");
                    if (columns.length != HEADERS_AMT)
                        continue;
                    int priorityValue = Integer.parseInt(columns[0]);
                    String taskName = columns[1];
                    Task T = new Task(taskName, priorityValue);
                    switch (priorityValue) { //Checks the priority value and assigns Tasks to certain list in array
                        case 0:
                            organizedTask[0].addTask(T);
                            break;

                        case 1:
                            organizedTask[1].addTask(T);
                            break;

                        case 2:
                            organizedTask[2].addTask(T);
                            break;

                        case 3:
                            organizedTask[3].addTask(T);
                            break;

                        case 4:
                            organizedTask[4].addTask(T);
                            break;

                        default:
                            break;
                    }
                }
                fileReader.close();
                System.out.println("File Loaded");
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Enter the Task's Priorty: ");
            int pValue = intInput.nextInt();
            System.out.println("Enter the Task's Action: ");
            String tAction = strInput.nextLine();
            Task T = new Task(tAction, pValue);
            switch (pValue) { //Checks priority value and assigns Task to certain List
                case 0:
                    organizedTask[0].addTask(T);
                    break;

                case 1:
                    organizedTask[1].addTask(T);
                    break;

                case 2:
                    organizedTask[2].addTask(T);
                    break;

                case 3:
                    organizedTask[3].addTask(T);
                    break;

                case 4:
                    organizedTask[4].addTask(T);
                    break;

                default:
                    break;
            }
        }
        System.out.println();
    }

    //Removes a task from the list
    public static void case02() {
        System.out.println("Enter the Task's Priority: ");
        int priority = intInput.nextInt();
        System.out.println("Enter the Task's Action: ");
        String action = strInput.nextLine();
        Task T = new Task(action, priority);

        for (int i = 0; i < organizedTask.length; i++) {
            if(organizedTask[i].contains(T))
            {
                organizedTask[i].removeTask();
            }
        }
    }

    //Reads the List to the console and saves the console output for File I/O use
    public static void case03() {
        for (int i = 0; i < organizedTask.length; i++) {
            organizedTask[i].printList();
        }
        console = processMethod(() -> forConsole()); //Saving the console output to a string
        System.out.println();
    }

    //Reads the task from a file.
    public static void case04() {
        try {
            System.out.println("Enter the file name: ");
            String fileName = strInput.nextLine();
            Scanner fileReader = new Scanner(new File(fileName));
            while(fileReader.hasNextLine())
            {
                System.out.println(fileReader.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Writes the current List to a file
    public static void case05() {
        try {
            System.out.println("Enter the file name: ");
            String fileName = strInput.nextLine();
            PrintWriter printer = new PrintWriter(new FileOutputStream(new File(fileName)));
            printer.write(console);
            printer.close();
            System.out.println();
            System.out.print("File: " + fileName + " has been written.\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void printOpening() {
        System.out.println(
                "Enter 1. To Add a Task\n" +
                        "Enter 2. To Remove a Task\n" +
                        "Enter 3. To Print Tasks To Console\n" +
                        "Enter 4. To Read from a Task File\n" +
                        "Enter 5. To Write to a Task File\n" +
                        "Enter 9. To Quit");
    }

    public static void quit() {
        System.exit(0);
    }

    //Method to get Console output as a string
    public static void forConsole()
    {
        for (int i = 0; i < organizedTask.length; i++) {
            organizedTask[i].printList();
        }
        System.out.println();
    }

    //Gets the Consoles Output and returns it as a string
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
