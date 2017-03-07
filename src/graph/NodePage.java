package graph;

/**
 * Created by Andrea on 06/03/2017.
 */
public class NodePage
{
    double id;
    String content;

    public double getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void freeContentMemory()
    {
        content = "";
    }

}
