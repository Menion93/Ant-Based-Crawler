package crawler; /**
 * Created by Andrea on 05/03/2017.
 */

import graph.GraphRepository;
import graph.NodePage;
import scorer.Scorer;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class AntBasedCrawler
{

    private int numberOfAnts;
    private int maxPagesToVisit;
    private int maxNumberOfIteration ;
    private int visitedPages;
    private double trailUpdateCoefficient;

    private Scorer scorer;
    private GraphRepository graphRepo;

    // This map represent the id of the pages and its scoreClassifier.
    // It is a cache of the scores of the single pages
    private HashMap<Double, Double> id2score;

    // Map representation of the arc and its associated trail value
    private HashMap<Arc, Double > arc2trail;

    public AntBasedCrawler(int numberOfAnts, int maxNumberOfIteration, int maxPagesToVisit,
                           double trailUpdateCoefficient, Scorer scorer)
    {
        this.numberOfAnts = numberOfAnts;
        this.maxNumberOfIteration = maxNumberOfIteration;
        this.maxPagesToVisit = maxPagesToVisit;
        this.scorer = scorer;
        this.trailUpdateCoefficient = trailUpdateCoefficient;

        id2score = new HashMap<>();
        arc2trail = new HashMap<>();

        graphRepo = new GraphRepository();
    }

    public double[] FetchPagesId()
    {
        visitedPages = 0;

        System.out.println("Starting the crawling...");
        double startTime = System.currentTimeMillis() * 1000;

        NodePage nodeRoot = graphRepo.getNodePageRoot();

        int numberOfStep = 1;

        while (visitedPages < maxPagesToVisit)
        {
            double[][] paths = DoAntsCycle(numberOfStep);

            if(paths == null)
            {
                // Number of visited pages reached
                System.out.println("Halting criterion reached");
                numberOfStep++;
                break;
            }

            // Updater Trails
            UpdateTrails(paths);
            //
            numberOfStep++;
        }

        double lastedTime = (System.currentTimeMillis() * 1000) - startTime;

        System.out.format("The crawling has ended in %f seconds.\n", lastedTime);
        System.out.format("In mean the crawling spends about %f seconds per iteration\n", lastedTime/(numberOfStep-1));


        return new double[]{1,2,3};
    }

    private double[][] DoAntsCycle(int numberOfStep)
    {

        for(int i=0; i<numberOfAnts; i++)
        {
            // Do Stuff
        }

        visitedPages++;
        return new double[][] {{},{}};
    }

    // Update the trails after an ant epoch
    private void UpdateTrails(double[][] paths)
    {
        // For all ants
        for(int i = 0; i<paths.length; i++)
        {
            // For all pages the ant walked on
            // Compute the trail score for the path
            // and update the arc2trail list

            List<Arc> arcList = new ArrayList<>();

            double pathScore = paths[i][0];

            for(int j = 0; j<paths[i].length-1; j++)
            {
                arcList.add(new Arc(paths[i][j], paths[i][j+1]));
                pathScore += id2score.get(paths[i][j+1]);
            }

            // Normalization
            pathScore /= paths[i].length;

            // Update the trail score of the visited page by this ant
            for(Arc e : arcList)
            {
                arc2trail.put(e, trailUpdateCoefficient*arc2trail.get(e) + pathScore);
            }
        }


    }

}
