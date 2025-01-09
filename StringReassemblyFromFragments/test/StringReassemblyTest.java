import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    /*
     * tests for overlap
     */

    @Test
    //Tests the boundary case of 0 overlap
    public void testOverlapBoundary() {
        String str1 = "helpme";
        String str2 = "donteventry";
        int expected = 0;
        int output = StringReassembly.overlap(str1, str2);
        assertEquals(output, expected);
    }

    @Test
    //Tests routine case of some overlap
    public void testOverlapRoutine() {
        String str1 = "helpme";
        String str2 = "pmember";
        int expected = 3;
        int output = StringReassembly.overlap(str1, str2);
        assertEquals(output, expected);
    }

    /*
     * Tests for combination
     */

    @Test
    //Tests the challenging case of identical strings
    public void testCombinationSameString() {
        String str1 = "o";
        String str2 = "o";
        int overlap = 1;
        String output = StringReassembly.combination(str1, str2, overlap);
        assertEquals(output, str1);
    }

    @Test
    //Tests the routine case of two strings with normal overlap
    public void testCombinationRoutine() {
        String str1 = "oh hello there";
        String str2 = "hello there my friend";
        int overlap = 11;
        String expected = "oh hello there my friend";
        String output = StringReassembly.combination(str1, str2, overlap);
        assertEquals(output, expected);
    }

    /*
     * Tests for addToSetAvoidingSubstrings
     */

    @Test
    //Tests a boundary case where the string exists in the set
    public void testAddToSetAvoidingSubstringsBoundary() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hi");
        strSet.add("Hello");
        strSet.add("Hoi");
        Set<String> strSetExpected = new Set1L<>();
        strSetExpected.add("Hi");
        strSetExpected.add("Hello");
        strSetExpected.add("Hoi");
        String str = "Hoi";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(strSet, strSetExpected);
    }

    @Test
    //Tests a routine case where we add a new string to the set
    public void testAddToSetAvoidingSubstringsRoutine() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hi");
        strSet.add("Hello");
        strSet.add("Hoi");
        Set<String> strSetExpected = new Set1L<>();
        strSetExpected.add("Hi");
        strSetExpected.add("Hello");
        strSetExpected.add("Hoi");
        strSetExpected.add("Nope");
        String str = "Nope";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(strSet, strSetExpected);
    }

    @Test
    //Tests a routine case where we add try to add a new string that is in the set
    public void testAddToSetAvoidingSubstringsRoutine2() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hi");
        strSet.add("Hello");
        strSet.add("Hoi");
        Set<String> strSetExpected = new Set1L<>();
        strSetExpected.add("Hi");
        strSetExpected.add("Hello");
        strSetExpected.add("Hoi");
        String str = "Hoi";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(strSet, strSetExpected);
    }

    @Test
    //Tests a challenging case where we add a new string to the set
    //and a substring of it exists in the set
    public void testAddToSetAvoidingSubstringsChallenge() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hi");
        strSet.add("Hello");
        strSet.add("Hoi");
        Set<String> strSetExpected = new Set1L<>();
        strSetExpected.add("Hi");
        strSetExpected.add("Hello");
        strSetExpected.add("Hoi minoy");
        String str = "Hoi minoy";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(strSet, strSetExpected);
    }

    @Test
    //Tests a challenging case where we add a new string to the set
    //and it is a substring of something in the set already
    public void testAddToSetAvoidingSubstringsChallenge2() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hi");
        strSet.add("Hello");
        strSet.add("Hoi minoy");
        Set<String> strSetExpected = new Set1L<>();
        strSetExpected.add("Hi");
        strSetExpected.add("Hello");
        strSetExpected.add("Hoi minoy");
        String str = "Hoi";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(strSet, strSetExpected);
    }

    /*
     * Tests for linestFromInput
     */

    @Test
    //Checks
    public void linesFromInputTest1() {
        Set<String> expected = new Set1L<>();
        expected.add("Bucks -- Beat");
        expected.add("Go Bucks");
        expected.add("Beat Mich");
        expected.add("Michigan~");
        expected.add("o Bucks -- B");
        SimpleReader in = new SimpleReader1L("data/test1.txt");
        Set<String> strSet = StringReassembly.linesFromInput(in);
        in.close();
        assertEquals(expected, strSet);
    }

    /*
     * Tests for printWithLineSeparators
     */

    @Test

    //Boundary test with an empty string
    public void testPrintWithLineSeparatorsBoundary() {
        SimpleWriter out = new SimpleWriter1L();
        String test = "";
        StringReassembly.printWithLineSeparators(test, out);
        out.close();
    }

    @Test

    //Routine test with only separators
    public void testPrintWithLineSeparatorsOnlySeparators() {
        SimpleWriter out = new SimpleWriter1L();
        String test = "~~~";
        StringReassembly.printWithLineSeparators(test, out);
        out.close();
    }

    @Test

    //Routine test with no separators
    public void testPrintWithLineSeparatorsRoutine() {
        SimpleWriter out = new SimpleWriter1L();
        String test = "This is a long string which won't be split up!";
        StringReassembly.printWithLineSeparators(test, out);
        out.close();
    }

    @Test

    //Routine test of a string with multiple line separators including a double
    //separator
    public void testPrintWithLineSeparatorsRoutine2() {
        SimpleWriter out = new SimpleWriter1L();
        String test = "These~lines~are~~separated!\n";
        StringReassembly.printWithLineSeparators(test, out);
        out.close();
    }
}
