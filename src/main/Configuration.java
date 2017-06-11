package main; /**
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
    private double trailUpdateCoefficient = 0.1;
    private double randomInitValue = 0.5;

    private String scoringMethod = "DictionaryScorer";
    //private String scoringMethod = "ClassifierScorer";

    private String graphApi = "StandardWeb";
    //private String graphApi = "CommonCrawl";

    private String query = "University of Rome 3";

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
        return scoringMethod;
    }

    public String getGraphApi(){ return graphApi; }

    public double getTrailUpdateCoefficient() {return trailUpdateCoefficient; }

    public double getRandomInitValue() { return randomInitValue; }

    public String getQuery() {
        return query;
    }
}
