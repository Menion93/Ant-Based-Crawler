package tests;

import graph.GraphRepoFactory;
import graph.GraphRepository;
import graph.NodePage;
import graphBuilding.GraphBuilder;
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

        GraphRepository repo = new GraphRepoFactory().getGraphApi(conf.getGraphApi());

        ScorerFactory scorerFactory = new ScorerFactory();
        Scorer scorer = scorerFactory.getScorer(conf.getScoringMethod(), conf.getQuery());

        scorer.predictScore(new NodePage("http://readms.net/r/neverland/038/4244/2", repo));
    }

}
