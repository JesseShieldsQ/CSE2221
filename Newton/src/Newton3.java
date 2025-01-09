import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Takes some input from the user for a number and an error estimate, computes
 * the square root using Newton iteration, prints the result, then loops until
 * quit.
 *
 * @author Jesse Shields
 *
 */
public final class Newton3 {
    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton3() {

    }

    /**
     * Computes estimate of square root of x to within relative error e.
     * Accounts for input of 0.
     *
     * @param x
     *            positive number to compute square root of
     *
     * @param e
     *            positive number for error estimate
     * @return estimate of square root
     */
    private static double sqrt(double x, double e) {
        double r = x;
        double err = e;
        if (r != 0) {

            while (Math.abs((r * r) - x) / x >= (err * err)) {
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
         * square root of and a relative error to compute to.
         */
        boolean cont = true;
        while (cont) {
            out.println("What number would you like to find the root of?: ");
            double num = in.nextDouble();
            out.println("What relative error would you like to compute to?: ");
            double e = in.nextDouble();
            double res = sqrt(num, e);
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
