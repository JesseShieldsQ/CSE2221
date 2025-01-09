import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Takes some input from the user for a number, computes the square root using
 * Newton iteration, prints the result, then loops until quit.
 *
 * @author Jesse Shields
 *
 */
public final class Newton2 {
    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton2() {

    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     * Accounts for input of 0.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        double r = x;
        final double e = 0.01;
        if (r != 0) {

            while (Math.abs((r * r) - x) / x >= (e * e)) {
                r = (r + (x / r)) / 2;
            }
        }
        return r;
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
        /*
         * Loops until quit, and prompts user for input on a number to find the
         * square root of.
         */
        boolean cont = true;
        while (cont) {
            out.println("What number would you like to find the root of?: ");
            double res = sqrt(in.nextDouble());
            out.println("Your number's square root is " + res);
            out.println("Would you like to continue? (Y/N)");
            if (in.nextLine().toLowerCase().equals("n")) {
                cont = false;
            }
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
