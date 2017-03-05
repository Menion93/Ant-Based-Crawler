/**
 * Created by Andrea on 05/03/2017.
 */

import scorer.Scorer;

import java.util.Map;
import java.util.HashMap;

public class AntBasedCrawler
{

    private int numberOfAnts;
    private int maxPagesToVisit;
    private int maxNumberOfIteration ;

    private Scorer scorer;

    // This map represent the id of the pages and its relative trail score
    private Map<Double, Double> id2score;

    public AntBasedCrawler(int numberOfAnts, int maxNumberOfIteration, int maxPagesToVisit, Scorer scorer)
    {
        this.numberOfAnts = numberOfAnts;
        this.maxNumberOfIteration = maxNumberOfIteration;
        this.maxPagesToVisit = maxPagesToVisit;
        this.scorer = scorer;

        id2score = new HashMap<>();
    }

    public double[] FetchPagesId()
    {
        return new double[]{1,2,3};
    }

}
