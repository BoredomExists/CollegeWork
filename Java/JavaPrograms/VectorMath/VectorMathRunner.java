/*
 * Christian Biermann
 */

import java.util.Scanner;

public class VectorMathRunner {

    public static void main(String[] args) {
        VectorMathMethods methods = new VectorMathMethods();
        Scanner in = new Scanner(System.in);
        double[] vector1;
        double[] vector2;
        int optionsInput;
        int vectorAmount;

        System.out.println(
                "Welcome to the Vector Operations Program\nEnter 1. To Add 2 Vectors\nEnter 2. To Subtract 2 Vectors\nEnter 3. To find the Magnitude of a Vector\nEnter 9. To Quit");
        optionsInput = in.nextInt();

        //Makes users input valid only if the input is 1, 2, 3, or 9
        while(optionsInput <= 0 || optionsInput >= 4 && optionsInput != 9)
        {
            System.out.println("Invalid Option, Valid options are 1, 2, 3, or 9.");
            optionsInput = in.nextInt();
        }

        //Exits the program if user enters 9 during selection.
        if (optionsInput == 9) {
            System.out.println("Program Exiting....");
            System.exit(0);
        }

        System.out.println("Enter the size of Vector(s): ");
        vectorAmount = in.nextInt();
        while (vectorAmount <= 0) {
            System.out.println("The sizes of the vector(s) must be greater than 1");
            vectorAmount = in.nextInt();
        }
        vector1 = new double[vectorAmount]; // Initializes both vectors with their size based on the user's input.
        vector2 = new double[vectorAmount];

        

        // if the user's input is either 1 or 2, then values for both vectors are to be
        // inputted, but if 3, then only 1 vector will have values.
        if (optionsInput == 1 || optionsInput == 2) {
            System.out.println("Enter the values for Vector 1: ");
            for (int i = 0; i < vectorAmount; i++) {
                vector1[i] = in.nextDouble();
            }
            System.out.println("Enter the values for Vector 2: ");
            for (int i = 0; i < vectorAmount; i++) {
                vector2[i] = in.nextDouble();
            }
        } else if (optionsInput == 3) {
            System.out.println("Enter the values for the Vector: ");
            for (int i = 0; i < vectorAmount; i++) {
                vector1[i] = in.nextDouble();
            }
        }

        methods.DisplayOutput(vector1, vector2, optionsInput);
    }

}
