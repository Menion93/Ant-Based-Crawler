package graph;

/**
 * Created by Andrea on 06/03/2017.
 */
public class GraphRepository
{

    public NodePage getNodePageRoot()
    {
        return new NodePage();
    }

    public NodePage[] expandeNode(NodePage nodePage)
    {
        return new NodePage[]{};
    }
}
