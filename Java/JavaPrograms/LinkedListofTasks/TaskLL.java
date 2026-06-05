package Homework03;

public class TaskLL {
    private class ListNode {
        Task data;
        ListNode aLink;
        ListNode pLink;

        public ListNode() {
            data = null;
            aLink = null;
            pLink = null;
        }

        public ListNode(Task aTask, ListNode aLinkP, ListNode pLinkP) {
            this.data = aTask;
            this.aLink = aLinkP;
            this.pLink = pLinkP;
        }
    }

    private ListNode head;
    private ListNode current;
    private ListNode prev;
    private int size;

    public TaskLL() {
        head = current = prev = null;
        prev = current = head = null;
        this.size = 0;
    }

    //Adds a new task to the list
    public void addTask(Task aTask) {
        ListNode newNode = new ListNode(aTask, null, null);

        if (head == null) {
            head = current = newNode;
            return;
        }
        ListNode temp = head;
        while (temp.aLink != null) {
            temp = temp.aLink;
        }
        temp.aLink = newNode;
        this.size++;
    }

    //Removes the current task from the list
    public void removeTask() {
        if (head == current) {
            head = head.aLink;
            current = head;
        } else {
            prev.aLink = current.aLink;
            current = current.aLink;
        }
        this.size--;
    }

    //Checks if an inputted task is in the current Linked List
    public boolean contains(Task task) {
        ListNode temp = head;
        while (temp != null) {
            if (task.equals(temp.data))
                return true;
            temp = temp.aLink;
        }
        return false;
    }

    //Checks if the list is empty
    public boolean isEmpty() {
        return head == null;
    }

    //Prints the List
    public void printList() {
        ListNode temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.aLink;
        }
    }

    //Returns Size
    public int getSize()
    {
        return this.size;
    }

}
