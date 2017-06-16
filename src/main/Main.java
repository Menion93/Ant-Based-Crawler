package main; /**
 * Created by Andrea on 05/03/2017.
 */

import crawler.AntBasedCrawler;
import crawler.Evaluation;
import graph.GraphRepoFactory;
import graph.GraphRepository;
import scorer.ScorerFactory;
import scorer.Scorer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main
{

    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();

        ScorerFactory scorerFactory = new ScorerFactory(conf.getScoringMethod(), conf.getQuery());
        GraphRepoFactory graphFactory = new GraphRepoFactory();

        GraphRepository graphRepo = graphFactory.getGraphApi(conf.getGraphApi());

        AntBasedCrawler antCrawler
                = new AntBasedCrawler(conf.getNumberOfAnts(),
                                      conf.getMaxPagesToVisit(),
                                      conf.getTrailUpdateCoefficient(),
                                      conf.getRandomInitValue(),
                                      conf.canCachePages(),
                                      scorerFactory,
                                      graphRepo);

        List<Map.Entry<String, Evaluation>> entries = antCrawler.FetchPagesId();

        for(Map.Entry<String, Evaluation> entry : entries){
            System.out.println(entry.getKey() + " - " + entry.getValue().getScore());
        }

        System.out.println("End");

    }
}
