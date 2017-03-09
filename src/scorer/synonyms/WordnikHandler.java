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



	public List<String> getSynonyms(String word) throws Exception {
		// use your API key here
		System.setProperty("WORDNIK_API_KEY", "70538348db6b42e43a5181e32070feebc0b303e293ed13a97");

		// check the status of the API key
		TokenStatus status = AccountApi.apiTokenStatus();
		if (status.isValid()) {
			System.out.println("API key is valid.");
		} else {
			System.out.println("API key is invalid!");
			System.exit(1);
		}

		List<String> synonyms = new LinkedList<>();
		try {
			List<Related> list = WordApi.related(word, false, EnumSet.of(RelationshipType.synonym),100);
			for(Related r : list)
				for(String s :r.getWords())
					synonyms.add(s);
		} 
		catch (KnickerException e) { e.printStackTrace();	}
		return synonyms;
	}
}
