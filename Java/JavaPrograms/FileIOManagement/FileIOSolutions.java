/*
 * Christian Biermann
 */

import java.util.*;
import java.io.*;

public class FileIOSolutions {
    static Scanner fileReader;
    static PrintWriter printer;

    // Method that reads the text file and changes every instance of is to was.
    public static void pastTense(String inputFile, String outputFile) {
        try {
            // Scanner to read the file and PrintWriter to write to a new file
            fileReader = new Scanner(new File(inputFile));
            printer = new PrintWriter(new FileOutputStream(new File(outputFile)));

            // Loop continues as long as the file can read the next word. if null, loop ends
            while (fileReader.hasNext()) {
                String isFound = fileReader.next();

                // if "is" is found, then replace it with was.
                if (isFound.equalsIgnoreCase("is"))
                    isFound = isFound.replace(isFound, "was");
                System.out.println(isFound + " ");

                // writes to a new file of the replace words
                printer.write(isFound + " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        fileReader.close();
        printer.close();
    }

    // Reads the radius and height columns and calculates the volumes of them and
    // returns it
    public static double totalTubeVolume(String fileName) {
        double volume = 0.0;

        try {
            fileReader = new Scanner(new File(fileName));
            while (fileReader.hasNextLine()) {
                String splitStr = fileReader.nextLine();
                String[] tubeColumns = splitStr.split("\t");

                // Variables for the radius and height index of tubecolumns
                int radiusColumn = 1;
                int heightColumn = 2;

                if (radiusColumn >= 0 && radiusColumn < tubeColumns.length) {
                    double radiusValue = Double.parseDouble(tubeColumns[radiusColumn]);
                    double heightValue = Double.parseDouble(tubeColumns[heightColumn]);

                    // Calulate the volume of each tube and adds it together
                    volume += Math.pow(radiusValue, 2) * Math.PI * heightValue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return volume;
    }
}
