/*
 * Christian Biermann
 */
public class SheepScheduler {
    Sheep currentSheep;
    int currentMinute = 0;

    // Adds a sheep to the specific heap
    public void addSheep(MinHeap<Sheep> heap, Sheep s) {
        if (currentSheep == null)
            currentSheep = s;
        else
            heap.add(s);
    }

    // Checks if the currentSheep is done being sheared.
    public boolean isDone() {
        if (currentSheep == null)
            return true;
        else
            return false;
    }

    public Sheep getCurrentSheep() {
        return currentSheep;
    }

    // Decreases the sheep's shear time by 1
    public void advanceOneMinute(MinHeap<Sheep> heap) {
        currentMinute++;
        currentSheep.shearForOneMinute();
        if (currentSheep.isDone()) {
            currentSheep = heap.remove();
        }
    }

    // Adds the elements of the array directly into a heap
    public void addArrayToHeap(MinHeap<Sheep> heap, Sheep[] sheepA) {
        for (int i = 0; i < sheepA.length; i++) {
            heap.add(sheepA[i]);
        }
    }

    // Sorts the array based on arrival time
    public void bubbleSort(Sheep[] sheepA) {
        boolean hasSwapped = true;
        while (hasSwapped) {
            hasSwapped = false;
            for (int i = 0; i < sheepA.length - 1; i++) {
                if (sheepA[i].getArrivalTime() > sheepA[i + 1].getArrivalTime()) {
                    Sheep temp = sheepA[i];
                    sheepA[i] = sheepA[i + 1];
                    sheepA[i + 1] = temp;
                    hasSwapped = true;
                }
            }
        }
    }

    public void printArray(Sheep[] sheepA) {
        for (int i = 0; i < sheepA.length; i++) {
            System.out.println(sheepA[i]);
        }
    }

}
