package trash;

import seeds.QueryInput;

public class ProvaQuery {

	public static void main(String[] args) {
		try {
			System.out.println(new QueryInput("").getSeeds());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
