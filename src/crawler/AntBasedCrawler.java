package crawler;

/**
 * Created by Andrea on 05/03/2017.
 */

import graph.GraphRepository;

import java.io.IOException;
import java.util.*;
import graph.NodePage;
import scorer.ScorerFactory;
import util.RankingComparator;

public class AntBasedCrawler
{

    private int numberOfAnts;
    private int maxPagesToVisit;
    private int maxNumberOfIteration ;
    private int visitedPages;
    private double trailUpdateCoefficient;

    private GraphRepository graphRepo;

    // This map represent the id of the pages and its scoreClassifier.
    // It is a cache of the scores of the single pages
    private HashMap<String, Evaluation> id2score;

    // Map representation of the edge and its associated trail value
    private HashMap<Edge, Double > edge2trail;

    private List<Ant> ants;


    public AntBasedCrawler(int numberOfAnts, int maxNumberOfIteration, int maxPagesToVisit,
                           double trailUpdateCoefficient, double randomInitValue, boolean cachePages, ScorerFactory scFactory,
                           GraphRepository graphRepo)
    {
        this.numberOfAnts = numberOfAnts;
        this.maxNumberOfIteration = maxNumberOfIteration;
        this.maxPagesToVisit = maxPagesToVisit;
        this.trailUpdateCoefficient = trailUpdateCoefficient;

        id2score = new HashMap<>();
        edge2trail = new HashMap<>();

        this.graphRepo = graphRepo;

        ants = new ArrayList<>();

        // Create all the Ants
        for(int i=0; i<numberOfAnts; i++)
            ants.add(new Ant(scFactory.getScorer(), edge2trail, randomInitValue,  6337 + i, cachePages));

    }

    public List<Map.Entry<String, Evaluation>> FetchPagesId() throws IOException {
        List<String> crawledPages = new ArrayList<>();

        visitedPages = 0;
        int numberOfStep = 1;

        System.out.println("Starting the crawling...");
        double startTime = System.currentTimeMillis() / 1000;

        // This node will keep all the graph crawled since now in memory.
        NodePage startNode = graphRepo.getNodePageRoot();

        while (visitedPages<maxPagesToVisit)
        {
            // Get the visited pages by the ants
            for(Ant ant : ants)
                visitedPages += ant.AntCycle(startNode, id2score, graphRepo, numberOfStep, visitedPages, maxPagesToVisit);

            // Update the trails
            UpdateTrails(ants, numberOfStep);

            // Increment the step of the ants can take by one
            numberOfStep++;
        }

        double lastedTime = (System.currentTimeMillis() / 1000) - startTime;

        System.out.format("The crawling has ended in %f seconds.\n", lastedTime);
        System.out.format("In mean the crawling spends about %f seconds per iteration\n", lastedTime/(numberOfStep-1));


        List<Map.Entry<String, Evaluation>> ranking = new LinkedList<>(id2score.entrySet());
        ranking.sort(new RankingComparator());
        // To Do: return the pages crawled, maybe a ranking?
        return ranking;
    }



    // Update the trails after an ant epoch
    private void UpdateTrails(List<Ant> ants, int numberOfStep)
    {
        // For all ants
        for(Ant ant : ants)
        {
            // For all pages the ant walked on
            // Compute the trail score for the path
            // and update the edge2trail list

            List<Edge> path = ant.getPath();

            if(path.size() == 0)
                continue;

            double pathScore = id2score.get(path.get(0).getFrom()).getScore();

            for(Edge e : path)
            {
                pathScore += id2score.get(e.getTo()).getScore();
            }

            // Normalization
            pathScore /= (path.size()+1);

            // Update the trail score of the visited page by this ant
            for(Edge e : path)
            {
                edge2trail.put(e, trailUpdateCoefficient* edge2trail.get(e) + pathScore);
            }
        }

    }

}
