import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Jesse Shields
 *
 */
public class CryptoUtilitiesTest {

    //Lower boundary test
    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    //Routine test
    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(21);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    //Challenging test where the GCD is 1
    @Test
    public void testReduceToGCD_41_9() {
        NaturalNumber n = new NaturalNumber2(41);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber m = new NaturalNumber2(9);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    //Challenging test where both numbers are the same
    @Test
    public void testReduceToGCD_10_10() {
        NaturalNumber n = new NaturalNumber2(10);
        NaturalNumber nExpected = new NaturalNumber2(10);
        NaturalNumber m = new NaturalNumber2(10);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    //Routine test with larger number as second parameter
    @Test
    public void testReduceToGCD_21_30() {
        NaturalNumber n = new NaturalNumber2(21);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(30);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isEven
     */

    //Boundary test for lowest even number, and boundary of NN
    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    //Boundary test for lowest odd number
    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    //Routine test for even non-0
    @Test
    public void testIsEven_20() {
        NaturalNumber n = new NaturalNumber2(20);
        NaturalNumber nExpected = new NaturalNumber2(20);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    //Routine test for odd non-boundary
    @Test
    public void testIsEven_21() {
        NaturalNumber n = new NaturalNumber2(21);
        NaturalNumber nExpected = new NaturalNumber2(21);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests of powerMod
     */

    //Boundary test for lower boundary
    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    //Routine test of 17,18,19, output is 1
    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber pExpected = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    //Routine test where output isn't 1
    @Test
    public void testPowerMod_15_6_6() {
        NaturalNumber n = new NaturalNumber2(15);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber p = new NaturalNumber2(6);
        NaturalNumber pExpected = new NaturalNumber2(6);
        NaturalNumber m = new NaturalNumber2(6);
        NaturalNumber mExpected = new NaturalNumber2(6);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isWitnessToCompositeness
     */
    //Routine test where result is true
    @Test
    public void testIsWitnessToCompositeness_5_20() {
        NaturalNumber w = new NaturalNumber2(5);
        NaturalNumber wExpected = new NaturalNumber2(5);
        NaturalNumber n = new NaturalNumber2(20);
        NaturalNumber nExpected = new NaturalNumber2(20);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    //Routine test where result is false
    @Test
    public void testIsWitnessToCompositeness_3_7() {
        NaturalNumber w = new NaturalNumber2(3);
        NaturalNumber wExpected = new NaturalNumber2(3);
        NaturalNumber n = new NaturalNumber2(7);
        NaturalNumber nExpected = new NaturalNumber2(7);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    //Lower boundary case where n = 4
    @Test
    public void testIsWitnessToCompositeness_2_4() {
        NaturalNumber w = new NaturalNumber2(2);
        NaturalNumber wExpected = new NaturalNumber2(2);
        NaturalNumber n = new NaturalNumber2(4);
        NaturalNumber nExpected = new NaturalNumber2(4);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }
    /*
     * Tests of isPrime1
     */

    //Boundary test of n=2, smallest legal prime
    @Test
    public void testIsPrime1_2() {
        NaturalNumber w = new NaturalNumber2(2);
        NaturalNumber wExpected = new NaturalNumber2(2);
        boolean result = CryptoUtilities.isPrime1(w);
        assertEquals(wExpected, w);
        assertEquals(true, result);
    }

    //Routine test of a small prime
    @Test
    public void testIsPrime1_3() {
        NaturalNumber w = new NaturalNumber2(3);
        NaturalNumber wExpected = new NaturalNumber2(3);
        boolean result = CryptoUtilities.isPrime1(w);
        assertEquals(wExpected, w);
        assertEquals(true, result);
    }

    //Routine test of a small non-prime
    @Test
    public void testIsPrime1_10() {
        NaturalNumber w = new NaturalNumber2(10);
        NaturalNumber wExpected = new NaturalNumber2(10);
        boolean result = CryptoUtilities.isPrime1(w);
        assertEquals(wExpected, w);
        assertEquals(false, result);
    }

    //Challenging test of a large prime
    @Test
    public void testIsPrime1_54323() {
        NaturalNumber w = new NaturalNumber2(54323);
        NaturalNumber wExpected = new NaturalNumber2(54323);
        boolean result = CryptoUtilities.isPrime1(w);
        assertEquals(wExpected, w);
        assertEquals(true, result);
    }

    //Challenging test of a large non-prime
    @Test
    public void testIsPrime1_54321() {
        NaturalNumber w = new NaturalNumber2(54321);
        NaturalNumber wExpected = new NaturalNumber2(54321);
        boolean result = CryptoUtilities.isPrime1(w);
        assertEquals(wExpected, w);
        assertEquals(false, result);
    }
    /*
     * Tests of isPrime2
     */

    //Boundary test of n=5, smallest legal prime
    @Test
    public void testIsPrime2_5() {
        NaturalNumber w = new NaturalNumber2(5);
        NaturalNumber wExpected = new NaturalNumber2(5);
        boolean result = CryptoUtilities.isPrime2(w);
        assertEquals(wExpected, w);
        assertEquals(true, result);
    }

    //Routine test of a small non-prime
    @Test
    public void testIsPrime2_10() {
        NaturalNumber w = new NaturalNumber2(10);
        NaturalNumber wExpected = new NaturalNumber2(10);
        boolean result = CryptoUtilities.isPrime2(w);
        assertEquals(wExpected, w);
        assertEquals(false, result);
    }

    //Challenging test of a large prime
    @Test
    public void testIsPrime2_54323() {
        NaturalNumber w = new NaturalNumber2(54323);
        NaturalNumber wExpected = new NaturalNumber2(54323);
        boolean result = CryptoUtilities.isPrime2(w);
        assertEquals(wExpected, w);
        assertEquals(true, result);
    }

    //Challenging test of a large non-prime
    @Test
    public void testIsPrime2_54321() {
        NaturalNumber w = new NaturalNumber2(54321);
        NaturalNumber wExpected = new NaturalNumber2(54321);
        boolean result = CryptoUtilities.isPrime2(w);
        assertEquals(wExpected, w);
        assertEquals(false, result);
    }
    /*
     * Tests of generateNextLikelyPrime
     */

    //Boundary case of smallest input n = 5
    @Test
    public void testGenerateNextLikelyPrime_5() {
        NaturalNumber n = new NaturalNumber2(5);
        NaturalNumber nExpected = new NaturalNumber2(5);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(n, nExpected);
    }

    //Routine test of an even number where n+1 isn't prime
    @Test
    public void testGenerateNextLikelyPrime_8() {
        NaturalNumber n = new NaturalNumber2(8);
        NaturalNumber nExpected = new NaturalNumber2(11);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(n, nExpected);
    }

    //Routine test of a larger odd number where n+2 isn't prime
    @Test
    public void testGenerateNextLikelyPrime_1233() {
        NaturalNumber n = new NaturalNumber2(1233);
        NaturalNumber nExpected = new NaturalNumber2(1237);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(n, nExpected);
    }

    //Challenging case of a large number whose next prime is 19 away
    @Test
    public void testGenerateNextLikelyPrime_10000000() {
        NaturalNumber n = new NaturalNumber2(10000000);
        NaturalNumber nExpected = new NaturalNumber2(10000019);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(n, nExpected);
    }
}
