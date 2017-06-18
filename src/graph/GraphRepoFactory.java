package graph;

import java.io.IOException;

/**
 * Created by Andrea on 11/06/2017.
 */
public class GraphRepoFactory {


    // Refactor with reflection???
    public GraphRepository getGraphApi(String gApi, boolean focusOnSinglePage, String seedUrl, String suffix) throws IOException {

        if(gApi == "StandardWeb") {
            return new WebRequestRepo(focusOnSinglePage, seedUrl, suffix);
        }

        if(gApi == "CommonCrawlRepo") {
            return new CommonCrawlRepo(focusOnSinglePage, seedUrl, suffix);
        }

        System.out.println("GraphApi: \"" + gApi + "\" not supported!!");
        return null;
    }


}
