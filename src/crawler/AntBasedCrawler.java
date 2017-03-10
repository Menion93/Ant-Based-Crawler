package crawler;

/**
 * Created by Andrea on 05/03/2017.
 */

import graph.GraphRepository;
import graph.NodePage;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import scorer.Scorer;

public class AntBasedCrawler
{

    private int numberOfAnts;
    private int maxPagesToVisit;
    private int maxNumberOfIteration ;
    private int visitedPages;
    private int numberOfStep;
    private double trailUpdateCoefficient;
    private double randomInitValue;

    private Scorer scorer;
    private GraphRepository graphRepo;

    // This map represent the id of the pages and its scoreClassifier.
    // It is a cache of the scores of the single pages
    private HashMap<Double, Double> id2score;

    // Map representation of the arc and its associated trail value
    private HashMap<Arc, Double > arc2trail;

    private Random randomGenerator;

    public AntBasedCrawler(int numberOfAnts, int maxNumberOfIteration, int maxPagesToVisit,
                           double trailUpdateCoefficient, double randomInitValue, Scorer scorer)
    {
        this.numberOfAnts = numberOfAnts;
        this.maxNumberOfIteration = maxNumberOfIteration;
        this.maxPagesToVisit = maxPagesToVisit;
        this.scorer = scorer;
        this.trailUpdateCoefficient = trailUpdateCoefficient;
        this.randomInitValue = randomInitValue;

        id2score = new HashMap<>();
        arc2trail = new HashMap<>();

        graphRepo = new GraphRepository();

        randomGenerator = new Random(6337);
    }

    public double[] FetchPagesId()
    {
        visitedPages = 0;
        numberOfStep = 0;

        System.out.println("Starting the crawling...");
        double startTime = System.currentTimeMillis() * 1000;


        while (haltingCriterion())
        {
            // Get the visited pages by the ants
            ArrayList<ArrayList<Arc>> paths = DoAntsCycle();

            // Update the trails
            UpdateTrails(paths);

            // Increment the step of the ants can take by one
            numberOfStep++;
        }

        double lastedTime = (System.currentTimeMillis() * 1000) - startTime;

        System.out.format("The crawling has ended in %f seconds.\n", lastedTime);
        System.out.format("In mean the crawling spends about %f seconds per iteration\n", lastedTime/(numberOfStep-1));

        // To Do: return the pages crawled, maybe a ranking?
        return new double[]{1,2,3};
    }

    // Need to refactorize the code with class Ant
    private ArrayList<ArrayList<Arc>> DoAntsCycle()
    {
        // A list of list representing all the paths taken by all the ants
        ArrayList<ArrayList<Arc>> path = new ArrayList<>();

        // For all the ants
        for(int i=0; i<numberOfAnts; i++)
        {
            ArrayList<Arc> singlePath = new ArrayList<>();

            // Start the iteration of the i-th ant
            NodePage currentNode = graphRepo.getNodePageRoot();

            if(numberOfStep == 1 && i == 0)
            {
                id2score.put(currentNode.getId(), scorer.predictScore(currentNode));
                currentNode.freeContentMemory();
            }

            for(int j=0; j<numberOfStep;j++)
            {
                NodePage[] frontier = graphRepo.expandeNode(currentNode);

                NodePage successorNode = selectNode(currentNode, frontier);

                if(!id2score.containsKey(successorNode.getId()))
                {
                    id2score.put(successorNode.getId(), scorer.predictScore(successorNode));
                    successorNode.freeContentMemory();
                }

                singlePath.add(new Arc(currentNode.getId(), successorNode.getId()));

                currentNode = successorNode;
            }

            path.add(singlePath);
        }

        return path;
    }

    // Update the trails after an ant epoch
    private void UpdateTrails(ArrayList<ArrayList<Arc>> paths)
    {
        // For all ants
        for(ArrayList<Arc> singlePath : paths)
        {
            // For all pages the ant walked on
            // Compute the trail score for the path
            // and update the arc2trail list

            double pathScore = id2score.get(singlePath.get(0).getFrom());

            for(int j = 0; j<numberOfStep; j++)
            {
                pathScore += id2score.get(singlePath.get(j).getTo());
            }

            // Normalization
            pathScore /= (numberOfStep+1);

            // Update the trail score of the visited page by this ant
            for(Arc e : singlePath)
            {
                arc2trail.put(e, trailUpdateCoefficient*arc2trail.get(e) + pathScore);
            }
        }

    }

    // It counts the same pages multiple times, need to find a better halting criterion
    private boolean haltingCriterion()
    {
        return (visitedPages + numberOfAnts * numberOfStep) <= maxPagesToVisit;
    }

    // To do: select a node with probability according to the trail, or randomValue
    // if it is the last iteration
    private NodePage selectNode(NodePage currentNode, NodePage[] frontier)
    {
        double[] trailsProba = new double[frontier.length];
        double sumTrails = 0;

        for(int i=0; i<frontier.length; i++)
        {
            Arc e = new Arc(currentNode.getId(), frontier[i].getId());
            if(arc2trail.containsKey(e))
            {
                trailsProba[i] = arc2trail.get(e);
            }
            else
            {
                arc2trail.put(e, randomInitValue);
                trailsProba[i] = randomInitValue;
            }

            sumTrails += trailsProba[i];
        }

        // Normalize probability
        for(int i=0; i<trailsProba.length; i++)
        {
            trailsProba[i] /= sumTrails;
        }


        // Chose random a successor between those probability
        double nodeChoice = randomGenerator.nextDouble();
        double currentProba = 0;

        for(int i=0; i<trailsProba.length; i++)
        {
            currentProba += trailsProba[i];

            if(nodeChoice < currentProba)
                return frontier[i];
        }

        System.out.println("ERROR in the selection of the successor");
        return null;
    }

}
