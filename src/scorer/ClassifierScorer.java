package scorer;

import graph.NodePage;

/**
 * Created by Andrea on 05/03/2017.
 */
public class ClassifierScorer extends Scorer
{
    public ClassifierScorer(String query)
    {
        super(query);
    }

    public double predictScore(NodePage nodePage)
    {
        return 0;
    }

}
