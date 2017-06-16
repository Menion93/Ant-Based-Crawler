package scorer;

/**
 * Created by Andrea on 05/03/2017.
 */
public class ScorerFactory
{
    String defaultScorer;
    String defaultQuery;

    public ScorerFactory(){

    }

    public ScorerFactory(String myScorer, String query){
        defaultScorer = myScorer;
        defaultQuery = query;
    }

    // Refactor with reflection???
    public Scorer getScorer(String myScorer, String query)
    {
        if(myScorer == "DictionaryScorer")
        {
            return new DictionaryScorer(query);
        }

        if(myScorer == "HeaderScorer")
        {
            return new HeaderScorer(query);
        }

        System.out.println("Scoring method not supported!");
        return null;
    }

    public Scorer getScorer(){

        if(defaultScorer == "DictionaryScorer")
        {
            return new DictionaryScorer(defaultQuery);
        }

        if(defaultScorer == "HeaderScorer")
        {
            return new HeaderScorer(defaultQuery);
        }
        return null;

    }
}
