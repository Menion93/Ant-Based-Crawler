package graphBuilding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static List<JSONObject> readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {

			List<JSONObject> jsons = new LinkedList<JSONObject>();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);

			Pattern p = Pattern.compile("\\{.*\\}");
			Matcher m = p.matcher(jsonText);
			while(m.find())
				jsons.add(new JSONObject(m.group()));
			return jsons;
		} finally {
			is.close();
		}
	}

	public static boolean check(){

		return true;
	}

	public static void main(String[] args) throws IOException, JSONException {
		List<JSONObject> jsons = readJsonFromUrl("http://index.commoncrawl.org/CC-MAIN-2017-04-index?url=google.com&output=json");
		for (JSONObject json : jsons) {
			System.out.println(json.toString());
			System.out.println(json.get("filename"));
		}
	}
}
