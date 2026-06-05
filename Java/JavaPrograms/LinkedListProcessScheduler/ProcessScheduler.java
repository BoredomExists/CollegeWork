/*
 * Christian Biermann
 */

public class ProcessScheduler {
    LLQueue<Process> processes;
    Process current;

    // Default Constructor
    public ProcessScheduler() {
        processes = new LLQueue<Process>();
    }

    // Returns Current Process
    public Process getCurrentProcess() {
        return current;
    }

    // Adds Processto the Queue | If Current process is null, assigns current to
    // added process
    public void addProcess(Process aProcess) {
        if (aProcess == null) {
            return;
        } else if (current == null) {
            current = aProcess;
        }
        processes.enqueue(aProcess);
    }

    // Removes and assigns current process to the next process
    public void runNextProcess() {
        current = processes.dequeue();
    }

    // Cancels the current process and runs the next process in the Queue
    public void cancelCurrentProcess() {
        current = processes.dequeue();
        runNextProcess();
    }

    public void printProcessQueue() {
        processes.print();
    }
}
