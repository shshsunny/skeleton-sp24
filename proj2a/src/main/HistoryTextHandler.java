package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap myMap;

    public HistoryTextHandler(NGramMap map) {
        super();
        myMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            TimeSeries ts = myMap.weightHistory(word, startYear, endYear);

            /*
             * List<Integer> years = ts.years();
             * List<Double> data = ts.data();
             */
            sb.append(word + ": " + ts.toString() + "\n");
        }
        return sb.toString();
    }

    /*
     * private String formatSeries(List<?> years, List<?> data) {
     * StringBuilder sb = new StringBuilder("{");
     * for(int i=0;i<years.size();++i) sb.append()
     * }
     */
}
