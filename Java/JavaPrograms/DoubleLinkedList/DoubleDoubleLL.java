/*
 * Christian Biermann
 */

public class DoubleDoubleLL {
    private class ListNode {
        double data;
        ListNode aLink;
        ListNode pLink;

        public ListNode() {
            data = 0;
            aLink = null;
            pLink = null;
        }

        public ListNode(double aData, ListNode aLinkP, ListNode pLinkP) {
            this.data = aData;
            this.aLink = aLinkP;
            this.pLink = pLinkP;
        }
    }

    private ListNode head;
    private ListNode current;
    private ListNode prev;
    private int size;

    // Sets a Link to the next node and a Link to the previous node
    public DoubleDoubleLL() {
        head = current = prev = null;
        prev = current = head = null;
        this.size = 0;
    }

    public void add(double aData) // Adds to the end of the list
    {
        // Create a new node with the data
        ListNode newNode = new ListNode(aData, null, null);
        // Add the node to the list
        if (head == null) // Empty list
        {
            head = current = newNode;
            return;
        }
        ListNode temp = head;
        while (temp.aLink != null) {
            temp = temp.aLink;
        }
        temp.aLink = newNode; // Sets the last node in the list to the newNode
        this.size++;
    }

    // Adds a new value one after the current node
    public void addDataAfterCurrent(double aData) {
        if (current == null) {
            return;
        }
        ListNode newNode = new ListNode(aData, current.aLink, current.pLink);
        current.aLink = newNode;
        this.size++;
    }

    // Moves the current node to the next node
    public void gotoNext() {
        if (current == null) {
            return;
        }
        prev = current;
        current = current.aLink;
    }

    // Moves the current node ot the previous node
    public void gotoPrev() {
        if (current == null) {
            return;
        }
        current = prev;
        prev = prev.pLink;
    }

    // Moves current node to the head
    public void reset() {
        current = head;
        prev = null;
    }

    // Moves the current node to the last node of the list
    public void gotoEnd() {
        while (hasMore()) {
            if (current.aLink == null) {
                return;
            }
            prev = current;
            current = current.aLink;
        }
    }

    // Checks if there is more valid nodes in the list
    public boolean hasMore() {
        return current != null;
    }

    // Sets the current node to a value
    public void setCurrent(double aData) {
        if (current == null) {
            return;
        }
        current.data = aData;
    }

    // Possible Issue here, when removing last item in list, returns if null
    // Gets the current node
    public double getCurrent() {
        if (current == null) {
            return prev.data;
       }
        return current.data;
    }

    // Removes the current node
    public void removeCurrent() {
        if (current == null)
            return;
        if (head == current) {
            head = head.aLink;
            current = head;
        } else {
            prev.aLink = current.aLink;
            current = current.aLink;
        }
        this.size--;
    }

    // Removes a node at a specific index
    public void remove(double aData) {
        if (aData < 0 || aData > this.size)
            return;

        reset();
        for (int i = 0; i < aData; i++) {
            gotoNext();
        }
        removeCurrent();
    }

    // Prints the Linked List
    public void print() {
        ListNode temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.aLink;
        }
    }

    // Returns the size
    public int getSize() {
        return this.size;
    }

    // Checks to see if the list has a specific value
    public boolean contains(double aData) {
        ListNode temp = head;
        while (temp != null) {
            if (aData == temp.data)
                return true;
            temp = temp.aLink;
        }
        return false;
    }
}
