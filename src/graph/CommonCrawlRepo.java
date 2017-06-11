package graph;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrea on 11/06/2017.
 */
public class CommonCrawlRepo extends GraphRepository {

    @Override
    public NodePage getNodePageRoot() {
        return new NodePage(this.seed, this);
    }

    @Override
    public List<NodePage> expandNode(NodePage nodePage) {
        return new ArrayList<>();
    }

    @Override
    public String getContentPage(String id) {
        return null;
    }
}
