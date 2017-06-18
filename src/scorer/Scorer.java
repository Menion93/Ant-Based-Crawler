package scorer;

import graph.NodePage;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

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

    public abstract double predictScore(NodePage content) throws UnsupportedEncodingException, SQLException;

}
