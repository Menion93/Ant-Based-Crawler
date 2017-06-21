package scorer;

import net.jeremybrooks.knicker.KnickerException;
import scorer.PageScorer.PageScorer;

import java.io.FileNotFoundException;

/**
 * Created by Andrea on 05/03/2017.
 */
public class ScorerFactory
{
    String defaultScorer = "PageScorer";
    String defaultQuery;
    QueryAdapter queryAdapter;

    public ScorerFactory() {

    }

    public ScorerFactory(String myScorer, String query){
        defaultScorer = myScorer;
        defaultQuery = query;
    }

    // Refactor with reflection???
    public Scorer getScorer(String myScorer, String query) throws KnickerException, FileNotFoundException {
        if(queryAdapter == null)
            queryAdapter = new QueryAdapter(query);

        if(myScorer == "DictionaryScorer")
        {
            return new DictionaryScorer(queryAdapter);
        }

        if(myScorer == "PageScorer")
        {
            return new PageScorer(queryAdapter);
        }

        System.out.println("Scoring method not supported!");
        return null;
    }

    public Scorer getScorer() throws FileNotFoundException, KnickerException {

        if(queryAdapter == null)
            queryAdapter = new QueryAdapter(defaultQuery);

        if(defaultScorer == "DictionaryScorer")
        {
            return new DictionaryScorer(queryAdapter);
        }

        if(defaultScorer == "PageScorer")
        {
            return new PageScorer(queryAdapter);
        }

        return null;

    }
}
