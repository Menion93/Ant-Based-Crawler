package graph;

import com.github.kevinsawicki.http.HttpRequest;


/**
 * Created by Andrea on 11/06/2017.
 */
public class CommonCrawlRepo extends GraphRepository {

    private String prefix = "http://urlServerUni?id=";

    @Override
    public NodePage getNodePageRoot() {
        return new NodePage(this.seed, this);
    }

    @Override
    public String getContentPage(String id) {
        return HttpRequest.get(prefix + id).body();
    }
}
