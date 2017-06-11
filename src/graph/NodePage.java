package graph;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Andrea on 06/03/2017.
 */
public class NodePage
{
    String id;
    String content;
    GraphRepository repo;

    public NodePage(String id, GraphRepository repo)
    {
        this.content = null;
        this.id = id;
        this.repo = repo;
    }

    public String getId() {
        return id;
    }

    public String getContent() {

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
