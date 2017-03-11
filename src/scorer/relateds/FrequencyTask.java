package scorer.relateds;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import net.jeremybrooks.knicker.KnickerException;
import net.jeremybrooks.knicker.WordApi;
import net.jeremybrooks.knicker.dto.FrequencySummary;
import net.jeremybrooks.knicker.dto.Related;

public class FrequencyTask implements Callable<Map<String, Double>> {

	private BlockingQueue<Related> queueRelateds;
	
	public FrequencyTask(BlockingQueue<Related> queueRelateds) {
		this.queueRelateds = queueRelateds;
	}

	@Override
	public Map<String, Double> call() throws Exception {
		Map<String, Double> relatedFreq = new HashMap<>();
		BlockingQueue<RelFreq> futureResults = new LinkedBlockingQueue<>();
		
		//asynchronous requests
		while(!queueRelateds.isEmpty()){
			Related related = this.queueRelateds.poll();
			if(related != null)
				for(String s :related.getWords())
					futureResults.put(new RelFreq(s, this.getFrequencyAsync(s)));
		}
		
		//synchronization
		for (RelFreq synFreq : futureResults)
			relatedFreq.put(synFreq.getSynonym(), synFreq.getFrequency().get());
		
		return relatedFreq;
	}
	
	private Future<Double> getFrequencyAsync(String word){
		return CompletableFuture.supplyAsync(() -> this.getFrequency(word));
	}
	
	/**
	 * retrieves the frequency of the input word
	 * @param word
	 * @return
	 */
	private double getFrequency(String word) {
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
