/*
 * Christian Biermann
 */

public class Fruit implements Comparable<Fruit> {
    String name;
    double weight;

    public Fruit() {
        name = "apple";
        weight = 1.0;
    }

    public Fruit(String aN, double aW) {
        this.setName(aN);
        this.setWeight(aW);
    }

    public void setName(String aN) {
        if (aN == null)
            name = "apple";
        else
            this.name = aN;
    }

    public void setWeight(double aW) {
        if (aW <= 0)
            weight = 1.0;
        else
            this.weight = aW;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    public String toString() {
        return "Type: " + this.name + " Weight: " + this.weight;
    }

    public int compareTo(Fruit fruit) {
        if (fruit.getWeight() > getWeight()) { // If assigned fruit's weight is greater than compared fruit, return
                                               // positive
            return 1;
        } else if (fruit.getWeight() < getWeight()) { // If less than, return negative
            return -2;
        } else if (fruit.getWeight() == getWeight()) { // If same, compare by name
            return fruit.getName().compareTo(getName());
        }

        return -1;
    }

}
