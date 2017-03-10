package trash;

import java.util.Map;

import scorer.synonyms.WordnikHandler;

public class ProvaSinonimiDelete {

	public static void main(String[] args) throws Exception {

		WordnikHandler t = new WordnikHandler();
//		System.out.println(t.getFrequency("dog's cabbage"));	//TODO control if getFrequency works
		Map<String, Double> mappa = t.getSynonymsFreq("dog");
		for (String string : mappa.keySet()) {
			System.out.println(string + mappa.get(string));
		}
//		for (String string : t.getHyphenation("bag")) {
//			System.out.println(string);
//		}
	}

}
