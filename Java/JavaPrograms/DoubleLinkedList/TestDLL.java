public class TestDLL {
    public static final double[] TEST_VALS_1 = { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0 };

    public static void main(String[] args) {
        DoubleDoubleLL list = new DoubleDoubleLL();

        for (double i = 0; i < TEST_VALS_1.length; i++) {
            list.add(i);
        }
    }
}
