/*
 * Christian Biermann
 */


public class LinkedBST {
    private class Node {
        Shape data;
        Node leftChild;
        Node rightChild;

        public Node(Shape aData) {
            data = aData;
            leftChild = rightChild = null;
        }
    }

    private Node root; // head

    public LinkedBST() {
        root = null;
    }

    public void add(Shape aData) {
        if (root == null)
            root = new Node(aData);
        else
            add(root, aData);
    }

    // Adds a new node to the tree
    private Node add(Node aNode, Shape aData) {
        if (aNode == null)
            aNode = new Node(aData);
        else if (aData.compareTo(aNode.data) < 0) // Go Left
            aNode.leftChild = add(aNode.leftChild, aData);
        else if (aData.compareTo(aNode.data) > 0) // Go Right
            aNode.rightChild = add(aNode.rightChild, aData);
        return aNode;
    }

    public void printPreorder() {
        printPreorder(root);
    }

    // Prints the tree in a pre-order fashion
    private void printPreorder(Node aNode) {
        if (aNode == null)
            return;
        System.out.println(aNode.data);// Process
        printPreorder(aNode.leftChild);// Left
        printPreorder(aNode.rightChild);// Right
    }

    public void printInorder() {
        printInorder(root);
    }

    // prints the tree in order based on weight
    private void printInorder(Node aNode) {
        if (aNode == null)
            return;

        printInorder(aNode.leftChild);// Left
        System.out.println(aNode.data);// Process
        printInorder(aNode.rightChild);// Right
    }

    public void printPostOrder() {
        printPostOrder(root);
    }

    // Prints the tree in a post-order style.
    private void printPostOrder(Node aNode) {
        if (aNode == null)
            return;

        printPostOrder(aNode.leftChild);// Left
        printPostOrder(aNode.rightChild);// Right
        System.out.println(aNode.data);// Process
    }

    public boolean search(Shape aData) {
        return search(root, aData);
    }

    // Searches the tree for a specific node.
    private boolean search(Node aNode, Shape aData) {
        if (aNode == null) {
            return false;
        } else if (aData.compareTo(aNode.data) < 0) // Go Left
        {
            return search(aNode.leftChild, aData);
        } else if (aData.compareTo(aNode.data) > 0) // Go Right
        {
            return search(aNode.rightChild, aData);
        } else
            return true;
    }

    public void remove(Shape aData) {
        root = remove(root, aData);
    }

    // Removes a node from the tree
    private Node remove(Node aNode, Shape aData) {
        if (aNode == null)
            return null;
        else if (aData.compareTo(aNode.data) < 0)
            aNode.leftChild = remove(aNode.leftChild, aData);
        else if (aData.compareTo(aNode.data) > 0)
            aNode.rightChild = remove(aNode.rightChild, aData);
        else {
            if (aNode.rightChild == null)
                return aNode.leftChild;
            else if (aNode.leftChild == null)
                return aNode.rightChild;
            Node temp = findMinInTree(aNode.rightChild);
            aNode.data = temp.data;
            aNode.rightChild = remove(aNode.rightChild, temp.data);
        }
        return aNode;
    }

    // finds the minimum area value in the tree
    public Shape findMinInTree() {
        return findMinInTree(root).data;
    }

    private Node findMinInTree(Node aNode) {
        if (aNode == null)
            return null;
        else if (aNode.leftChild == null)
            return aNode;
        else
            return findMinInTree(aNode.leftChild);
    }

    // Finds the maximum area value in the tree
    public Shape findMaxInTree() {
        return findMaxInTree(root);
    }

    private Shape findMaxInTree(Node aNode) {
        if (aNode == null)
            return null;
        else if (aNode.rightChild == null)
            return aNode.data;
        else
            return findMaxInTree(aNode.rightChild);
    }

    // Deletes all shapes that have a greater area than the inputted area.
    public Shape deleteMaxArea(double maxArea) {
        return deleteMaxArea(root, maxArea).data;
    }

    private Node deleteMaxArea(Node aNode, double maxArea) {
        if (aNode == null)
            return null;
        deleteMaxArea(aNode.leftChild, maxArea);
        deleteMaxArea(aNode.rightChild, maxArea);

        if (aNode.data.getArea() >= maxArea) {
            remove(aNode.data);
            return aNode;
        }

        return aNode;
    }
}
