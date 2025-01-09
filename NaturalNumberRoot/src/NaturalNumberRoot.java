import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program with implementation of {@code NaturalNumber} secondary operation
 * {@code root} implemented as static method.
 *
 * @author Jesse Shields
 *
 */
public final class NaturalNumberRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NaturalNumberRoot() {
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {
        assert n != null : "Violation of: n is  not null";
        assert r >= 2 : "Violation of: r >= 2";
        //Initializes NN 1 variable to deal with magic numbers.
        NaturalNumber one = new NaturalNumber2(1);
        //Initializes NN 2 variable to deal with magic numbers.
        NaturalNumber two = new NaturalNumber2(2);
        //Initializes NN variable for high guess
        NaturalNumber highEnough = new NaturalNumber2(n);
        //Initializes NN variable for low guess
        NaturalNumber lowEnough = new NaturalNumber2();
        //Initializes NN variable for the difference between two numbers
        NaturalNumber difference = new NaturalNumber2(2);
        //Initializes NN variable for our guess
        NaturalNumber guess = new NaturalNumber2();
        //Initializes a NN variable to test if our guess is between n^r and (n+1)^r
        NaturalNumber powTest = new NaturalNumber2();

        //Increments highEnough to get n+1 as our maximum
        highEnough.increment();

        //While loop to iterate until the condition of n^r <= n# < (n+1)^r is met,
        //which is satisfied when the difference variable is equal to or less than 1.
        while (difference.compareTo(one) > 0) {

            //Copies the value of highEnough into guess, adds lowEnough,
            //and divides by 2 to find the middle point between them.
            guess.copyFrom(highEnough);
            guess.add(lowEnough);
            guess.divide(two);

            //Copies the value for guess and then executes (guess)^(r).
            powTest.copyFrom(guess);
            powTest.power(r);

            //Checks our guess against n. If n is lower, we make guess our new maximum.
            //If not, we make guess our new minimum.
            if (n.compareTo(powTest) < 0) {
                highEnough.copyFrom(guess);
            } else {
                lowEnough.copyFrom(guess);
            }
            //Sets variable difference to the new difference between our max and min
            //for our loop condition.
            difference.copyFrom(highEnough);
            difference.subtract(lowEnough);
        }
        //After the while loop, copies n to our minimum which is our answer.
        n.copyFrom(lowEnough);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final String[] numbers = { "0", "1", "13", "1024", "189943527", "0", "1", "13",
                "4096", "189943527", "0", "1", "13", "1024", "189943527", "82", "82",
                "82", "82", "82", "9", "27", "81", "243", "143489073", "2147483647",
                "2147483648", "9223372036854775807", "9223372036854775808",
                "618970019642690137449562111", "162259276829213363391578010288127",
                "170141183460469231731687303715884105727" };
        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15, 2, 3, 4,
                5, 15, 2, 3, 4, 5, 15, 2, 2, 3, 3, 4, 5, 6 };
        final String[] results = { "0", "1", "3", "32", "13782", "0", "1", "2", "16",
                "574", "0", "1", "1", "1", "3", "9", "4", "3", "2", "1", "3", "3", "3",
                "3", "3", "46340", "46340", "2097151", "2097152", "4987896", "2767208",
                "2353973" };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber2(numbers[i]);
            NaturalNumber r = new NaturalNumber2(results[i]);
            root(n, roots[i]);
            if (n.equals(r)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i] + ", "
                        + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root(" + numbers[i] + ", "
                        + roots[i] + ") expected <" + results[i] + "> but was <" + n
                        + ">");
            }
        }

        out.close();
    }

}
