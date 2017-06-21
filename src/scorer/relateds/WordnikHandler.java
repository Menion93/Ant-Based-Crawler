package scorer.relateds;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import net.jeremybrooks.knicker.AccountApi;
import net.jeremybrooks.knicker.Knicker.RelationshipType;
import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordApi;
import net.jeremybrooks.knicker.dto.Related;
import net.jeremybrooks.knicker.dto.TokenStatus;

public class WordnikHandler {



	public WordnikHandler(){
		checkKey();
	}

	/**
	 * retrieves relateds (specified in related input), with relative frequency, of the input word
	 * @param word
	 * @param related -> [synonym|hyperonym|hyponym|equivalent]
	 * @return
	 */
	public Map<String, Double> getRelatedFreqParallel(String word, String related) {
		checkKey();

		Map<String, Double> relatedFreq = new HashMap<>();
		List<Related> listRelateds;
		try {
			switch (related) {
			case "synonym": listRelateds = WordApi.related(word, false, EnumSet.of(RelationshipType.synonym),100);
				break;
				
			case "hyperonym": listRelateds = WordApi.related(word, false, EnumSet.of(RelationshipType.hypernym),100);
				break;

			case "hyponym": listRelateds = WordApi.related(word, false, EnumSet.of(RelationshipType.hyponym),100);
				break;

			case "equivalent": listRelateds = WordApi.related(word, false, EnumSet.of(RelationshipType.equivalent),100);
				break;

			default: listRelateds = null;
				break;
			}
			relatedFreq = parallelExec(listRelateds);
		} catch (KnickerException e) { e.printStackTrace();	}
		return relatedFreq;

	}

	/**
	 * controls if the key is valid
	 */
	private void checkKey() {
		// use your API key here
		System.setProperty("WORDNIK_API_KEY", "5661784b547b335408004d7156724d060c42de5ca8f2a46b3");

		try {
			// check the status of the API key
			TokenStatus status = AccountApi.apiTokenStatus();
			if (status.isValid())
				System.out.println("API key is valid.");
			else {
				System.out.println("API key is invalid!");
				System.exit(1);
			}
		} catch (KnickerException e) { e.printStackTrace();	}
	}
	
	/**
	 * retrieves relateds using LEF and asynchronous functions
	 * @param listRelateds
	 * @return
	 */
	private Map<String, Double> parallelExec(List<Related> listRelateds){
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		BlockingQueue<Related> queueRelateds = new LinkedBlockingQueue<>(listRelateds);
		Map<String, Double> relatedsFreq = new HashMap<>();

		ExecutorService pool = Executors.newFixedThreadPool(availableProcessors);
		CompletionService<Map<String, Double>> taskCompletionService = new ExecutorCompletionService<Map<String, Double>>(pool);

		for(int i = 0; i < availableProcessors; i++)
			taskCompletionService.submit(new FrequencyTask(queueRelateds));
		for(int j = 0; j < availableProcessors; j++)
			try {
				relatedsFreq.putAll(taskCompletionService.take().get());
			} catch (InterruptedException | ExecutionException e) {	e.printStackTrace();}
		pool.shutdown();
		return relatedsFreq;
	}

	public List<String> getHypernyms(String word) throws KnickerException {
		List<String> hypernyms = new LinkedList<>();
		List<Related> listRelateds = WordApi.related(word, false, EnumSet.of(RelationshipType.hypernym),100);

		for(Related rel : listRelateds){
			for(String hyp : rel.getWords())
				hypernyms.add(hyp);
		}

		return hypernyms;
	}

	public List<String> getHyponyms(String word) throws KnickerException {
		List<String> hyponyms = new LinkedList<>();
		List<Related> listRelateds = WordApi.related(word, false, EnumSet.of(RelationshipType.hypernym),100);

		for(Related rel : listRelateds){
			for(String hyp : rel.getWords())
				hyponyms.add(hyp);
		}

		return hyponyms;
	}

	public List<String> getSynonyms(String word) throws KnickerException {
		List<String> synonyms = new LinkedList<>();
		List<Related> listRelateds = WordApi.related(word, false, EnumSet.of(RelationshipType.hypernym),100);

		for(Related rel : listRelateds){
			for(String hyp : rel.getWords())
				synonyms.add(hyp);
		}

		return synonyms;
	}
}
