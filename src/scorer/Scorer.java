package scorer;

import graph.NodePage;

/**
 * Created by Andrea on 05/03/2017.
 */
public interface Scorer
{
    // Params to be decided
    double predictScore(NodePage nodePage);
}
