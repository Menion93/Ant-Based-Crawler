package scorer;

import graph.NodePage;

/**
 * Created by Andrea on 10/03/2017.
 */
public abstract class Scorer
{
    protected String query;

    public Scorer(String query)
    {
        this.query = query;
    }

    public abstract double predictScore(NodePage content);

}
