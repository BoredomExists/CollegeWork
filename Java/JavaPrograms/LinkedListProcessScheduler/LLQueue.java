/*
 * Christian Biermann
 */

public class LLQueue<T> implements QueueI<T> {
    private class ListNode {
        T data;
        ListNode link;

        public ListNode(T TData, ListNode aLink) {
            data = TData;
            link = aLink;
        }
    }

    private ListNode head;
    private ListNode tail;

    public LLQueue() {
        head = tail = null;
    }

    // Adds Type to the queue
    public void enqueue(T TData) {
        ListNode newNode = new ListNode(TData, null);
        if (head == null) {
            head = tail = newNode;
            return;
        }
        tail.link = newNode;
        tail = tail.link;
    }

    // Removes Type from the queue
    public T dequeue() {
        if (head == null)
            return null;
        T ret = head.data;
        head = head.link;
        return ret;
    }

    // Views the first element in the queue without removing it
    public T peek() {
        if (head == null)
            return null;
        return head.data;
    }

    // Prints the Linked List Queue
    public void print() {
        for (ListNode temp = head; temp != null; temp = temp.link)
            System.out.println(temp.data);
    }
}
