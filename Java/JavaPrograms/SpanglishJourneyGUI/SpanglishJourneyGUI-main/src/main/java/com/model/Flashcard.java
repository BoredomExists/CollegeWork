package com.model;
/**
 * The Flashcard class represents a flashcard that contains a word and a
 * definition
 * that provides context for that word.
 * 
 * This works alongside Wordsmastery.json & WordsMastery.java for its
 * information.
 */
public class Flashcard {
    private String word;
    private String knownWord;
    private String definition;

    /**
     * Constructs a Flashcard with a specified word and definition.
     *
     * @param word       the word associated with the flashcard.
     * @param definition a definition that provides context for the word.
     */
    public Flashcard(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    /**
     * Retrieves the definition associated with the flashcard.
     *
     * @return the definition providing context for the word.
     */
    public String getDefinition() {
        return this.definition;
    }

    /**
     * Sets the definition for the flashcard.
     *
     * @param definition the new definition providing context for the word.
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     * Retrieves the word associated with the flashcard.
     *
     * @return the word for the flashcard.
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Displays the flashcard
     */
    public String toString() {
        return "Word: " + this.word + "\nDefinition: " + this.definition;
    }
}
