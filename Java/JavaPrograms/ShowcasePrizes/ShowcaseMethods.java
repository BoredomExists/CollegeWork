/*
 * Christian Biermann
 */

import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class ShowcaseMethods {
    final private int HEADER_FIELDS = 2;
    private Scanner fileReader;
    private Random random;
    private String fileName = "./prizes.txt";
    private String[] prizeNames = new String[52];
    private String[] prizeValues = new String[52];
    private String[][] selectedPrizes = new String[5][2];

    // Reads the prizes text file and returns 5 unique randomly selected prizes
    public void showPrizes() {
        random = new Random();
        int number;
        int index = 0;
        try {
            fileReader = new Scanner(new File(fileName));
            while (fileReader.hasNextLine()) {

                // Reads the file, splits the columns, and assigns them to their own array;
                String str = fileReader.nextLine();
                String[] prizes = str.split("\t");

                // If the text file format is not correctly formatted, ignore the line
                if (prizes.length == HEADER_FIELDS) {
                    String name = prizes[0];
                    prizeNames[index] = name;

                    String value = prizes[1];
                    prizeValues[index] = value;
                } else {
                    continue;
                }
                index++;
            }
            // Selects 5 random prizes and assigns it to the 2D Array
            for (int i = 0; i < 5; i++) {
                number = random.nextInt(prizeNames.length);
                selectedPrizes[i][0] = prizeNames[number];
                selectedPrizes[i][1] = prizeValues[number];
            }

            // Outputs the prize list after they have been sorted and rerolled.
            DisplayPrizes(RerollArray(prizeNames, SortArray(selectedPrizes, 0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Returns the total value of all the prizes
    public int getPrizeValue(String[][] prizeArray) {
        int answer = 0;
        for (int i = 0; i < prizeArray.length; i++) {
            answer += Integer.parseInt(prizeArray[i][1]);
        }
        return answer;
    }

    // Compares the total values of prizes and what the user guesses
    public void comparePrices(int userGuess) {
        int prizesValue = getPrizeValue(selectedPrizes);
        if (userGuess == prizesValue || userGuess >= (prizesValue - 1300) && userGuess <= (prizesValue + 1300)) {
            System.out.println("The total cost was: " + prizesValue + "\nCongrats, you win!!!");
        } else if (userGuess >= (prizesValue - 1500) && userGuess < (prizesValue - 1300)) {
            System.out.println("The total cost was: " + prizesValue + "\n Close, but not close enough. You Lose.");
        } else {
            System.out.println("The total cost was: " + prizesValue + "\nSorry, You lose.");
        }
    }

    public void DisplayPrizes(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i][0] + "\n");
        }
    }

    // Copies a 2D array
    private String[][] CopyArray(String[][] array) {
        String[][] copiedArray = new String[array.length][array[0].length];
        for (int i = 0; i < copiedArray.length; i++) {
            for (int j = 0; j < copiedArray[0].length; j++) {
                copiedArray[i][j] = array[i][j];
            }
        }
        return copiedArray;
    }

    // Sorts a 2D array
    private String[][] SortArray(String[][] inputArray, int sortColumn) {
        String[][] sortedArray = CopyArray(inputArray);
        boolean hasSwapped = true;

        // Bubble sorts the 2D array by name
        while (hasSwapped) {
            hasSwapped = false;
            for (int i = 0; i < sortedArray.length; i++) {
                for (int j = 0; j < sortedArray.length - 1; j++) {
                    if (inputArray[j][sortColumn].compareTo(inputArray[j + 1][sortColumn]) > 0) {
                        String[] temp = inputArray[j];
                        inputArray[j] = inputArray[j + 1];
                        inputArray[j + 1] = temp;
                        hasSwapped = true;
                    }
                }
            }
        }
        return sortedArray;
    }

    // If duplicates are found then replaces them with a new item
    private String[][] RerollArray(String[] inputArray, String[][] outputArray) {
        String[][] uniqueArray = CopyArray(outputArray);
        random = new Random();
        boolean hasRerolled = true;

        while (hasRerolled) {
            hasRerolled = false;
            // Sorts the array everytime an item is compared so a duplicate does not show up
            // at a later index
            SortArray(uniqueArray, 0);
            for (int i = 0; i < uniqueArray.length; i++) {
                for (int j = 0; j < uniqueArray.length - 1; j++) {
                    // If index i = index i + 1, then replaces i with a new item
                    if (uniqueArray[j][0].equals(uniqueArray[j + 1][0])) {
                        int newItem = random.nextInt(inputArray.length);
                        uniqueArray[j][0] = inputArray[newItem];
                        hasRerolled = true;
                    }
                }
            }
        }
        return uniqueArray;
    }

}
