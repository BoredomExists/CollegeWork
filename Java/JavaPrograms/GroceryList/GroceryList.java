/*
 * Christian Biermann
 */
public class GroceryList {
    private class ListNode {
        GroceryItem data;
        ListNode link;

        // Default Constructor
        public ListNode() {
            data = null;
            link = null;
        }

        // Parameter Constructor
        public ListNode(GroceryItem aData, ListNode aLink) {
            this.data = aData;
            this.link = aLink;
        }
    }

    private ListNode head;
    private ListNode current;
    private ListNode prev;

    public GroceryList() {
        head = current = prev = null;
    }

    // Moves current node to the next node in the list
    public void gotoNext() {
        if (current == null) {
            return;
        }
        prev = current;
        current = current.link;
    }

    // Gets the current node
    public GroceryItem getCurrent() {
        if (current == null) {
            return null;
        }
        return current.data;
    }

    // Sets the current node
    public void setCurrent(GroceryItem aData) {
        if (current == null) {
            return;
        }
        current.data = aData;
    }

    // Adds a new GroceryItem to the list
    public void addItem(GroceryItem aData) {
        ListNode newNode = new ListNode(aData, null);

        if (head == null) {
            head = current = newNode;
            return;
        }
        ListNode temp = head;
        while (temp.link != null) {
            temp = temp.link;
        }
        temp.link = newNode;
    }

    // Adds a GroceryItem one after the current node
    public void addItemAfterCurrent(GroceryItem aData) {
        if (current == null) {
            return;
        }
        ListNode newNode = new ListNode(aData, current.link);
        current.link = newNode;
    }

    // Removes the current GroceryItem
    public void removeCurrent() {
        if (head == current) {
            head = head.link;
            current = head;
        } else {
            prev.link = current.link;
            current = current.link;
        }
    }

    // Outputs the GroceryList
    public void showList() {
        ListNode temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.link;
        }
    }

    // Checks if the inputted name and value matches an item in the GroceryList
    public boolean contains(GroceryItem aData) {
        ListNode temp = head;
        while (temp != null) {
            // if aData name and value == GroceryList's current node name and value
            if (aData.equals(temp.data))
                return true;
            temp = temp.link;
        }
        return false;
    }

    // Checks total cost of the GroceryList
    public double totalCost() {
        double total = 0;
        ListNode temp = head;
        while (temp != null) {
            // Gets the data's value and adds it to the total
            total += temp.data.value;
            temp = temp.link;
        }
        return total;
    }
}
