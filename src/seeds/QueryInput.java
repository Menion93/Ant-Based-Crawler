package seeds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QueryInput {
	private String url;
	private String path;
	private String endPath;
	
	private String seedSport;
	private String seedFilm;
	
	private String query;

	
	/**
	 * only for film and sports (static seeds in this moment)
	 */
	public QueryInput(String query){
		this.url = "http://index.commoncrawl.org/";
		this.path = "CC-MAIN-2017-09";
		this.endPath = "-index?url=";

		this.seedSport = "givemesport.com";
		this.seedFilm = "imdb.com";
		
		this.query = query;
	}

	/**
	 * return the content of the web page in the input URL
	 * @param urlToRead
	 * @return
	 * @throws Exception
	 */
	private String getHTML(String urlToRead) throws Exception {
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line + "\n");
		}
		rd.close();
		return result.toString();
	}

	public String getSeeds() throws Exception{
		return this.getHTML(this.url + this.path + this.endPath + this.seedFilm) +
		this.getHTML(this.url + this.path + this.endPath + this.seedSport);
	}
}
