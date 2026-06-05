/*
 * Christian Biermann
 */

public class Sheep implements Comparable<Sheep> {
    String name;
    int shearTime;
    int arrivalTime;
    int shearTimeLeft;

    public Sheep() {
        name = "None";
        shearTime = 0;
        arrivalTime = 0;
        shearTimeLeft = shearTime;
    }

    public Sheep(String aName, int aShearTime, int anArrivalTime) {
        this.setName(aName);
        this.setShearTime(aShearTime);
        this.setArrivalTime(anArrivalTime);
        this.shearTimeLeft = shearTime;
    }

    public void setName(String aName) {
        if (aName == null)
            name = "None";
        else
            this.name = aName;
    }

    public String getName() {
        return this.name;
    }

    public void setShearTime(int aShearTime) {
        if (aShearTime < 0)
            shearTime = 0;
        else
            this.shearTime = aShearTime;
    }

    public int getShearTime() {
        return this.shearTime;
    }

    public void setArrivalTime(int anArrivalTime) {
        if (anArrivalTime < 0)
            arrivalTime = 0;
        else
            this.arrivalTime = anArrivalTime;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getShearTimeLeft() {
        return this.shearTimeLeft;
    }

    public int compareTo(Sheep s) {
        if (s.getShearTime() > getShearTime())
            return -1;
        else if (s.getShearTime() < getShearTime())
            return 1;
        else if (s.getShearTime() == getShearTime())
            return s.getName().compareTo(getName());
        return -1;
    }

    public String toString() {
        return "Name: " + getName() + ", " + "Sheer Time: " + getShearTime() + ", " + "Arrival Time: "
                + getArrivalTime();
    }

    public void shearForOneMinute() {
        this.shearTimeLeft = getShearTimeLeft() - 1;
    }

    public boolean isDone() {
        if (getShearTimeLeft() == 0)
            return true;
        else
            return false;
    }
}
