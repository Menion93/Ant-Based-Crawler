package crawler;

import graph.GraphRepository;
import graph.NodePage;
import scorer.Scorer;
import util.GraphUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Andrea on 07/03/2017.
 */
public class Ant
{

    private List<Edge> path;
    private Scorer scorer;
    private Random randomGenerator;
    private double randomInitValue;
    private boolean cachePages;


    HashMap<Edge, Double > edge2trail;


    public Ant(Scorer scorer, HashMap<Edge, Double > edge2trail, double randomInitValue, int seed, boolean cachePages){
        this.path = new ArrayList<>();
        this.scorer = scorer;
        this.edge2trail = edge2trail;
        this.randomInitValue = randomInitValue;
        this.cachePages = cachePages;
        randomGenerator = new Random(seed);

    }


    public int AntCycle(NodePage startNode, HashMap<String,Evaluation> id2score, GraphRepository graphRepo, int numberOfStep, int visitedPages, int maxPagesToVisit) throws IOException, SQLException {

        this.path = new ArrayList<>();

        int currentVisitedPages = 0;

        if(visitedPages >= maxPagesToVisit)
            return currentVisitedPages;

        NodePage currentNode = startNode;

        if(!id2score.containsKey(currentNode.getId()))
        {
            id2score.put(currentNode.getId(), new Evaluation(scorer.predictScore(currentNode), 0));
            if(!cachePages)
                currentNode.freeContentMemory();

            currentVisitedPages++;

            if(visitedPages+currentVisitedPages >= maxPagesToVisit)
                return currentVisitedPages;
        }

        for(int j=0; j<numberOfStep;j++)
        {
            List<NodePage> frontier = graphRepo.expandNode(currentNode);

            if (frontier == null){
                System.out.println("Frontier is null");
                return currentVisitedPages;
            }

            NodePage successorNode = selectNode(currentNode, frontier);

            while(successorNode != null && GraphUtils.contains(path, successorNode)){
                frontier.remove(successorNode);
                successorNode = selectNode(currentNode, frontier);
            }

            if(successorNode == null) {
                System.out.println("Found a loop, exiting this ant cycle");
                return currentVisitedPages;
            }


            if(!id2score.containsKey(successorNode.getId()))
            {
                id2score.put(successorNode.getId(), new Evaluation( scorer.predictScore(successorNode), j));
                if(!cachePages)
                    successorNode.freeContentMemory();

                currentVisitedPages++;
            }

            path.add(new Edge(currentNode.getId(), successorNode.getId()));


            if(visitedPages + currentVisitedPages >= maxPagesToVisit)
                return currentVisitedPages;

            currentNode = successorNode;
        }

        return currentVisitedPages;
    }

    // To do: select a node with probability according to the trail, or randomValue
    // if it is the last iteration
    private NodePage selectNode(NodePage currentNode, List<NodePage> frontier)
    {
        if(frontier.size() == 0)
            return null;

        double[] trailsProba = new double[frontier.size()];
        double sumTrails = 0;

        for(int i=0; i<frontier.size(); i++)
        {
            Edge e = new Edge(currentNode.getId(), frontier.get(i).getId());
            if(edge2trail.containsKey(e))
            {
                trailsProba[i] = edge2trail.get(e);
            }
            else
            {
                edge2trail.put(e, randomInitValue);
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
                return frontier.get(i);
        }

        System.out.println("ERROR in the selection of the successor");
        return null;
    }

    public List<Edge> getPath() {
        return path;
    }
}
