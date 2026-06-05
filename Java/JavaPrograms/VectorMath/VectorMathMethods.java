/*
 * Christian Biermann
 */

public class VectorMathMethods {

    // Method to that loops through the vectors and add each element, then adds it
    // to an array called resultV (ResultVector)
    public double[] AddVectors(double[] v1, double[] v2) {
        double[] resultV = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            resultV[i] = v1[i] + v2[i];
        }
        return resultV;
    }

    // Method to that loops through the vectors and subtracts each element, then
    // adds it
    // to an array called resultV (ResultVector)
    public double[] SubtractVectors(double[] v1, double[] v2) {
        double[] resultV = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            resultV[i] = v1[i] - v2[i];
        }
        return resultV;
    }

    // Method to that loops through a vector and adds each element after the
    // elements had a power of 2 raised to them. Then the answer is square rooted
    // and returns the answer.
    public double FindMagnitudeofVector(double[] v) {
        double answer = 0;
        for (int i = 0; i < v.length; i++) {
            // Takes each value in the vector, squares it, and adds it to the answer
            answer += Math.pow(v[i], 2);
        }
        // Takes the sqrt of the answer
        answer = Math.sqrt(answer);

        return answer;
    }

    // Method meant to output each process using the return values and which option
    // the user selected, will change the output result.
    public void DisplayOutput(double[] v1, double[] v2, int optionSelect) {
        if (optionSelect == 1) {
            double[] outputArray = AddVectors(v1, v2);

            for (int i = 0; i < v1.length; i++) {
                System.out.println(v1[i] + " + " + v2[i] + " = " + outputArray[i]);
            }
        } else if (optionSelect == 2) {
            double[] outputArray = SubtractVectors(v1, v2);

            for (int i = 0; i < v1.length; i++) {
                System.out.println(v1[i] + " - " + v2[i] + " = " + outputArray[i]);
            }
        } else if (optionSelect == 3) {
            double outputAnswer = FindMagnitudeofVector(v1);
            // Formats the output to 2 decimal values
            System.out.println("The magnitude of the vector is: " + String.format("%.2f", outputAnswer));
        }

    }

}
