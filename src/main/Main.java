package main; /**
 * Created by Andrea on 05/03/2017.
 */

import crawler.AntBasedCrawler;
import crawler.Evaluation;
import graph.GraphRepoFactory;
import graph.GraphRepository;
import net.jeremybrooks.knicker.KnickerException;
import scorer.ScorerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Main
{

    public static void main(String[] args) throws IOException, SQLException, KnickerException {
        Configuration conf = new Configuration();

        ScorerFactory scorerFactory = new ScorerFactory(conf.getScoringMethod(), conf.getQuery());
        GraphRepoFactory graphFactory = new GraphRepoFactory();

        GraphRepository graphRepo = graphFactory.getGraphApi(conf.getGraphApi(),
                                                             conf.isFocusingOnSingleSite(),
                                                             conf.getSeedUrl(),
                                                             conf.getSuffix());

        AntBasedCrawler antCrawler
                = new AntBasedCrawler(conf.getNumberOfAnts(),
                                      conf.getMaxPagesToVisit(),
                                      conf.getMaxNumberOfIteration(),
                                      conf.getTrailUpdateCoefficient(),
                                      conf.getRandomInitValue(),
                                      conf.canCachePages(),
                                      scorerFactory,
                                      graphRepo);

        List<Map.Entry<String, Evaluation>> entries = antCrawler.FetchPagesId();

        for(Map.Entry<String, Evaluation> entry : entries){
            System.out.println(entry.getKey() + " - " + entry.getValue().getScore() + " - "
                    + entry.getValue().getDepth() + " - "
                    + entry.getValue().isGood());
        }

        System.out.println("The crawler visited " + conf.getMaxPagesToVisit() + " pages, "
                + graphRepo.getBadPages() + " of them were impossible to retrieve");

        System.out.println("End");

    }
}
