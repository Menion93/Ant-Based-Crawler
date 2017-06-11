package graph;

/**
 * Created by Andrea on 11/06/2017.
 */
public class GraphRepoFactory {


    // Refactor with reflection???
    public GraphRepository getGraphApi(String gApi)
    {
        if(gApi == "StandardWeb")
        {
            return new WebRequestRepo();
        }

        if(gApi == "CommonCrawl")
        {
            return new CommonCrawlRepo();
        }

        System.out.println("GraphApi: \"" + gApi + "\" not supported!!");
        return null;
    }

}
