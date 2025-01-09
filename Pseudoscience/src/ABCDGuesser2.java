import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Prompts the user for 5 numbers and tests the charming theory through the De
 * Jager formula.
 *
 * @author Jesse Shields
 *
 */
public final class ABCDGuesser2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double value = -1;
        while (value < 0) {
            String temp = in.nextLine();
            if (FormatChecker.canParseDouble(temp)) {
                value = Double.parseDouble(temp);
            }
        }
        return value;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in, SimpleWriter out) {
        double value = -1.0;
        while (value < 0 || Double.compare(value, 1.0) == 0) {
            String temp = in.nextLine();
            if (FormatChecker.canParseDouble(temp)) {
                value = Double.parseDouble(temp);
            }
        }
        return value;
    }

    /**
     * Method to compute the charming theory calculation. Takes five inputs for
     * u, w, x, y, and z, and then iterates through the 17 exponent numbers for
     * each value w, x, y, and z in all combinations, then prints the exponents,
     * the best approximation of u, and the relative error.
     *
     * @param out
     *            The output stream
     *
     * @param u
     *            the value for u in the De Jager formula
     *
     * @param w
     *            the value for w in the De Jager formula
     * @param x
     *            the value for x in the De Jager formula
     *
     * @param y
     *            the value for y in the De Jager formula
     *
     * @param z
     *            the value for the z in the De Jager formula
     *
     */
    private static void charmingTheory(SimpleWriter out, double u, double w, double x,
            double y, double z) {
        double temp = 0.0;
        double bestApprox = 0.0;
        double estimate = 0.0;
        double exp1 = 0.0;
        double exp2 = 0.0;
        double exp3 = 0.0;
        double exp4 = 0.0;
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        final double[] exponents = new double[] { -5, -4, -3, -2, -1, -1 / 2, -1 / 3,
                -1 / 4, 0, 1 / 4, 1 / 3, 1 / 2, 1, 2, 3, 4, 5 };
        for (count1 = 0; count1 < exponents.length - 1; count1++) {
            temp = Math.pow(w, exponents[count1]) * Math.pow(x, exponents[count2])
                    * Math.pow(y, exponents[count3]) * Math.pow(z, exponents[count4]);

            for (count2 = 0; count2 < exponents.length - 1; count2++) {
                temp = Math.pow(w, exponents[count1]) * Math.pow(x, exponents[count2])
                        * Math.pow(y, exponents[count3]) * Math.pow(z, exponents[count4]);
                for (count3 = 0; count3 < exponents.length - 1; count3++) {
                    temp = Math.pow(w, exponents[count1]) * Math.pow(x, exponents[count2])
                            * Math.pow(y, exponents[count3])
                            * Math.pow(z, exponents[count4]);
                    for (count4 = 0; count4 < exponents.length - 1; count4++) {
                        temp = Math.pow(w, exponents[count1])
                                * Math.pow(x, exponents[count2])
                                * Math.pow(y, exponents[count3])
                                * Math.pow(z, exponents[count4]);

                        if (Math.abs(u - temp) < Math.abs(u - estimate)) {
                            estimate = temp;
                            exp1 = exponents[count1];
                            exp2 = exponents[count2];
                            exp3 = exponents[count3];
                            exp4 = exponents[count4];
                            bestApprox = temp;
                        }
                    }
                }
            }
        }
        printResult(out, exp1, exp2, exp3, exp4, u, bestApprox);

    }

    /**
     * Prints the exponents, relative error, and best approximation from the
     * charmingTheory method.
     *
     * @param out
     *            Simplewriter to print
     * @param exp1
     *            First exponent from charmingTheory
     * @param exp2
     *            Second exponent from charmingTheory
     * @param exp3
     *            Third exponent from charmingTheory
     * @param exp4
     *            Fourth exponent from charmingTheory
     * @param u
     *            our number we are tyring to approximate with charmingTheory
     * @param bestApprox
     *            the best approximation of u using the four given numbers and a
     *            combination of exponents
     */
    private static void printResult(SimpleWriter out, double exp1, double exp2,
            double exp3, double exp4, double u, double bestApprox) {
        out.println(
                "your exponents are " + exp1 + ", " + exp2 + ", " + exp3 + ", " + exp4);
        out.println("Your relative error is " + Math.abs(u - bestApprox) / u);
        out.println("Your best approximation is: " + bestApprox);

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.println("What would you like your value for u to be? (must be > 0)");
        double u = getPositiveDouble(in, out);
        out.println("What would you like your value of w to be? (must be > 0 and != 1)");
        double w = getPositiveDoubleNotOne(in, out);
        out.println("What would you like your value of w to be? (must be > 0 and != 1)");
        double x = getPositiveDoubleNotOne(in, out);
        out.println("What would you like your value of y to be? (must be > 0 and != 1)");
        double y = getPositiveDoubleNotOne(in, out);
        out.println("What would you like your value of z to be? (must be > 0 and != 1)");
        double z = getPositiveDoubleNotOne(in, out);
        charmingTheory(out, u, w, x, y, z);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
