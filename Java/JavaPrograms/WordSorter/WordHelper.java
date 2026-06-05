/*
 * Christian Biermann
 */

public class WordHelper {

    // Takes the array as a parameter and copies it as a return value
    public static String[] CopyArray(String[] inputArray) {
        String[] copiedArray = new String[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            copiedArray[i] = inputArray[i];
        }
        return copiedArray;
    }

    // Compares each word to see how many vowels are in it.
    public static String[] sortByVowels(String[] array) {
        // Vowels to look for
        String vowels = "aeiouyAEIOUY";
        boolean hasSwapped = true;
        String[] sortedArray = CopyArray(array);

        // Start of bubble sort
        while (hasSwapped) {
            hasSwapped = false;

            // Start of looping through copied array
            for (int i = 0; i < sortedArray.length - 1; i++) {
                // Every loop, the vowel counters reset for new words
                int vowelCount1 = 0;
                int vowelCount2 = 0;
                // With each word, it checks if any of the letters in the vowels string are in
                // the word and increments counter if so
                for (char c : sortedArray[i].toCharArray()) {
                    if (vowels.indexOf(c) != -1) {
                        vowelCount1++;
                    }
                }
                for (char c : sortedArray[i + 1].toCharArray()) {
                    if (vowels.indexOf(c) != -1) {
                        vowelCount2++;
                    }
                }
                // Doing the bubble sort swapping the words if the first word's vowel count is
                // greater than the second word
                if (vowelCount1 > vowelCount2) {
                    String temp = sortedArray[i];
                    sortedArray[i] = sortedArray[i + 1];
                    sortedArray[i + 1] = temp;
                    hasSwapped = true;
                }
            } // End of loop
        } // End of bubble sort

        return sortedArray;
    }

    // Same thing as the sort by vowels, just variable name changes
    public static String[] sortByConsonants(String[] array) {
        // Consonants to look for
        String consonants = "bcdfghjklmnpqrstvwxzBCDFGHJKLMNQRSTVWXZ";
        boolean hasSwapped = true;
        String[] sortedArray = CopyArray(array);

        // Start of bubble sort
        while (hasSwapped) {
            hasSwapped = false;

            // Start of looping through copied array
            for (int i = 0; i < sortedArray.length - 1; i++) {
                int consonantCount1 = 0;
                int consonantCount2 = 0;
                for (char c : sortedArray[i].toCharArray()) {
                    if (consonants.indexOf(c) != -1) {
                        consonantCount1++;
                    }
                }
                for (char c : sortedArray[i + 1].toCharArray()) {
                    if (consonants.indexOf(c) != -1) {
                        consonantCount2++;
                    }
                }

                // Uses bubble sort to compare the first word and the second word.
                if (consonantCount1 > consonantCount2) {
                    String temp = sortedArray[i];
                    sortedArray[i] = sortedArray[i + 1];
                    sortedArray[i + 1] = temp;
                    hasSwapped = true;
                }
            } // End of loop
        } // End of bubble sort
        return sortedArray;
    }

    // Compares each word by their length and sorts them
    public static String[] sortByLength(String[] array) {
        String[] sortedArray = CopyArray(array);
        int wordLength1, wordLength2;
        boolean hasSwapped = true;

        // Start of bubble sort
        while (hasSwapped) {
            hasSwapped = false;

            // Start of looping through copied array
            for (int i = 0; i < sortedArray.length - 1; i++) {
                // Gets each word's length
                wordLength1 = sortedArray[i].length();
                wordLength2 = sortedArray[i + 1].length();

                // Uses bubble sort to compare the first word and the second word.
                if (wordLength1 > wordLength2) {
                    String temp = sortedArray[i];
                    sortedArray[i] = sortedArray[i + 1];
                    sortedArray[i + 1] = temp;
                    hasSwapped = true;
                }
            } // End of loop
        } // End of bubble sort
        return sortedArray; // Returns the copied array sorted
    }
}