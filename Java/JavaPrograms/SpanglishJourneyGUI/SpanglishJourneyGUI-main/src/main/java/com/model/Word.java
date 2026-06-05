package com.model;

/**
 * A class that acts as a dictionary, storing information about a word and the
 * word in another language.
 * 
 * @author Christian Lowery
 */
public class Word {
    private String word;
    private String wordInOtherLanguage;
    private String definition;
    private String pronunciation;

    /**
     * Constructor for the Word
     * 
     * @param word                Word in English
     * @param wordInOtherLanguage Word in the users language
     * @param defintition         Definition of the word
     * @param pronunciation       Phonetic pronunciation of the word in English
     */
    public Word(String word, String wordInOtherLanguage, String defintition, String pronunciation) {
        this.word = word;
        this.wordInOtherLanguage = wordInOtherLanguage;
        this.definition = defintition;
        this.pronunciation = pronunciation;

    }

    /**
     * Retrieves the word.
     * 
     * @return The word
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Retrieves the word in the users language.
     * 
     * @return The word in the users language
     */
    public String getWordInOtherLanguage() {
        return this.wordInOtherLanguage;
    }

    /**
     * Retrieves the definition of the word.
     * 
     * @return The definition of the word
     */
    public String getDefinition() {
        return this.definition;
    }

    /**
     * Retrieves the phonetic pronunciation of the word.
     * 
     * @return The phonetic pronunciation of the word
     */
    public String getPronunciation() {
        return this.pronunciation;
    }
}