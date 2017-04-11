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
		//      Validate.isTrue(args.length == 1, "usage: supply url to fetch");
		//      String url = "https://mvnrepository.com/artifact/org.jsoup/jsoup/1.8.3";
		//      print("Fetching %s...", url);

		Document doc = Jsoup.parse(html);
		Elements links = doc.select("a[href]");
		List<String> linksList = new LinkedList<>();

		/*Elements media = doc.select("[src]");
      Elements imports = doc.select("link[href]");*/

		/*print("\nMedia: (%d)", media.size());
      for (Element src : media) {
          if (src.tagName().equals("img"))
              print(" * %s: <%s> %sx%s (%s)",
                      src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                      trim(src.attr("alt"), 20));
          else
              print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
      }

      print("\nImports: (%d)", imports.size());
      for (Element link : imports) {
          print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
      }*/

//		print("\nLinks: (%d)", links.size());
		for (Element link : links) 
			linksList.add(String.format("%s", link.attr("href"), trim(link.text(), 35)));
		
		return linksList;
	}

	private void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width-1) + ".";
		else
			return s;
	}
}
