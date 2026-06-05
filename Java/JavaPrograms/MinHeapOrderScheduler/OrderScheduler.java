/*
 * Christian Biermann
 */

public class OrderScheduler {
    MinHeap<Order> orders = new MinHeap<Order>();
    Order currentOrder;
    int currentMinute = 0;
    int totalOrders = 0;
    int summedWaitTimes = 0;

    // If the current order is null => Current order becomes the inputted order,
    // otherwise, add the inputted order into the heap
    public void addOrder(Order order) {
        if (currentOrder == null) {
            currentOrder = order;
        } else
            orders.add(order);
        totalOrders++;
    }

    // Increments the currentMinute counter and then cooks the currentOrder for one
    // minute, when done, calculates a waiting time and adds the waiting time to the
    // summed wait times and the current order becomes the order at the top of the
    // heap.
    public void advanceOneMinute() {
        currentMinute++;
        currentOrder.cookForOneMinute();
        if (currentOrder.isDone()) {
            int waitingTime = currentMinute - currentOrder.getArrivalTime();
            summedWaitTimes += waitingTime;
            currentOrder = orders.remove();
        }
    }

    // Checks if the order is done
    public boolean isDone() {
        if (currentOrder == null)
            return true;
        else
            return false;
    }

    // Returns current order
    public Order getCurrentOrder() {
        return currentOrder;
    }

    // Returns the average waiting time
    public double getAverageWaitingTime() {
        return summedWaitTimes / totalOrders;
    }
}
