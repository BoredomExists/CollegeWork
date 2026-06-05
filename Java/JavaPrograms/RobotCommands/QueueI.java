/*
 * Christian Biermann
 */
public interface QueueI<T> {
    public void enqueue(T tData);

    public T dequeue();

    public void clearQueue();

    public T peek();

    public void print();

    public int size();
}