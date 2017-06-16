package scorer;

import graph.NodePage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Andrea on 05/03/2017.
 */
public class HeaderScorer extends Scorer
{

    public HeaderScorer(String query)
    {
        super(query);
    }

    // It checks the hit on the h1, meta descriptions and the title, and calculate a score
    public double predictScore(NodePage nodePage) {

        double resultScore = 0;

        String content = nodePage.getContent();
        //System.out.println(nodePage.getId());

        try{
            Document doc = Jsoup.parse(content);

            // Get the title and compute the hit
            String title = doc.title();
            resultScore += countPercentageHit(title);


            Elements meta = doc.select("meta[name=description]");

            // Get the description if the page has one and compute the hit
            String description = null;

            if(meta != null){
                description = meta.attr("content");
                resultScore += countPercentageHit(description);
            }

            // Get the h1 if the page has one and compute the hit
            Elements h1 = doc.select("h1");

            double currentPercentage = 0;

            for(Element e : h1){
                double temp = countPercentageHit(e.text());

                if(currentPercentage < temp)
                    currentPercentage = temp;
            }

            resultScore += currentPercentage;
        }
        catch (Exception e){
            System.out.println("Parser failed for some reason");
            return 0;
        }
        //System.out.println("A score has been assigned!");

        return resultScore;
    }

    public double countPercentageHit(String text){

        double hits = 0;

        String whiteSpace = " ";
        String[] words = query.split(whiteSpace);
        String[] splittedText = text.split(whiteSpace);

        for(String word : words){
            for(String textWord : splittedText){
                if(word.equals(textWord)){
                    hits++;
                    break;
                }
            }
        }

        return hits/words.length;
    }

}
