package scorer;


import antMain.Configuration;
import net.jeremybrooks.knicker.Knicker;
import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordApi;
import scorer.relateds.WordnikHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Created by Andrea on 20/06/2017.
 */
public class QueryAdapter {

    private String query;
    private String[] wordsInQuery;

    private List<String> synonyms;
    private List<String> hyponyms;
    private List<String> hypernyms;

    private WordnikHandler wordnik;

    private String stopWordsFile = "stopwords.txt";
    private List<String> stopWords;


    public QueryAdapter(String query) throws FileNotFoundException {
        wordsInQuery = query.trim().replace(",", "").split(" ");
        this.wordnik = new WordnikHandler();

        this.query = query;

        Configuration conf = new Configuration();
        Scanner in = new Scanner(new FileReader(conf.getRootFolder() + stopWordsFile));
        stopWords = new LinkedList<>();

        while(in.hasNext()){
             stopWords.add(in.nextLine());
        }
        in.close();
    }

    // Java do not let me to passing values by reference like c++ & or c# ref :(

    public List<String> getHypernyms() throws KnickerException {
        if(hypernyms == null) {
            hypernyms = new ArrayList<>();
            for (String word : wordsInQuery) {
                if(!stopWords.contains(word))
                    hypernyms.addAll(wordnik.getHypernyms(word));
            }
        }
        return hypernyms;
    }
    public List<String> getHyponyms() throws KnickerException {
        if(hyponyms == null) {
            hyponyms = new ArrayList<>();
            for (String word : wordsInQuery) {
                if(!stopWords.contains(word))
                    hyponyms.addAll(wordnik.getHyponyms(word));
            }
        }
        return hyponyms;
    }

    public List<String> getSynonyms() throws KnickerException {
        if(synonyms == null) {
            synonyms = new ArrayList<>();
            for (String word : wordsInQuery) {
                if(!stopWords.contains(word))
                    synonyms.addAll(wordnik.getSynonyms(word));
            }
        }
        return synonyms;
    }

    public String[] getWordsInQuery() {
        return wordsInQuery;
    }


    public String getQuery() {
        return query;
    }
}
