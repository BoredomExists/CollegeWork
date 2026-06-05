/*
 * Christian Biermann
 */

public class Process {
    String name;
    double time;

    // Default Constructor
    public Process() {
        name = "None";
        time = 0.0;
    }

    // Parameter Constructor
    public Process(String aName, double aTime) {
        this.setName(aName);
        this.setCompletionTime(aTime);
    }

    // Sets Name if not null
    public void setName(String aName) {
        if (aName != null) {
            this.name = aName;
        } else {
            this.name = "None";
        }
    }

    // Sets time if not less than 0
    public void setCompletionTime(double aTime) {
        if (aTime < 0.0) {
            this.time = 0.0;
        } else {
            this.time = aTime;
        }
    }

    // Gets Process Name
    public String getName() {
        return this.name;
    }

    // Gets Process Completion Time
    public double getCompletionTime() {
        return this.time;
    }

    public String toString() {
        return "Process Name: " + this.name + " Completion Time: " + this.time;
    }
}
