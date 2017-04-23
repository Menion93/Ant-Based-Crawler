package graph;

public class TestLine {

	public static void main(String[] args) {

		String get;
		String input = "GET /news/3383 HTTP/1.0\n"
				+ "Host: 03online.com\n"
				+ "Accept-Encoding: x-gzip, gzip, deflate\n"
				+ "User-Agent: CCBot/2.0 (http://commoncrawl.org/faq/)\n"
				+ "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n";

		String[] lines = input.split(System.getProperty("line.separator"));
		get = lines[1].substring(6, lines[1].length()) + lines[0].substring(4, lines[0].length()-9);
		System.out.println(get);

	}

}
