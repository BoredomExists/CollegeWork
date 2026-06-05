/*
 * Christian Biermann
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class RecursiveTriangle extends Canvas {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Recursive Triangle");
        frame.setSize(900, 900);

        RecursiveTriangle rt = new RecursiveTriangle();

        frame.add(rt);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Paints the triangle onto the GUI
    public void paint(Graphics g) {
        int[] x = { 0, (this.getSize().height) / 2, this.getSize().height }; // Gets the x-points for the triangle
        int[] y = { this.getSize().height, 0, this.getSize().height }; // Gets the y-points for the triangle
        drawTriangle(x, y, this.getSize().height, g);// Calls the method to draw the triangle
    }

    public void drawTriangle(int[] xPoints, int[] yPoints, int s, Graphics g) {
        int points = 3;
        int sub = s / 2; // Gets the subdivision of the triangle

        g.setColor(Color.black);
        g.fillPolygon(xPoints, yPoints, points); // Paints the triangle
        if (sub > 4) {
            g.setColor(Color.white);

            // Sets x-points inside the drawn triangle
            int[] newXPoints = { (xPoints[0] + xPoints[1]) / 2, (xPoints[1] + xPoints[2]) / 2,
                    (xPoints[2] + xPoints[0]) / 2 };

            // Sets y-points inside the drawn triangle
            int[] newYPoints = { (yPoints[0] + yPoints[1]) / 2, (yPoints[1] + yPoints[2]) / 2,
                    (yPoints[2] + yPoints[0]) / 2 };

            g.fillPolygon(xPoints, yPoints, points);// Paints the inner triangle

            // Using recursion draws multiple triangles within the drawn triangles
            drawTriangle(new int[] { xPoints[0], newXPoints[0], newXPoints[2] },
                    new int[] { yPoints[0], newYPoints[0], newYPoints[2] }, sub, g);
            drawTriangle(new int[] { newXPoints[0], xPoints[1], newXPoints[1] },
                    new int[] { newYPoints[0], yPoints[1], newYPoints[1] }, sub, g);
            drawTriangle(new int[] { newXPoints[2], newXPoints[1], xPoints[2] },
                    new int[] { newYPoints[2], newYPoints[1], yPoints[2] }, sub, g);
        }
    }
}
