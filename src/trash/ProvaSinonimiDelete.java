package trash;

import scorer.synonyms.WordnikHandler;

public class ProvaSinonimiDelete {

	public static void main(String[] args) throws Exception {

		WordnikHandler t = new WordnikHandler();
		for (String string : t.getEquivalents("dog")) {
			System.out.println(string);
		}
//		for (String string : t.getHyphenation("bag")) {
//			System.out.println(string);
//		}
	}

}
