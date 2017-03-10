package scorer.synonyms;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jeremybrooks.knicker.AccountApi;
import net.jeremybrooks.knicker.Knicker.RelationshipType;
import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordApi;
import net.jeremybrooks.knicker.dto.FrequencySummary;
import net.jeremybrooks.knicker.dto.Related;
import net.jeremybrooks.knicker.dto.TokenStatus;

public class WordnikHandler {
	
	private void checkKey() {
		// use your API key here
		System.setProperty("WORDNIK_API_KEY", "70538348db6b42e43a5181e32070feebc0b303e293ed13a97");

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
	 * retrieves synonyms, with relative frequency, of the input word
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public Map<String, Double> getSynonymsFreq(String word) {
		checkKey();

		Map<String, Double> synonymsFreq = new HashMap<>();
		try {
			List<Related> listSynonyms = WordApi.related(word, false, EnumSet.of(RelationshipType.synonym),100);
			for(Related r : listSynonyms)
				for(String s :r.getWords())
					synonymsFreq.put(s, this.getFrequency(s));
		} 
		catch (KnickerException e) { e.printStackTrace();	}
		return synonymsFreq;
	}
	
	/**
	 * retrieves hypernyms, with relative frequency, of the input word
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public Map<String, Double> getHypernymsFreq(String word) {
		checkKey();

		Map<String, Double> hypernymsFreq = new HashMap<>();
		try {
			List<Related> listHypernyms = WordApi.related(word, false, EnumSet.of(RelationshipType.hypernym),100);
			for(Related r : listHypernyms)
				for(String s :r.getWords())
					hypernymsFreq.put(s, this.getFrequency(s));
		} 
		catch (KnickerException e) { e.printStackTrace();	}
		return hypernymsFreq;
	}
	
	/**
	 * retrieves hyponyms, with relative frequency, of the input word
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public Map<String, Double> getHyponymsFreq(String word) {
		checkKey();

		Map<String, Double> hyponymsFreq = new HashMap<>();
		try {
			List<Related> listHyponyms = WordApi.related(word, false, EnumSet.of(RelationshipType.hyponym),100);
			for(Related r : listHyponyms)
				for(String s :r.getWords())
					hyponymsFreq.put(s, this.getFrequency(s));
		} 
		catch (KnickerException e) { e.printStackTrace();	}
		return hyponymsFreq;
	}
	
	/**
	 * retrieves equivalents, with relative frequency, of the input word
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public Map<String, Double> getEquivalentsFreq(String word) {
		checkKey();

		Map<String, Double> equivalentsFreq = new HashMap<>();
		try {
			List<Related> listEquivalents = WordApi.related(word, false, EnumSet.of(RelationshipType.equivalent),100);
			for(Related r : listEquivalents)
				for(String s :r.getWords())
					equivalentsFreq.put(s, this.getFrequency(s));
		}
		catch (KnickerException e) { e.printStackTrace();	}
		return equivalentsFreq;
	}
	
	public double getFrequency(String word) {
		try {
			FrequencySummary frequency = WordApi.frequency(word);
			int freq = frequency.getTotalCount();
			if(freq == 0)	//input word very rare: return 1
				return 1;
			return freq;
		} 
		catch (KnickerException e) { return 1;	}	//input word is not present: seen that is rare, the return is 1
	}
}
