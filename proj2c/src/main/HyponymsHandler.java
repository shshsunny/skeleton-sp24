package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;
import wordnet.WordNet;

public class HyponymsHandler extends NgordnetQueryHandler {
    private NGramMap ngm;
    private WordNet wordnet;

    public HyponymsHandler(NGramMap n, WordNet w) {
        super();
        ngm = n;
        wordnet = w;
    }

    @Override
    public String handle(NgordnetQuery q) {
        // q. startYear, endYear and k are in proj2C

        List<String> traversal = new ArrayList<>(
                q.ngordnetQueryType() == NgordnetQueryType.HYPONYMS ? wordnet.getHyponyms(q.words())
                        : wordnet.getAncestors(q.words()));
        if (q.k() == 0) {
            return traversal.toString();
        } else {
            Map<String, Double> counts = new HashMap<>();
            for (String word : traversal) {
                counts.put(word, ngm.countHistory(word, q.startYear(), q.endYear()).values().stream()
                        .mapToDouble(Double::doubleValue).sum());
            }
            List<Map.Entry<String, Double>> array = new ArrayList<>(counts.entrySet());
            array.sort(new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                    return (int) Math.signum(-(o1.getValue() - o2.getValue()));
                }
            });
            List<String> res = new ArrayList<>();
            for (int i = 0; i < array.size() && i < q.k(); ++i) {
                Map.Entry<String, Double> entry = array.get(i);
                if (entry.getValue() == 0.0)
                    break;
                res.add(entry.getKey());
            }
            res.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            return res.toString();
        }

    }
}
