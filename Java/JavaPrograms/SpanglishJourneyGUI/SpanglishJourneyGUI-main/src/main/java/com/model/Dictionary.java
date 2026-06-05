package com.model;
import java.util.ArrayList;

/**
 * Tracks a users progress with individual words, adding them to the Word Mastery once they've mastered them.
 * 
 */
public class Dictionary {
    // Fields
    private String word;
    private String wordInOtherLanguage;
    private String definition;
    private String pronunciation;
    private ArrayList<Word> similarWords;

    // Constructor

    /**
     * Intializes a new Word Mastery, creating an array list for similar words.
     */
    public Dictionary() {
        this.similarWords = new ArrayList<>();
    }

    // Methods

    /**
     * Adds a word to the users Word Mastery.
     * @param word The word .
     * @param definition The definition of the word.
     * @param pronunciation The phonetic pronunciation of the word.
     */
    public void addWord(String word, String definition, String pronunciation) {
        this.word = word;
        this.definition = definition;
        this.pronunciation = pronunciation;
    }

    /**
     * Adds a new word to the list of similar words.
     * @param word The new word being added.
     */
    public void addSimilarWord(Word word) {
        this.similarWords.add(word);
    }

    /**
     * Returns the Array List of similar words.
     * @return The Array List of words.
     */
    public ArrayList<Word> getSimilarWords() {
        return new ArrayList<>(this.similarWords);
    }
}

