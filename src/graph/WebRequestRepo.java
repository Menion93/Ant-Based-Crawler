package graph;

import com.github.kevinsawicki.http.HttpRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrea on 11/06/2017.
 */
public class WebRequestRepo extends GraphRepository{

    public WebRequestRepo(){
        super();
    }

    @Override
    public NodePage getNodePageRoot() {
        return new NodePage(this.seed, this);
    }

    @Override
    public List<NodePage> expandNode(NodePage nodePage) throws IOException {

        List<NodePage> nodeSuccessors = new ArrayList<>();

        String body = HttpRequest.get(nodePage.id).body();

        List<String> links = super.parser.getLinks(body);

        for(String link : links){
            nodeSuccessors.add(new NodePage(link, this));
        }

        return nodeSuccessors;
    }

    @Override
    public String getContentPage(String id) {
        return HttpRequest.get(id).body();
    }
}
