import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        int res = 0;
        for (int i = 0; i < L.size(); ++i)
            res += L.get(i);
        return res;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> M = new ArrayList<Integer>();
        for (int i : L) {
            if (i % 2 == 0)
                M.add(i);
        }
        return M;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> M = new ArrayList<Integer>();
        for (int i : L1) {
            if (L2.contains(i))
                M.add(i);
        }
        return M;
    }

    /**
     * Returns the number of occurrences of the given character in a list of
     * strings.
     */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int res = 0;
        for (String s : words) {
            for (int i = 0; i < s.length(); ++i) {
                if (s.charAt(i) == c)
                    res++;
            }
        }
        return res;
    }
}
