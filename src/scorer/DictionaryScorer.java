package scorer;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graph.NodePage;
import scorer.synonyms.WordnikHandler;

/**
 * Created by Andrea on 05/03/2017.
 */
public class DictionaryScorer implements Scorer
{
    public DictionaryScorer()
    {

    }

    public double predictScore(NodePage nodePage, String query)
    {
    	double tf_idf, score = 0;
    	double numberDocuments = 1000000;		//TODO calculate a better value
    	
    	String content = nodePage.getContent();
    	double dimNode = nodePage.getSizeWords();

    	WordnikHandler wordnik = new WordnikHandler();
    	Map<String, Double> synonymsFreq = wordnik.getSynonymsFreq(query);
    	Map<String, Double> equivalentsFreq = wordnik.getEquivalentsFreq(query);
    	Map<String, Double> hyponymsFreq = wordnik.getHyponymsFreq(query);
    	Map<String, Double> hypernymsFreq = wordnik.getHypernymsFreq(query);
    	
    	for (String synonym : synonymsFreq.keySet()) {
    		tf_idf = (countOccurrences(content, synonym) / dimNode) * Math.log(numberDocuments / synonymsFreq.get(synonym));
    		score = score + tf_idf;
		}
    	
    	for (String equivalent : equivalentsFreq.keySet()) {
    		tf_idf = (countOccurrences(content, equivalent) / dimNode) * 
    				Math.log(1000000 / equivalentsFreq.get(equivalent));
    		score = score + tf_idf;
		}
    	
    	for (String hyponym : hyponymsFreq.keySet()) {
    		tf_idf = (countOccurrences(content, hyponym) / dimNode) * Math.log(numberDocuments / hyponymsFreq.get(hyponym));
    		score = score + tf_idf;
		}
    	
    	for (String hypernym : hypernymsFreq.keySet()) {
    		tf_idf = (countOccurrences(content, hypernym) / dimNode) * Math.log(numberDocuments / hypernymsFreq.get(hypernym));
    		score = score + tf_idf;
		}
        return score;
    }
    
    /**
     * counts how many times document contains word
     * @param document
     * @param word
     * @return
     */
    private double countOccurrences(String document, String word){
    	double occurrences = 0;
    	Pattern pattern = Pattern.compile(word);
		Matcher matcher = pattern.matcher(document);
		while(matcher.find())
			occurrences++;
    	return occurrences;
    }
}
