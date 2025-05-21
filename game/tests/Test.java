package game.tests;

public class Test {
    public static int counter = 0;

    public static void checkAllTestsPassed() {
        if (counter == 0)
            System.out.println("All tests passed!");
        else
            System.out.println(counter + " tests failed!");
    }

    public static void expect(Object expected, Object actual) {
        System.out.println("Expected " + expected + " and got " + actual);
        if (expected.equals(actual)) {
            System.out.println("OK");
        } else {
            counter++;
            System.out.println("ERROR");
        }
    }
}
