/*
 * Christian Biermann
 */

import java.util.Scanner;
import java.io.*;

public class ShapeTreeFE {
    static Scanner fileReader, strInput, intInput, traversalInput;
    static LinkedBST sBST;
    public static String console = "";

    public static void main(String[] args) {
        boolean repeat = true;

        System.out.println("Welcome to the Shape Tree.");

        while (repeat) {
            options();
            System.out.println();
            strInput = new Scanner(System.in);
            intInput = new Scanner(System.in);

            /*
             * Cases:
             * 0 - exits program
             * 1 - User inputs a file to fill Shape tree
             * 2 - Prints the Shape Tree in either pre-order, in-order, post-order
             * 3 - Adds a shape to the tree
             * 4 - Removes a shape from the tree
             * 5 - Searches for a shape
             * 6 - Finds the shape with the max area in the tree
             * 7 - Removes all shapes greater than an area
             * 8 - Prints the shape tree to a file
             */

            switch (intInput.nextInt()) {
                case 0:
                    repeat = false;
                    System.out.println("Program Exiting....");
                    System.exit(0);
                    break;

                case 1:
                    System.out.println("Enter the filename: ");
                    String file = strInput.nextLine();
                    fillShapeTree(file);
                    System.out.println("Printing in In-Order Traversal\n");
                    sBST.printInorder();
                    System.out.println();
                    break;

                case 2:
                    printTraversalOptions();
                    traversalInput = new Scanner(System.in);
                    switch (traversalInput.nextInt()) {
                        case 1:
                            sBST.printPreorder();
                            System.out.println();
                            break;

                        case 2:
                            sBST.printInorder();
                            System.out.println();
                            break;

                        case 3:
                            sBST.printPostOrder();
                            System.out.println();
                            break;

                        default:
                            repeat = false;
                    }
                    break;

                case 3:
                    System.out.println("Enter the type of shape to add.");
                    strInput = new Scanner(System.in);
                    intInput = new Scanner(System.in);
                    switch (strInput.nextLine().toLowerCase()) {
                        case "circle":
                            System.out.println("Enter the Radius:");
                            Shape c = new Shape("Circle");
                            c.setRadius(intInput.nextDouble());
                            sBST.add(c);
                            break;

                        case "rectangle":
                            System.out.println("Enter the Length followed by the Width:");
                            Shape r = new Shape("Rectangle");
                            r.setLength(intInput.nextDouble());
                            r.setWidth(intInput.nextDouble());
                            sBST.add(r);
                            break;

                        case "right triangle":
                            System.out.println("Enter the Base followed by the Height:");
                            Shape rt = new Shape("Right Triangle");
                            rt.setBase(intInput.nextDouble());
                            rt.setHeight(intInput.nextDouble());
                            sBST.add(rt);
                            break;

                        default:
                            repeat = false;
                    }

                    System.out.println();
                    break;

                case 4:
                    System.out.println("Enter the type of shape to remove.");
                    strInput = new Scanner(System.in);
                    intInput = new Scanner(System.in);
                    switch (strInput.nextLine().toLowerCase()) {
                        case "circle":
                            System.out.println("Enter the Radius:");
                            Shape c = new Shape("Circle");
                            c.setRadius(intInput.nextDouble());
                            sBST.remove(c);
                            System.out.println("Shape Removed: " + c.toString());
                            break;

                        case "rectangle":
                            System.out.println("Enter the Length followed by the Width:");
                            Shape r = new Shape("Rectangle");
                            r.setLength(intInput.nextDouble());
                            r.setWidth(intInput.nextDouble());
                            sBST.remove(r);
                            System.out.println("Shape Removed: " + r.toString());
                            break;

                        case "right triangle":
                            System.out.println("Enter the Base followed by the Height:");
                            Shape rt = new Shape("Right Triangle");
                            rt.setBase(intInput.nextDouble());
                            rt.setHeight(intInput.nextDouble());
                            sBST.remove(rt);
                            System.out.println("Shape Removed: " + rt.toString());
                            break;

                        default:
                            repeat = false;
                    }
                    System.out.println();
                    break;

                case 5:
                    System.out.println("Enter the type of shape to search.");
                    strInput = new Scanner(System.in);
                    intInput = new Scanner(System.in);
                    switch (strInput.nextLine().toLowerCase()) {
                        case "circle":
                            System.out.println("Enter the Radius:");
                            Shape c = new Shape("Circle");
                            c.setRadius(intInput.nextDouble());
                            System.out.println("Was the shape in the tree? " + sBST.search(c));
                            break;

                        case "rectangle":
                            System.out.println("Enter the Length followed by the Width:");
                            Shape r = new Shape("Rectangle");
                            r.setLength(intInput.nextDouble());
                            r.setWidth(intInput.nextDouble());
                            System.out.println("Was the shape in the tree? " + sBST.search(r));
                            break;

                        case "right triangle":
                            System.out.println("Enter the Base followed by the Height:");
                            Shape rt = new Shape("Right Triangle");
                            rt.setBase(intInput.nextDouble());
                            rt.setHeight(intInput.nextDouble());
                            System.out.println("Was the shape in the tree? " + sBST.search(rt));
                            break;

                        default:
                            repeat = false;
                    }
                    System.out.println();
                    break;

                case 6:
                    System.out.println("The shape with the max area: " + sBST.findMaxInTree());
                    System.out.println();
                    break;

                case 7:
                    System.out.println("Enter the maximum area: ");
                    intInput = new Scanner(System.in);
                    double maxArea = intInput.nextDouble();
                    sBST.deleteMaxArea(maxArea);
                    System.out.println("Shapes with Area of greater than " + maxArea + " have been removed.\n");
                    break;

                case 8:
                    printTraversalOptions();
                    traversalInput = new Scanner(System.in);
                    switch (traversalInput.nextInt()) {
                        case 1:
                            console = processMethod(() -> sBST.printPreorder());
                            break;

                        case 2:
                            console = processMethod(() -> sBST.printInorder());
                            break;

                        case 3:
                            console = processMethod(() -> sBST.printPostOrder());
                            break;

                        default:
                            break;
                    }
                    System.out.println("Enter the file's name: ");
                    strInput = new Scanner(System.in);
                    String fileName = strInput.nextLine();
                    printShapeTreeToFile(fileName);
                    break;
                default:
                    repeat = false;
            }
        }
    }

    public static void options() {
        System.out.println("Enter 1. To Read a Shape Tree from a File.\n" +
                "Enter 2. To Print a Tree Traversal to the Console.\n" +
                "Enter 3. To Add a Shape.\n" +
                "Enter 4. To Remove a Shape.\n" +
                "Enter 5. To Search for a Shape.\n" +
                "Enter 6. To Find the Shape with the Max Area.\n" +
                "Enter 7. To Remove All Shapes Greater than an Area.\n" +
                "Enter 8. To Print Shape Tree to a File.\n" +
                "Enter 0. To Quit.");
    }

    // User inputs a filename that has the shapes and their dimensions and fills the
    // tree.
    public static void fillShapeTree(String fileName) {
        try {
            fileReader = new Scanner(new File(fileName));
            sBST = new LinkedBST();
            while (fileReader.hasNextLine()) {
                String fileLine = fileReader.nextLine();
                String[] columns = fileLine.split("\t");
                switch (columns[0].toLowerCase()) {
                    // If the name of the shape is circle, gets only 2 columns, the name and radius
                    case "circle":
                        if (columns.length != 2)
                            continue;
                        Shape circle = new Shape(columns[0]);
                        circle.setRadius(Double.parseDouble(columns[1]));
                        sBST.add(circle);
                        break;

                    // If the name of the shape is rectangle, gets 3 columns: name, length, and
                    // width
                    case "rectangle":
                        if (columns.length != 3)
                            continue;
                        Shape rectangle = new Shape(columns[0]);
                        rectangle.setLength(Double.parseDouble(columns[1]));
                        rectangle.setWidth(Double.parseDouble(columns[2]));
                        sBST.add(rectangle);
                        break;

                    // If the name is right triangle, gets 3 columns: name, base, height
                    case "right triangle":
                        if (columns.length != 3)
                            continue;
                        Shape rightTriangle = new Shape(columns[0]);
                        rightTriangle.setBase(Double.parseDouble(columns[1]));
                        rightTriangle.setHeight(Double.parseDouble(columns[2]));
                        sBST.add(rightTriangle);
                        break;

                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printTraversalOptions() {
        System.out.println("Enter 1. For Pre-Order.\n" + "Enter 2. For In-Order.\n" + "Enter 3. For Post-Order.\n");
    }

    // Prints the shape tree to a file
    public static void printShapeTreeToFile(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(new File(fileName)));
            writer.write(console);
            writer.close();
            System.out.println("File: " + fileName + " has been written.");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gets the Consoles Output and returns it as a string
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