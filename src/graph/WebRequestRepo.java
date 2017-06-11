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

        if(nodePage.hasNoSuccessor())
            return null;

        if (!nodePage.hasNoSuccessor() && nodePage.getSuccessors().size() != 0)
            return nodePage.getSuccessors();

        List<NodePage> nodeSuccessors = new ArrayList<>();

        String body = nodePage.getContent();

        List<String> links = super.parser.getLinks(body);

        if(links.size() == 0){
            nodePage.hasNoSuccessor(true);
            return null;
        }

        for(String link : links){
            nodeSuccessors.add(new NodePage(link, this));
        }

        nodePage.setSuccessors(nodeSuccessors);

        return nodeSuccessors;
    }

    @Override
    public String getContentPage(String id) {
        return HttpRequest.get(id).body();
    }
}
