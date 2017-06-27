package crawler;

import graph.NodePage;

/**
 * Created by Andrea on 06/03/2017.
 */
public class Edge
{
    private NodePage from;
    private NodePage to;

    public Edge(NodePage from, NodePage to)
    {
        this.from = from;
        this.to = to;
    }

    public NodePage getFrom()
    {
        return from;
    }

    public NodePage getTo()
    {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge edge = (Edge) o;

        return (this.from.getId().compareTo(edge.getFrom().getId()) + this.to.getId().compareTo(edge.getTo().getId())) == 0;
    }

    @Override
    public int hashCode() {
        int result = getFrom().hashCode();
        result = 31 * result + getTo().hashCode();
        return result;
    }
}
