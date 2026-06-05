/*
 * Christian Biermann
 */

import java.io.*;
import java.util.Scanner;

public class SheepSchedularSimulation {

    public static void main(String[] args) {
        Sheep[] sheeps = new Sheep[60];
        SheepScheduler ss = new SheepScheduler();
        MinHeap<Sheep> sheepHeap = new MinHeap<Sheep>(60);

        Scanner strInput = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        boolean again = true;

        while (again) {
            again = false;
            System.out.println("Enter the file name to schedule: ");
            String fileName = strInput.nextLine();

            addSheepFromFile(fileName, sheeps);
            ss.bubbleSort(sheeps); // Sorts the sheeps array by the arrival time.
            scheduleSheep(sheeps, sheepHeap);// Schedules the sheep

            // Rerun first while loop
            System.out.println("\nSchedule Another File? (Y/N)");
            String againInput = input.nextLine();
            if (againInput.equalsIgnoreCase("y")) {
                again = true;
            } else {
                System.out.println("Program Exiting....");
                again = false;
            }

        }
    }

    // Adds the sheep data from a file to the sheep array
    public static void addSheepFromFile(String fileName, Sheep[] sheepA) {
        int index = 0;
        try {
            Scanner fileReader = new Scanner(new File(fileName));
            while (fileReader.hasNextLine()) {
                String fileLine = fileReader.nextLine();
                String[] columns = fileLine.split("\t");
                if (columns.length != 3)
                    continue;
                Sheep newSheep = new Sheep(columns[0], Integer.parseInt(columns[1]), Integer.parseInt(columns[2]));
                sheepA[index] = newSheep;
                index++;
            }
            fileReader.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void scheduleSheep(Sheep[] sheepA, MinHeap<Sheep> sheepMH) {
        int arrivalTime = 0;
        int i = 0;
        Sheep currSheep = null;
        int shearTime = 0;

        // Schedule Sheep
        while (true) {

            // Adds the sheep to the heap when the current index is less than Sheep array's
            // length and that sheep's arrival time is equal to the incrementing arrival
            // time
            while (i < sheepA.length && sheepA[i].getArrivalTime() == arrivalTime) {
                sheepMH.add(sheepA[i]);
                i++;
            }

            // Gets a new current sheep from the heap and prints it and sets the shearTime
            // to that sheep's shear time.
            if (shearTime == 0) {
                currSheep = sheepMH.remove();
                if (currSheep != null) {
                    System.out.println(currSheep);
                    shearTime = currSheep.getShearTime();
                } else
                    break;
            }
            shearTime--; // Decreases the current sheep's shear time
            arrivalTime++; // Increases the arrivalTime, so more sheep get added to the heap.
        }
    }

}
