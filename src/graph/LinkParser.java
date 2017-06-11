package graph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkParser {

	public List<String> getLinks(String html) throws IOException {

		Document doc = Jsoup.parse(html);
		Elements links = doc.select("a[href]");
		List<String> linksList = new LinkedList<>();

		for (Element link : links) 
			linksList.add(String.format("%s", link.attr("href"), trim(link.text(), 35)));
		
		return linksList;
	}


	private String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width-1) + ".";
		else
			return s;
	}
}
