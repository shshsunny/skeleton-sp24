package main;

import java.util.ArrayList;
import java.util.List;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import wordnet.WordNet;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordnet;

    public HyponymsHandler(WordNet w) {
        super();
        wordnet = w;
    }

    @Override
    public String handle(NgordnetQuery q) {
        // q. startYear, endYear and k are in proj2C
        List<String> hyponyms = new ArrayList<>(wordnet.getHyponyms(q.words()));
        return hyponyms.toString();

    }
}
