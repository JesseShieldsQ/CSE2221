import java.util.Comparator;

/**
 * Comparator class to compare strings alphabetically.
 */
public final class AlphabetComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }

    /**
     * No-argument constructor--private to prevent instantiation.
     */
    AlphabetComparator() {
        //No code needed here
    }
}
