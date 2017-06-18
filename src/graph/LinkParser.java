package graph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkParser {

	private String suffix;

	public LinkParser(){}

	public LinkParser(String suffix){
		this.suffix = suffix;
	}

	public List<String> getLinks(String html, String url) throws IOException {
		return getLinks(html, url, false);
	}


		public List<String> getLinks(String html, String url, boolean filterForUrl) throws IOException {

		Document doc = Jsoup.parse(html);
		Elements links = doc.select("a[href]");
		List<String> linksList = new LinkedList<>();

		String baseUrl = "http://" + url.split("/")[2] + suffix;

		for (Element link : links) {
			String parsedLink = String.format("%s", link.attr("href"), trim(link.text(), 35));

   			// Attention HARDCODED on suffix
			if(filterForUrl && (parsedLink.startsWith("http://") || parsedLink.startsWith("https://")) && !parsedLink.startsWith(baseUrl))
				continue;

			if(filterForUrl && parsedLink.startsWith("/") && !parsedLink.startsWith(suffix))
				continue;

			if(parsedLink.startsWith("http://"))
				linksList.add(parsedLink);
			else if(!parsedLink.startsWith("#") && !parsedLink.equals(""))
				linksList.add(cleanLink(parsedLink, url));

		}

		return linksList;
	}



	private String cleanLink(String link, String url){

		if(link.startsWith("/"))
			return "http://" + url.split("/")[2] + link;

		// It means it is a root site
		if(url.endsWith("/"))
			return url + link;

		String[] urlPath = url.split("/");

		String newUrl = "";

		for(int i=0; i<urlPath.length-1; i++){
			newUrl += urlPath[i] + "/";
		}

		return newUrl + link;

	}


	private String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width-1) + ".";
		else
			return s;
	}
}
