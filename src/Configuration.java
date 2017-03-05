/**
 * Created by Andrea on 05/03/2017.
 *
 * This class define the behaviour of the ant based crawler
 *
 */
import scorer.*;

public class Configuration
{
    private int numberOfAnts = 100;
    private int maxPagesToVisit = 1000;
    private int maxNumberOfIteration = 100;

    private String dictionaryScoring = "DictionaryScorer";
    private String classifierScoring = "ClassifierScorer";

    public int getNumberOfAnts() {
        return numberOfAnts;
    }

    public int getMaxPagesToVisit() {
        return maxPagesToVisit;
    }

    public int getMaxNumberOfIteration() {
        return maxNumberOfIteration;
    }

    public String getScoringMethod() {
        return dictionaryScoring;
    }
}