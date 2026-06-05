package com.model;

import java.util.ArrayList;

/**
 * Creates a new course for the registered user
 */

public class Course {
    private String language;
    private ArrayList<Module> modules;
    private double progress;

    /**
     * Constructor to initialize a Course with a language.
     * Initializes an empty list of modules.
     *
     * @param language The language of the course.
     */
    public Course(String language) {
        this.language = language;
        this.modules = getDefaultModules(); // Initializes a module list of already existing modules in the course
        this.progress = 0.0;
    }

    /**
     * Constructor to initialize a Course with a language and a list of modules.
     *
     * @param language The language of the course.
     * @param modules  The list of modules associated with the course.
     */
    public Course(String language, ArrayList<Module> modules) {
        this.language = language;
        this.modules = modules;
    }

    /**
     * Returns the language of the course.
     *
     * @return The language of the course.
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     * Adds a module to the course if the module is not null.
     *
     * @param module The module to add to the course.
     */
    public void addModule(Module module) {
        if (module != null) {
            modules.add(module);
        }
    }

    /**
     * Gets a list of all the modules in the course
     * 
     * @return An ArrayList of modules
     */
    public ArrayList<Module> getModules() {
        return this.modules;
    }

    /**
     * Gets the progress of the module
     * 
     * @return A double value of the progress
     */
    public Double getProgress() {
        return this.progress;
    }

    /**
     * Updates the course progress based on the progress of the module
     */
    public void updateCourseProgress() {
        for (int i = 0; i < modules.size(); i++) {
            this.progress += modules.get(i).getProgress() / (double) modules.size();
        }
    }

    /**
     * Retrieves a module from the course by its name.
     *
     * @param name The name of the module.
     * @return The Module if found, otherwise returns null.
     */
    public Module getModule(String name) {
        // Iterates through the list of modules to find the one with the matching name.
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getName().equals(name))
                return modules.get(i);
        }
        return null; // Returns null if no module with the given name is found.
    }

    /**
     * Prints all the modules within the course
     */
    public void printModules() {
        for (int i = 0; i < modules.size(); i++) {
            System.out.println(modules.get(i).getName());
        }
    }

    /**
     * Gets all the default modules that are already existing in a course
     * 
     * @return An ArrayList of modules
     */
    public ArrayList<Module> getDefaultModules() {
        ArrayList<Module> mList = new ArrayList<Module>();
        mList.add(new Module("Basic English Essentials"));
        mList.add(new Module("Everyday English and Basic Grammer"));
        return mList;
    }
}
