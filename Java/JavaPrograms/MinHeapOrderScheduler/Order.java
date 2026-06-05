/*
 * Christian Biermann
 */

public class Order implements Comparable<Order> {
    String customer;
    String foodOrder;
    int cookingTime;
    int arrivalTime;
    int cookingTimeLeft;

    // Default Constructor
    public Order() {
        customer = "None";
        foodOrder = "None";
        cookingTime = 1;
        arrivalTime = 0;
        this.cookingTimeLeft = cookingTime;
    }

    // Parameterized Constructor
    public Order(String aCustomer, String aFoodOrder, int aCookingTime, int anArrivalTime) {
        this.setCustomer(aCustomer);
        this.setFoodOrder(aFoodOrder);
        this.setCookingTime(aCookingTime);
        this.setArrivalTime(anArrivalTime);
        cookingTimeLeft = getCookingTime();
    }

    // Sets the customer name
    public void setCustomer(String aCustomer) {
        if (aCustomer == null)
            customer = "None";
        else
            this.customer = aCustomer;
    }

    // Returns the customers info, such as name
    public String getCustomer() {
        return this.customer;
    }

    // Sets the order
    public void setFoodOrder(String aFoodOrder) {
        if (aFoodOrder == null)
            foodOrder = "None";
        else
            this.foodOrder = aFoodOrder;
    }

    // Returns the orders info
    public String getFoodOrder() {
        return this.foodOrder;
    }

    // Sets the cooking time for the order
    public void setCookingTime(int aCookingTime) {
        if (aCookingTime <= 0)
            cookingTime = 1;
        else
            this.cookingTime = aCookingTime;
    }

    // Returns the cooking time for the order
    public int getCookingTime() {
        return this.cookingTime;
    }

    // Sets the arrival time
    public void setArrivalTime(int anArrivalTime) {
        if (anArrivalTime < 0)
            arrivalTime = 0;
        else
            this.arrivalTime = anArrivalTime;
    }

    // Gets the arrival time of the order
    public int getArrivalTime() {
        return this.arrivalTime;
    }

    // Sets the time left to cook to the cooking time
    public void setCookingTimeLeft() {
        this.cookingTimeLeft = getCookingTime();
    }

    // Returns cooking time left
    public int getCookingTimeLeft() {
        return this.cookingTimeLeft;
    }

    // ToString method for displaying customer's order
    public String toString() {
        return "Customer: " + getCustomer() + " Order: " + getFoodOrder() + " Cooking Time Left: "
                + getCookingTimeLeft();
    }

    // Compares cooking time so shorter times have higher priority
    public int compareTo(Order o) {
        if (o.getCookingTime() < getCookingTime())
            return 1;
        else if (o.getCookingTime() > getCookingTime())
            return -1;
        else
            return -1;
    }

    // Decreases the time left by 1
    public void cookForOneMinute() {
        this.cookingTimeLeft = getCookingTimeLeft() - 1;
    }

    // Checks if the time left is 0 or not, returns true if 0, else false
    public boolean isDone() {
        if (getCookingTimeLeft() == 0)
            return true;
        else
            return false;
    }
}
