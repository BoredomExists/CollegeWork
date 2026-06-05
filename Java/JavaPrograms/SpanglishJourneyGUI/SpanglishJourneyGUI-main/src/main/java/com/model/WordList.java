package com.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The WordList class manages a collection of Word objects and provides methods
 * for adding, checking, and retrieving words. It also maintains a dictionary
 * mapping RegisteredUser objects to Word objects.
 * 
 * This class follows the Singleton design pattern, ensuring only one instance
 * of WordList exists.
 * 
 * Author: Keaton Hill
 */
public class WordList {

    private static WordList wordList;
    private ArrayList<Word> words;
    private HashMap<RegisteredUser, Word> dictionary;

    /**
     * Private constructor to prevent instantiation. Initializes the word list and
     * dictionary.
     */
    private WordList() {
        DataLoader dl = new DataLoader();
        words = dl.getWords();
        dictionary = new HashMap<>();
    }

    /**
     * Returns the single instance of the WordList. If the instance doesn't exist
     * yet,
     * it is created.
     * 
     * @return the singleton instance of WordList.
     */
    public static WordList getInstance() {
        if (wordList == null) {
            wordList = new WordList();
        }
        return wordList;
    }

    /**
     * Checks if the specified word is present in the word list.
     * 
     * @param word The word to be checked.
     * @return true if the word exists in the list, false otherwise.
     */
    public Word haveWord(String word) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWord().equalsIgnoreCase(word)
                    || words.get(i).getWordInOtherLanguage().equalsIgnoreCase(word)) {
                return words.get(i);
            }
        }
        return null;
    }

    /**
     * Adds the specified word to the word list if it is not already present.
     * 
     * @param word The word to be added.
     * @return true if the word was successfully added, false if the word already
     *         exists.
     */
    public boolean addWord(String word, String wordInOtherLanguage, String definition, String pronunciation) {
        if (haveWord(word) == null) {
            return words.add(new Word(word, wordInOtherLanguage, definition, pronunciation));
        }
        return false;
    }

    /**
     * Retrieves the specified word from the word list.
     * 
     * @param word The word to be retrieved.
     * @return The Word object if it exists in the list, or null if not found.
     */
    public Word getWord(Word word) {
        int index = words.indexOf(word);
        return index >= 0 ? words.get(index) : null;
    }

    public ArrayList<Word> getWords() {
        return this.words;
    }
}
