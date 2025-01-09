import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities that could be used with RSA cryptosystems.
 *
 * @author Jesse Shields
 *
 */
public final class CryptoUtilities {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CryptoUtilities() {
    }

    /**
     * Useful constant, not a magic number: 3.
     */
    private static final int THREE = 3;

    /**
     * Pseudo-random number generator.
     */
    private static final Random GENERATOR = new Random1L();

    /**
     * Returns a random number uniformly distributed in the interval [0, n].
     *
     * @param n
     *            top end of interval
     * @return random number in interval
     * @requires n > 0
     * @ensures <pre>
     * randomNumber = [a random number uniformly distributed in [0, n]]
     * </pre>
     */
    public static NaturalNumber randomNumber(NaturalNumber n) {
        assert !n.isZero() : "Violation of: n > 0";
        final int base = 10;
        NaturalNumber result;
        int d = n.divideBy10();
        if (n.isZero()) {
            /*
             * Incoming n has only one digit and it is d, so generate a random
             * number uniformly distributed in [0, d]
             */
            int x = (int) ((d + 1) * GENERATOR.nextDouble());
            result = new NaturalNumber2(x);
            n.multiplyBy10(d);
        } else {
            /*
             * Incoming n has more than one digit, so generate a random number
             * (NaturalNumber) uniformly distributed in [0, n], and another
             * (int) uniformly distributed in [0, 9] (i.e., a random digit)
             */
            result = randomNumber(n);
            int lastDigit = (int) (base * GENERATOR.nextDouble());
            result.multiplyBy10(lastDigit);
            n.multiplyBy10(d);
            if (result.compareTo(n) > 0) {
                /*
                 * In this case, we need to try again because generated number
                 * is greater than n; the recursive call's argument is not
                 * "smaller" than the incoming value of n, but this recursive
                 * call has no more than a 90% chance of being made (and for
                 * large n, far less than that), so the probability of
                 * termination is 1
                 */
                result = randomNumber(n);
            }
        }
        return result;
    }

    /**
     * Finds the greatest common divisor of n and m.
     *
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures n = [greatest common divisor of #n and #m]
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {

        /*
         * Use Euclid's algorithm; in pseudocode: if m = 0 then GCD(n, m) = n
         * else GCD(n, m) = GCD(m, n mod m)
         */
        NaturalNumber remainder = new NaturalNumber2();

        if (!m.isZero()) {
            //Gets the reaminder of n and m
            remainder = n.divide(m);
            //Applies Euclid's algorithm, recursively loops and continues dividing
            //until m = 0
            reduceToGCD(m, remainder);
            //Transfers the GCD from m to n, clearing m
            n.transferFrom(m);
        }

    }

    /**
     * Reports whether n is even.
     *
     * @param n
     *            the number to be checked
     * @return true iff n is even
     * @ensures isEven = (n mod 2 = 0)
     */
    public static boolean isEven(NaturalNumber n) {

        boolean even = false;
        //Extracts last digit of n
        int digit = n.divideBy10();
        //If digit is even, sets flag to true
        if (digit % 2 == 0) {
            even = true;
        }
        //Put digit back into n
        n.multiplyBy10(digit);

        //Returns flag
        return even;
    }

    /**
     * Updates n to its p-th power modulo m.
     *
     * @param n
     *            number to be raised to a power
     * @param p
     *            the power
     * @param m
     *            the modulus
     * @updates n
     * @requires m > 1
     * @ensures n = #n ^ (p) mod m
     */
    public static void powerMod(NaturalNumber n, NaturalNumber p, NaturalNumber m) {
        assert m.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: m > 1";

        /*
         * Use the fast-powering algorithm as previously discussed in class,
         * with the additional feature that every multiplication is followed
         * immediately by "reducing the result modulo m"
         */
        //Initializes
        final int one = 1;
        final int two = 2;
        NaturalNumber nCopy = new NaturalNumber2();
        NaturalNumber nOrig = new NaturalNumber2(n);
        NaturalNumber oneNN = new NaturalNumber2(one);
        NaturalNumber twoNN = new NaturalNumber2(two);
        NaturalNumber power = new NaturalNumber2();
        NaturalNumber remainder = new NaturalNumber2();
        //If the exponent is zero, the output is always 1
        if (p.isZero()) {
            n.copyFrom(oneNN);
            //If p>1, uses fast powering algorithm until p is 1
        } else if (p.compareTo(oneNN) > 0) {
            power.copyFrom(p);
            power.divide(twoNN);
            powerMod(n, power, m);
            nCopy.copyFrom(n);
            n.multiply(nCopy);
            //if p is odd (should only happen first time with int math),
            //multiply by original at the end
            if (!isEven(p)) {
                //if p was odd, we need to multiply by the original value
                n.multiply(nOrig);
            }
        }
        //Reduces the result modulo m
        remainder = n.divide(m);
        n.copyFrom(remainder);
    }

    /**
     * Reports whether w is a "witness" that n is composite, in the sense that
     * either it is a square root of 1 (mod n), or it fails to satisfy the
     * criterion for primality from Fermat's theorem.
     *
     * @param w
     *            witness candidate
     * @param n
     *            number being checked
     * @return true iff w is a "witness" that n is composite
     * @requires n > 2 and 1 < w < n - 1
     * @ensures <pre>
     * isWitnessToCompositeness =
     *     (w ^ 2 mod n = 1)  or  (w ^ (n-1) mod n /= 1)
     * </pre>
     */
    public static boolean isWitnessToCompositeness(NaturalNumber w, NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(2)) > 0 : "Violation of: n > 2";
        assert (new NaturalNumber2(1)).compareTo(w) < 0 : "Violation of: 1 < w";
        n.decrement();
        assert w.compareTo(n) < 0 : "Violation of: w < n - 1";
        n.increment();
        //Initializes all our variables
        boolean compWit = false;
        NaturalNumber oneNN = new NaturalNumber2(1);
        NaturalNumber twoNN = new NaturalNumber2(2);
        NaturalNumber test1 = new NaturalNumber2(w);
        NaturalNumber test2 = new NaturalNumber2(w);
        NaturalNumber nDec = new NaturalNumber2(n);
        //Sets nDec to n-1 for our test
        nDec.decrement();
        //Sets test1 to w^2 mod n
        powerMod(test1, twoNN, n);
        //Sets test 2 to w^(n-1) mod n
        powerMod(test2, nDec, n);
        //Checks if either condition for witnessing is true
        if (test1.compareTo(oneNN) == 0 || test2.compareTo(oneNN) != 0) {
            compWit = true;
        }
        //returns flag
        return compWit;

    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime1(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        boolean isPrime;
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            isPrime = true;
        } else if (isEven(n)) {
            /*
             * evens are composite
             */
            isPrime = false;
        } else {
            /*
             * odd n >= 5: simply check whether 2 is a witness that n is
             * composite (which works surprisingly well :-)
             */
            isPrime = !isWitnessToCompositeness(new NaturalNumber2(2), n);
        }
        return isPrime;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 4
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime2(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use the ability to generate random numbers (provided by the
         * randomNumber method above) to generate several witness candidates --
         * say, 10 to 50 candidates -- guessing that n is prime only if none of
         * these candidates is a witness to n being composite (based on fact #3
         * as described in the project description); use the code for isPrime1
         * as a guide for how to do this, and pay attention to the requires
         * clause of isWitnessToCompositeness
         */
        final int fifty = 50;
        final int four = 4;
        final int two = 2;
        boolean prime2 = false;
        NaturalNumber twoNN = new NaturalNumber2(two);
        NaturalNumber fourNN = new NaturalNumber2(four);
        NaturalNumber nGen = new NaturalNumber2(n);
        NaturalNumber random = new NaturalNumber2();

        //Our witness candidates must be in the range [2,n-2], so we subtract 4 from
        //n for our candidate, and then add 2 back after generation to account for the
        //generator lower bound being 0
        nGen.subtract(fourNN);
        //for loop to check each of 50 candidates
        for (int i = 0; i < fifty; i++) {
            //Generate a random number in range [0,n-4] and then add 2 so the
            //range is [2,n-2]
            random = randomNumber(nGen);
            random.add(twoNN);
            if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
                /*
                 * 2 and 3 are primes
                 */
                prime2 = true;
            } else if (isEven(n)) {
                /*
                 * evens are composite
                 */
                prime2 = false;
            } else {
                /*
                 * odd n >= 5: checks whether our random number in [2,n-2] is
                 * witness to compositness.
                 */
                prime2 = !isWitnessToCompositeness(random, n);
            }
        }
        return prime2;

    }

    /**
     * Generates a likely prime number at least as large as some given number.
     *
     * @param n
     *            minimum value of likely prime
     * @updates n
     * @requires n > 4
     * @ensures n >= #n and [n is very likely a prime number]
     */
    public static void generateNextLikelyPrime(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        NaturalNumber twoNN = new NaturalNumber2(2);

        /*
         * Use isPrime2 to check numbers, starting at n and increasing through
         * the odd numbers only, until n is likely prime
         */

        while (!isPrime2(n)) {
            if (isEven(n)) {
                n.increment();
            } else {
                n.add(twoNN);
            }
        }
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
         * Sanity check of randomNumber method -- just so everyone can see how
         * it might be "tested"
         */
        final int testValue = 17;
        final int testSamples = 100000;
        NaturalNumber test = new NaturalNumber2(testValue);
        int[] count = new int[testValue + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < testSamples; i++) {
            NaturalNumber rn = randomNumber(test);
            assert rn.compareTo(test) <= 0 : "Help!";
            count[rn.toInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            out.println("count[" + i + "] = " + count[i]);
        }
        out.println(
                "  expected value = " + (double) testSamples / (double) (testValue + 1));

        /*
         * Check user-supplied numbers for primality, and if a number is not
         * prime, find the next likely prime after it
         */
        while (true) {
            out.print("n = ");
            NaturalNumber n = new NaturalNumber2(in.nextLine());
            if (n.compareTo(new NaturalNumber2(2)) < 0) {
                out.println("Bye!");
                break;
            } else {
                if (isPrime1(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime1.");
                } else {
                    out.println(n + " is a composite number" + " according to isPrime1.");
                }
                if (isPrime2(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime2.");
                } else {
                    out.println(n + " is a composite number" + " according to isPrime2.");
                    generateNextLikelyPrime(n);
                    out.println("  next likely prime is " + n);
                }
            }
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
