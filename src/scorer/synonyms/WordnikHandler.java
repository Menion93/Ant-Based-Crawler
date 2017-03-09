package scorer.synonyms;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List; 
import net.jeremybrooks.knicker.AccountApi;
import net.jeremybrooks.knicker.Knicker.RelationshipType;
import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordApi; 
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
	 * retrieves synonyms of the input word
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public List<String> getSynonyms(String word) {
		checkKey();

		List<String> synonyms = new LinkedList<>();
		try {
			List<Related> listSynonyms = WordApi.related(word, false, EnumSet.of(RelationshipType.synonym),100);
			for(Related r : listSynonyms)
				for(String s :r.getWords())
					synonyms.add(s);
		} 
		catch (KnickerException e) { e.printStackTrace();	}
		return synonyms;
	}
	
	/**
	 * retrieves hypernyms of the input word
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public List<String> getHypernyms(String word) {
		checkKey();

		List<String> hypernyms = new LinkedList<>();
		try {
			List<Related> listHypernyms = WordApi.related(word, false, EnumSet.of(RelationshipType.hypernym),100);
			for(Related r : listHypernyms)
				for(String s :r.getWords())
					hypernyms.add(s);
		} 
		catch (KnickerException e) { e.printStackTrace();	}
		return hypernyms;
	}
	
	/**
	 * retrieves hyponyms of the input word
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public List<String> getHyponyms(String word) {
		checkKey();

		List<String> hyponyms = new LinkedList<>();
		try {
			List<Related> listHyponyms = WordApi.related(word, false, EnumSet.of(RelationshipType.hyponym),100);
			for(Related r : listHyponyms)
				for(String s :r.getWords())
					hyponyms.add(s);
		} 
		catch (KnickerException e) { e.printStackTrace();	}
		return hyponyms;
	}
	
	/**
	 * retrieves equivalents of the input word
	 * @param word
	 * @return
	 * @throws Exception
	 */
	public List<String> getEquivalents(String word) {
		checkKey();

		List<String> equivalents = new LinkedList<>();
		try {
			List<Related> listEquivalents = WordApi.related(word, false, EnumSet.of(RelationshipType.equivalent),100);
			for(Related r : listEquivalents)
				for(String s :r.getWords())
					equivalents.add(s);
		} 
		catch (KnickerException e) { e.printStackTrace();	}
		return equivalents;
	}
}
