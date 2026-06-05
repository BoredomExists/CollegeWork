/*
 * Christian Biermann
 */

import java.util.Scanner;
import java.io.*;

public class FruitTreeTester {
    static Scanner fileReader, strInput;
    static LinkedBST<Fruit> fruitBST;

    static final int HEADERS_AMT = 2;

    public static void main(String[] args) {
        strInput = new Scanner(System.in);
        String fileInput = new String();

        System.out.println("Welcome to the fruit tree!\n" + "Please enter a Fruit File Name: ");
        fileInput = strInput.nextLine();
        fillFruitBST(fileInput);

        System.out.println();
        System.out.println("Printing the in-order traversal.");
        fruitBST.printInorder();
        System.out.println();

        System.out.println("Printing the pre-order traversal.");
        fruitBST.printPreorder();
        System.out.println();

        System.out.println("Printing the post-order traversal.");
        fruitBST.printPostOrder();
        System.out.println();

        Fruit apple = new Fruit("Apple", 0.4859853412170728);
        System.out.println("Deleting " + apple.toString() + "\nPrinting in-order traversal.");
        fruitBST.remove(apple);
        fruitBST.printInorder();
    }


    //Reads a file and fills the tree
    public static void fillFruitBST(String fileName) {
        try {
            fileReader = new Scanner(new File(fileName));
            fruitBST = new LinkedBST<>();
            while (fileReader.hasNextLine()) {
                String fileLine = fileReader.nextLine();
                String[] columns = fileLine.split("\t");
                if (columns.length != HEADERS_AMT)
                    continue;
                Fruit fruit = new Fruit(columns[0], Double.parseDouble(columns[1]));
                fruitBST.add(fruit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
