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
    
    public void setContent(String content) {
        this.content = content;
    }

    public void freeContentMemory()
    {
        content = "";
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
