package wordnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.common.collect.HashBiMap;

import edu.princeton.cs.algs4.In;

public class WordNet { // A Directed Acyclic Graph containing synsets as its nodes
    private class Synset {
        // public int id; // this is unnecessary as there should be a map: id -> synset
        public String[] words;
        public String note; // the dictionary description
        // public ArrayList<Integer> idParents; // -1 if no parents
        // public ArrayList<Integer> idChildren;
        public ArrayList<Synset> parents;
        public ArrayList<Synset> children;

        public Synset(String[] w, String n) {
            words = w;
            note = n;
            // idParents = new ArrayList<>(); // may be better implemented with TreeMap?
            // idChildren = new ArrayList<>();
            parents = new ArrayList<>();
            children = new ArrayList<>();
        }
    }

    private ArrayList<Synset> idSynsets; // synsets indexed by id
    private Map<String, ArrayList<Synset>> wordSynsets;

    public WordNet(String synsetsFilename, String hyponymsFilename) {
        // initialization
        idSynsets = new ArrayList<>();
        wordSynsets = new HashMap<>();
        // input & construction
        In in = new In(synsetsFilename);
        // int id = 0;
        while (in.hasNextLine()) {
            String[] segs = in.readLine().split(",");
            String[] words = segs[1].split(" ");
            String note = segs[2];
            Synset newSynset = new Synset(words, note);
            idSynsets.add(newSynset);
            for (String word : words) {
                if (!wordSynsets.containsKey(word))
                    wordSynsets.put(word, new ArrayList<>());
                wordSynsets.get(word).add(newSynset);
            }
            // id++;
            // idSynsets.put(id, new Synset(words, note));
        }

        in = new In(hyponymsFilename);
        while (in.hasNextLine()) {
            String[] segs = in.readLine().split(",");
            int idParent = Integer.parseInt(segs[0]);
            Synset parent = idSynsets.get(idParent);
            for (int i = 1; i < segs.length; ++i) {
                Synset child = idSynsets.get(Integer.parseInt(segs[i]));
                // parent.idChildren.add(idChild);
                // synsets.get(idChild).idParents.add(idParent);
                parent.children.add(child);
                child.parents.add(parent);
            }
        }
    }

    private Set<String> getHyponymsRecursive(Synset synset) {
        // ArrayList<Synset> synsets = wordSynsets.get(word);
        Set<String> set = new TreeSet<>();
        for (String word : synset.words)
            set.add(word);
        for (Synset child : synset.children) {
            set.addAll(getHyponymsRecursive(child));
        }
        return set;
    }

    private Set<String> getAncestorsRecursive(Synset synset) {
        Set<String> set = new TreeSet<>();
        for (String word : synset.words)
            set.add(word);
        for (Synset parent : synset.parents) {
            set.addAll(getAncestorsRecursive(parent));
        }
        return set;
    }

    private Set<String> traversalHelper(List<String> words, boolean flag) {
        // flag == false: getHyponyms. true: getAncestors
        Set<String> res = new TreeSet<>();
        boolean begun = false;
        for (String word : words) {
            List<Synset> synsets = wordSynsets.get(word);
            Set<String> traversal = new TreeSet<>(); // record all synsets traversed (hyponyms or ancestors)
            for (Synset synset : synsets)
                if (!flag)
                    traversal.addAll(getHyponymsRecursive(synset));
                else
                    traversal.addAll(getAncestorsRecursive(synset));
            if (!begun) {
                res.addAll(traversal);
                begun = true;
            } else {
                res.retainAll(traversal);
            }
        }
        return res;
    }

    public Set<String> getHyponyms(List<String> words) {
        return traversalHelper(words, false);
    }

    public Set<String> getAncestors(List<String> words) {
        return traversalHelper(words, true);
    }
}
