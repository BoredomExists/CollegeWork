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
    private int size;

    // Default Constructor
    public LLQueue() {
        head = tail = null;
        this.size = 1;
    }

    // Adds Element to the queue
    public void enqueue(T TData) {
        ListNode newNode = new ListNode(TData, null);
        if (head == null) {
            head = tail = newNode;
            return;
        }
        tail.link = newNode;
        tail = tail.link;
        this.size++;
    }

    // Removes Element from the queue
    public T dequeue() {
        if (head == null)
            return null;
        T ret = head.data;
        head = head.link;
        this.size--;
        return ret;
    }

    // Clears the queue
    public void clearQueue() {
        while (this.size >= 0) {
            dequeue();
            if (this.size == 0) {
                break;
            }
        }
    }

    // Looks at first element in the queue but does not remove it
    public T peek() {
        if (head == null)
            return null;
        return head.data;
    }

    // Prints the Queue
    public void print() {
        for (ListNode temp = head; temp != null; temp = temp.link)
            System.out.println(temp.data);
    }

    public int size() {
        return this.size;
    }

}
