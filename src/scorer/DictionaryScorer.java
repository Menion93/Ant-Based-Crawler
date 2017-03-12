package scorer;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graph.NodePage;
import scorer.relateds.WordnikHandler;

/**
 * Created by Andrea on 05/03/2017.
 */
public class DictionaryScorer extends Scorer {
	
    public DictionaryScorer(String query){
		super(query);
    }
    
    public double predictScore(NodePage nodePage){
    	String content = nodePage.getContent();
    	double dimNode = nodePage.getSizeWords();
    	
//    	Future<Double> futSynonymsScore = tf_idfRelatedAsync(content, dimNode, "synonym");
//    	Future<Double> futHyperonymsScore = tf_idfRelatedAsync(content, dimNode, "hyperonym");
//    	Future<Double> futHyponymsScore = tf_idfRelatedAsync(content, dimNode, "hyponym");
//    	Future<Double> futEquivalentsScore = tf_idfRelatedAsync(content, dimNode, "equivalent");
//
//    	try {
//			score = futSynonymsScore.get() + futHyperonymsScore.get() + futHyponymsScore.get() + futEquivalentsScore.get();
//		} catch (InterruptedException | ExecutionException e) {	e.printStackTrace(); }
//        return score;
        
    	return tf_idfRelated(content, dimNode, "synonym") + tf_idfRelated(content, dimNode, "hyperonym") + 
    			tf_idfRelated(content, dimNode, "hyponym") + tf_idfRelated(content, dimNode, "equivalent");
    }
    
	private Future<Double> tf_idfRelatedAsync(String content, double dimNode, String related){
		return CompletableFuture.supplyAsync(() -> this.tf_idfRelated(content, dimNode, related));
	}
    
	/**
	 * calculates the tf-idf for each word related to the specified related and sums them
	 * @param content
	 * @param dimNode
	 * @param related -> [synonym|hyperonym|hyponym|equivalent]
	 * @return
	 */
    private double tf_idfRelated(String content, double dimNode, String related){
    	WordnikHandler wordnik = new WordnikHandler();
    	double numberDocuments = 1000000;	//TODO calculate a better value
    	double tf_idf = 0;

    	Map<String, Double> relatedFreq = wordnik.getRelatedFreqParallel(query, related);
    	for (String rel : relatedFreq.keySet()){
    		tf_idf = tf_idf + ((countOccurrences(content, rel) / dimNode) * Math.log(numberDocuments / relatedFreq.get(rel)));
//    		System.out.println(tf_idf);
    	}
    	return tf_idf;
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
