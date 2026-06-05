/*
 * Christian Biermann
 */

//Creates the video game item
public class VideoGame {
    String name;
    String console;

    // Default Constructor
    VideoGame() {
        this.name = "None";
        this.console = "None";
    }

    // Parameter Constructor
    VideoGame(String gameName, String consoleName) {
        this.setVideoGameName(gameName);
        this.setVideoGameConsole(consoleName);
    }

    // Sets the name of the video game
    public void setVideoGameName(String gameName) {
        if (gameName != null) {
            this.name = gameName;
        } else
            this.name = "None";
    }

    // Sets the console name of which the video game's name belongs to
    public void setVideoGameConsole(String consoleName) {
        if (consoleName != null) {
            this.console = consoleName;
        } else
            this.console = "None";
    }

    // Returns the video game's name
    public String getVideoGameName() {
        return name;
    }

    // Return the video game's console name
    public String getVideoGameConsole() {
        return console;
    }

    // Prints the name and console
    public String toString() {
        return name + " " + console;
    }

    // Checks if both the name and console is found in the video game's name and
    // console
    public boolean equals(VideoGame vg) {
        return vg != null &&
                vg.getVideoGameName().toUpperCase().contains(this.name.toUpperCase()) &&
                vg.getVideoGameConsole().toUpperCase().contains(this.console.toUpperCase());
    }

    // Does the same as the normal equals method except only does the name
    public boolean equalsOnlyNames(VideoGame vg) {
        return vg != null &&
                vg.getVideoGameName().toUpperCase().contains(this.name.toUpperCase());
    }

    // Does the same as the normal equals method except only does the console name
    public boolean equalsOnlyConsoles(VideoGame vg) {
        return vg != null &&
                vg.getVideoGameConsole().toUpperCase().contains(this.console.toUpperCase());
    }
}
