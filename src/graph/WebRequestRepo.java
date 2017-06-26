package graph;

import com.github.kevinsawicki.http.HttpRequest;


/**
 * Created by Andrea on 11/06/2017.
 */
public class WebRequestRepo extends GraphRepository{

    // In milliseconds;
    private int TIMEOUT = 2000;

    public WebRequestRepo(boolean focusOnSinglePage, String seedUrl, String suffix){
        super(focusOnSinglePage, seedUrl, suffix);
    }

    @Override
    public NodePage getNodePageRoot() {
        return new NodePage(this.seed, this);
    }

    @Override
    public String getContentPage(String id) {

        String body="";
        try{
            body = HttpRequest.get(id).connectTimeout(TIMEOUT).body();
        }
        catch (Exception e){
            System.out.println("Request Failed, returning an empty body");
        }
        finally {
            if(body == EMPTY)
                badPages++;
            return body;
        }
    }
}
