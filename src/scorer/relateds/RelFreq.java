package scorer.relateds;

import java.util.concurrent.Future;

public class RelFreq {

	private String synonym;
	private Future<Double> frequency;
	
	public RelFreq(String synonym, Future<Double> frequency){
		this.synonym = synonym;
		this.frequency = frequency;
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	public Future<Double> getFrequency() {
		return frequency;
	}

	public void setFrequency(Future<Double> frequency) {
		this.frequency = frequency;
	}
	

}
