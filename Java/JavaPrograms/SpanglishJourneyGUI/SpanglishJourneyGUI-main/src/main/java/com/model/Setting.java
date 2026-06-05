package com.model;

/**
 * The Setting class represents various user settings including dark mode,
 * text-to-speech, font size, notifications, and lesson timer.
 */
public class Setting {
    private boolean darkMode;
    private boolean textToSpeech;
    private int fontSize;
    private boolean notifications;
    private boolean lessonTimer;

    /**
     * Default constructor that initializes the settings with default values.
     * Dark mode, text-to-speech, notifications, and lesson timer are set to false.
     * The font size is initialized to 14.
     */
    public Setting() {
        this.darkMode = false;
        this.textToSpeech = false;
        this.fontSize = 14;
        this.notifications = false;
        this.lessonTimer = false;
    }

    /**
     * Overloaded constructor that allows initialization of all settings.
     * 
     * @param darkMode      true to enable dark mode, false to disable.
     * @param textToSpeech  true to enable text-to-speech, false to disable.
     * @param fontSize      the font size to be set.
     * @param notifications true to enable notifications, false to disable.
     * @param lessonTimer   true to enable the lesson timer, false to disable.
     */
    public Setting(boolean darkMode, boolean textToSpeech, int fontSize, boolean notifications, boolean lessonTimer) {
        this.darkMode = darkMode;
        this.textToSpeech = textToSpeech;
        this.fontSize = fontSize;
        this.notifications = notifications;
        this.lessonTimer = lessonTimer;
    }

    /**
     * Sets the font size.
     * 
     * @param number the font size to be set.
     */
    public void setFontSize(int number) {
        this.fontSize = number;
    }

    /**
     * Sets dark mode based on the provided value.
     * 
     * @param value 1 to turn it on, 0 to turn it off
     */
    public void setDarkMode(boolean value) {
        if (value)
            this.darkMode = true;
        else
            this.darkMode = false;
    }

    /**
     * Gets the setting of dark mode
     * 
     * @return The boolean value of darkmode
     */
    public boolean getDarkMode() {
        return this.darkMode;
    }

    /**
     * Turns on/off text-to-speech based on the provided value.
     * 
     * @param value 1 to turn it on, 0 to turn it off
     */
    public void setTextToSpeech(boolean value) {
        if (value)
            this.textToSpeech = true;
        else
            this.textToSpeech = false;
    }

    /**
     * Gets the setting of Text-To-Speech
     * 
     * @return The boolean value of textToSpeech
     */
    public boolean getTextToSpeech() {
        return this.textToSpeech;
    }

    /**
     * Turn on/off notifications based on the provided value.
     * 
     * @param value 1 to turn it on, 0 to turn it off
     */
    public void setNotifications(boolean value) {
        if (value)
            this.notifications = true;
        else
            this.notifications = false;
    }

    /**
     * Gets the setting of notifications
     * 
     * @return The boolean value of notifications
     */
    public boolean getNotifications() {
        return this.notifications;
    }

    /**
     * Gets the current font size setting.
     * 
     * @return the font size.
     */
    public int getFontSize() {
        return this.fontSize;
    }

    /**
     * Turns on or off the lesson timer
     * 
     * @param value 1 to turn it on, 0 to turn it off
     */
    public void setLessonTimer(boolean value) {
        if (value)
            this.lessonTimer = true;
        else
            this.lessonTimer = false;
    }

    /**
     * Gets the setting of lesson timer
     * 
     * @return The boolean value of lesson timer
     */
    public boolean getLessonTimer() {
        return this.lessonTimer;
    }
}
