/*
 * Christian Biermann
 */
public class GroceryItem {
    String name;
    double value;

    // Default Constructor
    GroceryItem() {
        this.name = "none";
        this.value = 0;
    }

    // Parameter Constructor
    GroceryItem(String aName, double aValue) {
        this.setGroceryName(aName);
        this.setGroceryValue(aValue);
    }

    // Sets the Grocery Item's name
    public void setGroceryName(String aName) {
        if (aName != null)
            this.name = aName;
        else
            this.name = "none";
    }

    // Sets the Grocery Item's value
    public void setGroceryValue(double aValue) {
        if (aValue >= 0)
            this.value = aValue;
        else
            this.value = 0;
    }

    // Gets the Grocery Item's name
    public String getGroceryName() {
        return name;
    }

    // Gets the Grocery Item's value
    public double getGroceryValue() {
        return value;
    }

    // Sets the toString method to print the Item in this format
    public String toString() {
        return "Grocery Item Name: " + this.name + " Value: " + this.value;
    }

    // Checks if the name and value is the same as the Grocery Item's name and value
    public boolean equals(GroceryItem item) {
        return item != null &&
                this.name.equals(item.getGroceryName()) &&
                this.value == (item.getGroceryValue());
    }
}
