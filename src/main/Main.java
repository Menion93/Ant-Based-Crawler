package main; /**
 * Created by Andrea on 05/03/2017.
 */

import crawler.AntBasedCrawler;
import graph.GraphRepoFactory;
import graph.GraphRepository;
import scorer.ScorerFactory;
import scorer.Scorer;

import java.io.IOException;

public class Main
{

    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();

        ScorerFactory scorerFactory = new ScorerFactory(conf.getScoringMethod(), conf.getQuery());
        GraphRepoFactory graphFactory = new GraphRepoFactory();

        GraphRepository graphRepo = graphFactory.getGraphApi(conf.getGraphApi());

        AntBasedCrawler antCrawler
                = new AntBasedCrawler(conf.getNumberOfAnts(),
                                      conf.getMaxNumberOfIteration(),
                                      conf.getMaxPagesToVisit(),
                                      conf.getTrailUpdateCoefficient(),
                                      conf.getRandomInitValue(),
                                      conf.canCachePages(),
                                      scorerFactory,
                                      graphRepo);

        antCrawler.FetchPagesId();

        System.out.println("End");

    }
}
