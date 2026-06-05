package Homework03;

public class Task {
    String task;
    int priority;

    public Task() {
        this.task = "None";
        this.priority = 4;
    }

    public Task(String taskName, int priorityValue) {
        this.setTaskName(taskName);
        this.setPriorityValue(priorityValue);
    }

    // Sets the Task's Name if null sets name to None
    public void setTaskName(String taskName) {
        if (taskName != null) {
            this.task = taskName;
        } else {
            this.task = "None";
        }
    }

    // Sets the Task's priority value if less than 0 or greater than 4, sets value
    // to 4
    public void setPriorityValue(int priorityValue) {
        if (priorityValue < 0 || priorityValue > 4) {
            priorityValue = 4;
        } else {
            this.priority = priorityValue;
        }
    }

    // Returns task name
    public String getTaskName() {
        return task;
    }

    // Returns task priority value
    public int getPriorityValue() {
        return priority;
    }

    public String toString() {
        return "[Task] Priority: " + this.priority + " Task: " + this.task;
    }

    // Checks if specific name and value is equal to Task's name and value
    public boolean equals(Task task) {
        return task != null &&
                this.task.equals(task.getTaskName()) &&
                this.priority == task.getPriorityValue();
    }
}
