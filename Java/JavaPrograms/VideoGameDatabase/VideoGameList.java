/*
 * Christian Biermann
 */

//Creates the Linked List of VideoGames
public class VideoGameList {
    // Creates the node and link for the Linked List
    private class ListNode {
        VideoGame data;
        ListNode link;

        public ListNode() {
            data = null;
            link = null;
        }

        public ListNode(VideoGame vgData, ListNode aLink) {
            this.data = vgData;
            this.link = aLink;
        }
    }

    private ListNode head;
    private ListNode current;
    private ListNode prev;

    // Default constructor for creating the Linked List
    public VideoGameList() {
        head = current = prev = null;
    }

    // Displays all video games in the List
    public void showVideoGames() {
        ListNode temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.link;
        }
    }

    // Adds a VideoGame to the List
    public void addVideoGame(VideoGame aGame) {
        ListNode newNode = new ListNode(aGame, null);
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

    // Checks if the List is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Checks if the searched name and console is within the list. Refer to Input02
    public void contains(VideoGame vgData) {
        ListNode temp = head;
        while (temp != null) {
            if (vgData.equals(temp.data))
                System.out.println(temp.data);
            temp = temp.link;
        }
    }

    // Shows all the VideoGame names with all the consoles the game is for
    public void showAllNames(VideoGame vgData) {
        ListNode temp = head;
        while (temp != null) {
            if (vgData.equalsOnlyConsoles(temp.data))
                System.out.println(temp.data);
            temp = temp.link;
        }
    }

    // Shows all the VideoGame consoles with all the games on the console
    public void showAllConsoles(VideoGame vgData) {
        ListNode temp = head;
        while (temp != null) {
            if (vgData.equalsOnlyNames(temp.data))
                System.out.println(temp.data);
            temp = temp.link;
        }
    }

    // Gets the current node
    public VideoGame getCurrent() {
        if (current == null) {
            return null;
        }
        return current.data;
    }

    // Goes to the next node
    public void goToNext() {
        if (current == null) {
            return;
        }
        prev = current;
        current = current.link;
    }

    // Checks if the next element is not null
    public boolean hasMore() {
        return current != null;
    }

    // Puts the current node back to the head
    public void reset() {
        current = head;
        prev = null;
    }
}
