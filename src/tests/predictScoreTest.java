package tests;

import graph.GraphRepoFactory;
import graph.GraphRepository;
import graph.NodePage;
import graphBuilding.GraphBuilder;
import net.jeremybrooks.knicker.KnickerException;
import scorer.Scorer;
import scorer.ScorerFactory;
import main.Configuration;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by Andrea on 13/03/2017.
 */
public class predictScoreTest
{

    public static void main(String[] args) throws IOException, SQLException, KnickerException {
        Configuration conf = new Configuration();

        GraphRepository repo = new GraphRepoFactory().getGraphApi(conf.getGraphApi(), conf.isFocusingOnSingleSite(), conf.getSeedUrl(), conf.getSuffix());

        ScorerFactory scorerFactory = new ScorerFactory();
        Scorer scorer = scorerFactory.getScorer(conf.getScoringMethod(), conf.getQuery());

        scorer.predictScore(new NodePage("http://readms.net/r/neverland/038/4244/2", repo));
    }

}
