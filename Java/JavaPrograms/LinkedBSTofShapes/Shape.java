/*
 * Christian Biermann
 */

public class Shape implements Comparable<Shape> {
    String name;
    double radius, base, height, width, length, area;

    public Shape() {
        name = "";
    }

    public Shape(String aName) {
        this.setName(aName);
    }

    public void setName(String aName) {
        if (aName == null)
            name = "";
        else
            this.name = aName;
    }

    public String getName() {
        return this.name;
    }

    public void setRadius(double aRadius) {
        if (aRadius <= 0.0)
            radius = 1.0;
        else
            this.radius = aRadius;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getCircleArea() {
        return Math.round((Math.pow(getRadius(), 2) * Math.PI) * 10.0) / 10.0;
    }

    public void setLength(double aLength) {
        if (aLength <= 0.0)
            length = 1.0;
        else
            this.length = aLength;
    }

    public double getLength() {
        return this.length;
    }

    public void setWidth(double aWidth) {
        if (aWidth <= 0.0)
            width = 1.0;
        else
            this.width = aWidth;
    }

    public double getWidth() {
        return this.width;
    }

    public double getRectangleArea() {
        return Math.round((getLength() * getWidth()) * 10.0) / 10.0;
    }

    public void setBase(double aBase) {
        if (aBase <= 0.0)
            base = 1.0;
        else
            this.base = aBase;
    }

    public double getBase() {
        return this.base;
    }

    public void setHeight(double aHeight) {
        if (aHeight <= 0.0)
            height = 1.0;
        else
            this.height = aHeight;
    }

    public double getHeight() {
        return this.height;
    }

    public double getRightTriangleArea() {
        return Math.round(((getBase() * getHeight()) / 2) * 10.0) / 10.0;
    }

    public void setArea(double anArea) {
        this.area = anArea;
    }

    public double getArea() {
        switch (name.toLowerCase()) {
            case "circle":
                return Math.round((Math.pow(getRadius(), 2) * Math.PI) * 10.0) / 10.0;
            case "rectangle":
                return Math.round((getLength() * getWidth()) * 10.0) / 10.0;
            case "right triangle":
                return Math.round(((getBase() * getHeight()) / 2) * 10.0) / 10.0;
            default:
                return 0.0;
        }
    }

    public String toString() {
        switch (name.toLowerCase()) {
            case "circle":
                return this.name + " Radius: " + getRadius() + " Area: " + getCircleArea();
            case "rectangle":
                return this.name + " Length: " + getLength() + " Width: " + getWidth() + " Area: " + getRectangleArea();
            case "right triangle":
                return this.name + " Base: " + getBase() + " Height: " + getHeight() + " Area: "
                        + getRightTriangleArea();
            default:
                return "Unknown Shape";
        }
    }

    public int compareTo(Shape s) {
        if (s.getArea() > getArea()) {
            return -1;
        } else if (s.getArea() < getArea()) {
            return 1;
        } else if (s.getArea() == getArea()) {
            return s.getName().compareTo(getName());
        }
        return -1;
    }
}
