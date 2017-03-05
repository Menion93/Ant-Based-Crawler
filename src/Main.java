/**
 * Created by Andrea on 05/03/2017.
 */

import scorer.Scorer;
import scorer.ScorerFactory;

public class Main
{

    public static void main(String[] args)
    {
        Configuration conf = new Configuration();

        ScorerFactory scorerFactory = new ScorerFactory();
        Scorer scorer = scorerFactory.getScorer(conf.getScoringMethod());

        AntBasedCrawler antCrawler
                = new AntBasedCrawler(conf.getNumberOfAnts(),
                                      conf.getMaxNumberOfIteration(),
                                      conf.getMaxPagesToVisit(),
                                      scorer);

        System.out.println("End");

    }
}
