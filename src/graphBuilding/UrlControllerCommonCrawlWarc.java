package graphBuilding;

/**
 * Handler for:	January 2017 Index
 * @author nicholas
 *
 */
public class UrlControllerCommonCrawlWarc {

	private String url;	//DNS name with path too
	
	public UrlControllerCommonCrawlWarc(String url){
		this.url = url;
	}
	
	public boolean check(){
		String beginning = "http://index.commoncrawl.org/CC-MAIN-2017-04-index?url=";
		return true;
	}
}
