package graph;

import com.github.kevinsawicki.http.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrea on 06/03/2017.
 */
public class NodePage
{
    private String id;
    private String content;
    private GraphRepository repo;
    private List<NodePage> successors;
    private boolean hasNoSuccessor;

    public NodePage(String id, GraphRepository repo)
    {
        this.content = null;
        this.id = id;
        this.repo = repo;
        successors = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getContent() throws UnsupportedEncodingException, SQLException {

        if(content == null)
            content = repo.getContentPage(this.id);
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public void freeContentMemory()
    {
        content = null;
    }

	public boolean hasNoSuccessor(){
        return hasNoSuccessor;
    }

    public void hasNoSuccessor(boolean hasNoSuccessor)
    {
        this.hasNoSuccessor = hasNoSuccessor;
    }

    public List<NodePage> getSuccessors(){
	    return successors;
    }

    public void setSuccessors(List<NodePage> successors){
        this.successors = successors;
    }


    /**
     * counts number of words in this.content
     * @return
     */
    public double getSizeWords() {
        String trim = this.content.trim();
        if (trim.isEmpty())
            return 0;
        return trim.split("\\s+").length;
    }

}
