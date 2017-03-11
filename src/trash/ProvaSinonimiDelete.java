package trash;

import java.util.Map;

import scorer.relateds.WordnikHandler;

public class ProvaSinonimiDelete {

	public static void main(String[] args) throws Exception {
		double startTimeParallel, stopTimeParallel, startTimeSerial, stopTimeSerial;

		WordnikHandler t = new WordnikHandler();

		startTimeParallel = System.nanoTime();
		Map<String, Double> mappapar = t.getRelatedFreqParallel("dog", "synonym");
		for (String string : mappapar.keySet())
			System.out.println(string + mappapar.get(string));
		stopTimeParallel = System.nanoTime();
		System.out.println("Parallel Time:\t" + (stopTimeParallel - startTimeParallel));
		

		startTimeSerial = System.nanoTime();
		Map<String, Double> mappa = t.getRelatedFreqParallel("dog", "synonym");
		for (String string : mappa.keySet())
			System.out.println(string + mappa.get(string));
		stopTimeSerial = System.nanoTime();
		System.out.println("Serial Time:\t" + (stopTimeSerial - startTimeSerial));


		System.out.println("Speedup:\t" + (stopTimeSerial - startTimeSerial) / (stopTimeParallel - startTimeParallel));

//		for (String string : t.getHyphenation("bag")) {
//			System.out.println(string);
//		}
	}

}
