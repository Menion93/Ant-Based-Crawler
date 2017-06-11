package graph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkParser {

	public List<String> getLinks(String html, String url) throws IOException {

		Document doc = Jsoup.parse(html);
		Elements links = doc.select("a[href]");
		List<String> linksList = new LinkedList<>();

		for (Element link : links) {
			String parsedLink = String.format("%s", link.attr("href"), trim(link.text(), 35));

			if(parsedLink.startsWith("http://"))
				linksList.add(parsedLink);
			else if(!parsedLink.startsWith("#"))
				linksList.add(cleanLink(parsedLink, url));

		}
		System.out.println("");

		return linksList;
	}

	private String cleanLink(String link, String url){
		if(url.endsWith("/") && link.startsWith("/"))
			return url + link.substring(1);

		if(!url.endsWith("/") && !link.startsWith("/"))
			return url + "/" +link.substring(1);

		return url + link;
	}


	private String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width-1) + ".";
		else
			return s;
	}
}
