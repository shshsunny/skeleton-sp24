package ngrams;

import java.util.Collection;
import java.util.HashMap;

import org.checkerframework.checker.units.qual.s;

import edu.princeton.cs.algs4.In;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private HashMap<String, TimeSeries> wordRecords;
    private TimeSeries countRecords;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordRecords = new HashMap<>();
        In in1 = new In(wordsFilename);
        while (in1.hasNextLine()) {
            String[] segs = in1.readLine().split("\t");
            if (!wordRecords.containsKey(segs[0]))
                wordRecords.put(segs[0], new TimeSeries());
            wordRecords.get(segs[0]).put(Integer.parseInt(segs[1]), Double.parseDouble(segs[2]));
        }

        countRecords = new TimeSeries();
        In in2 = new In(countsFilename);
        while (in2.hasNextLine()) {
            String[] segs = in2.readLine().split(",");
            countRecords.put(Integer.parseInt(segs[0]), Double.parseDouble(segs[1]));
        }
        System.out.println(wordRecords.size() + " " + countRecords.size());
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both
     * ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's
     * TimeSeries. In other
     * words, changes made to the object returned by this function should not also
     * affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the
     * data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordRecords.containsKey(word))
            return new TimeSeries();
        return new TimeSeries(wordRecords.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a
     * link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by
     * this function
     * should not also affect the NGramMap. This is also known as a "defensive
     * copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in
     * all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(countRecords, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD
     * between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files,
     * returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return countHistory(word, startYear, endYear).dividedBy(countRecords);
        /*
         * TimeSeries ts = countHistory(word, startYear, endYear);
         * double tot = 0;
         * for (double v : ts.values())
         * tot += v;
         * for (int y : ts.keySet())
         * ts.put(y, ts.get(y) / tot);
         * return ts;
         */

    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD
     * compared to all
     * words recorded in that year. If the word is not in the data files, returns an
     * empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordRecords.containsKey(word))
            return new TimeSeries();
        return wordRecords.get(word).dividedBy(countRecords);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between
     * STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame,
     * ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
            int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            ts = ts.plus(countHistory(word, startYear, endYear));
        }
        return ts.dividedBy(countRecords);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a
     * word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            ts = ts.plus(countHistory(word));
        }
        return ts.dividedBy(countRecords);
    }

}
