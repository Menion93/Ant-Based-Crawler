package trash;

import graph.GraphRepoFactory;
import graph.GraphRepository;
import graph.NodePage;
import antMain.Configuration;
import scorer.DictionaryScorer;
import scorer.QueryAdapter;

import java.io.IOException;
import java.sql.SQLException;

public class MatcherTester {

	public static void main(String[] args) throws IOException, SQLException {

		Configuration conf = new Configuration();

		GraphRepository repo = new GraphRepoFactory().getGraphApi(conf.getGraphApi(), conf.isFocusingOnSingleSite(), conf.getSeedUrl(), conf.getSuffix());

		NodePage nodePage = new NodePage("dummy", repo);

		nodePage.setContent("Domestic dog\n" + 
				"Temporal range: 0.033–0 Ma\n" + 
				"PreЄЄOSDCPTJKPgN\n" + 
				"↓\n" + 
				"Late Pleistocene – Recent\n" + 
				"Collage of Nine Dogs.jpg\n" + 
				"Selection of the different breeds of dog.\n" + 
				"Conservation status\n" + 
				"Domesticated\n" + 
				"Scientific classification e\n" + 
				"Kingdom:	Animalia\n" + 
				"Phylum:	Chordata\n" + 
				"Class:	Mammalia\n" + 
				"Order:	Carnivora\n" + 
				"Suborder:	Caniformia\n" + 
				"Family:	Canidae\n" + 
				"Genus:	Canis\n" + 
				"Species:	C. lupus\n" + 
				"Subspecies:	C. l. familiaris[1]\n" + 
				"Trinomial name\n" + 
				"Canis lupus familiaris[1]\n" + 
				"\n" + 
				"Montage showing the morphological variation of the dog.\n" + 
				"The domestic dog (Canis lupus familiaris or Canis familiaris)[2] is a member of genus Canis (canines) that forms part of the wolf-like canids,[3] and is the most widely abundant carnivore.[4][5][6] The dog and the extant gray wolf are sister taxa,[7][8][9] with modern wolves not closely related to the wolves that were first domesticated.[8][9] The dog was the first domesticated species[9][10] and has been selectively bred over millennia for various behaviors, sensory capabilities, and physical attributes.[11]\n" + 
				"\n" + 
				"Their long association with humans has led dogs to be uniquely attuned to human behavior[12] and they are able to thrive on a starch-rich diet that would be inadequate for other canid species.[13] Dogs vary widely in shape, size and colours.[14] Dogs perform many roles for people, such as hunting, herding, pulling loads, protection, assisting police and military, companionship and, more recently, aiding handicapped individuals. This influenc");
		String query = "dog";
		DictionaryScorer dicScore = new DictionaryScorer(new QueryAdapter(query));
		double score = dicScore.predictScore(nodePage);
		System.out.println(score);

	}
}
