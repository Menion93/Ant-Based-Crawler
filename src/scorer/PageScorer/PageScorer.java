package scorer.PageScorer;

import graph.NodePage;
import net.jeremybrooks.knicker.KnickerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import scorer.QueryAdapter;
import scorer.Scorer;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Andrea on 05/03/2017.
 */
public class PageScorer extends Scorer
{

    private double titleScore = 100;
    private double descriptionScore = 30;
    private double h1Score = 80;
    private double pScore = 30;
    private double anchorLinkScore = 80;

    private String h1 = "h1";
    private String p = "p";
    private String anchorText = "a";

    private List<String> hypernyms;
    private List<String> hyponyms;
    private List<String> synonyms;

    public PageScorer(QueryAdapter queryAdapter) throws KnickerException {
        super(queryAdapter);

        hypernyms = queryAdapter.getHypernyms();
        hyponyms = queryAdapter.getHyponyms();
        synonyms = queryAdapter.getSynonyms();

    }

    // It checks the hit on the h1, meta descriptions and the title, and calculate a score
    public double predictScore(NodePage nodePage) throws UnsupportedEncodingException, SQLException {

        double resultScore = 0;

        String content = nodePage.getContent();

        try{

            Document doc = Jsoup.parse(content);

            resultScore += titleScore * getTitleMultiplier(doc);
            resultScore += descriptionScore * getDescriptionMultiplier(doc);
            resultScore += h1Score * getH1Multiplier(doc);
            resultScore += pScore * getPScore(doc);
            resultScore += anchorLinkScore * getAnchorLinkScore(doc);

        }
        catch (Exception e){
            System.out.println("Parser failed for some reason");
        }

        return resultScore;
    }

    public double countPercentageHit(String text){

        double hits = 0;

        String whiteSpace = " ";
        String[] words = queryAdapter.getWordsInQuery();
        String[] splittedText = text.split(whiteSpace);

        for(String word : words){
            for(String textWord : splittedText){
                if(word.toLowerCase().equals(textWord.toLowerCase())){
                    hits++;
                    break;
                }
            }
        }

        return hits/words.length;
    }

    private double simpleScorerByTag(Document doc, String tag){
        // Get the h1 if the page has one and compute the hit
        Elements tagList = doc.select(tag);

        if(tagList == null)
            return 0;

        double currentPercentage = 0;

        for(Element e : tagList){
            double temp = countPercentageHit(e.text());

            if(currentPercentage < temp)
                currentPercentage = temp;
        }

        return currentPercentage;
    }

    public double getTitleMultiplier(Document doc) {
        // Get the title and compute the hit
        String title = doc.title();

        return countPercentageHit(title);
    }

    private double getDescriptionMultiplier(Document doc) {
        Elements meta = doc.select("meta[name=description]");

        if(meta == null)
            return 0;

        // Get the description if the page has one and compute the hit
        String description = null;

        int percentage = 0;

        description = meta.attr("content");
        percentage += countPercentageHit(description);

        return percentage;
    }

    private double getH1Multiplier(Document doc) {
       return simpleScorerByTag(doc, h1);
    }

    private double getPScore(Document doc) {
        return simpleScorerByTag(doc, p);
    }

    private double getAnchorLinkScore(Document doc) {
        return simpleScorerByTag(doc, anchorText);
    }
}
