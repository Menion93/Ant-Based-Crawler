package tests;

import graph.NodePage;
import scorer.Scorer;
import scorer.ScorerFactory;
import main.Configuration;


/**
 * Created by Andrea on 13/03/2017.
 */
public class predictScoreTest
{

    public static void main(String[] args)
    {
        Configuration conf = new Configuration();

        ScorerFactory scorerFactory = new ScorerFactory();
        Scorer scorer = scorerFactory.getScorer(conf.getScoringMethod(), conf.getQuery());

        scorer.predictScore(new NodePage());
    }

}
