/*
 * Christian Biermann
 */

public interface QueueI<T> {
    public void enqueue(T TData);

    public T dequeue();

    public T peek();

    public void print();
}
