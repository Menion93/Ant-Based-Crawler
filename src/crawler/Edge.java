package crawler;

/**
 * Created by Andrea on 06/03/2017.
 */
public class Edge
{
    private String from;
    private String to;

    public Edge(String from, String to)
    {
        this.from = from;
        this.to = to;
    }

    public String getFrom()
    {
        return from;
    }

    public String getTo()
    {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge edge = (Edge) o;

        return (this.from.compareTo(edge.getFrom()) + this.to.compareTo(edge.getTo())) == 0;
    }

    @Override
    public int hashCode() {
        int result = getFrom().hashCode();
        result = 31 * result + getTo().hashCode();
        return result;
    }
}
