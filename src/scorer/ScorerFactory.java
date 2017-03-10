package scorer;

/**
 * Created by Andrea on 05/03/2017.
 */
public class ScorerFactory
{
    public Scorer getScorer(String myScorer, String query)
    {
        if(myScorer == "DictionaryScorer")
        {
            return new DictionaryScorer(query);
        }

        if(myScorer == "ClassifierScorer")
        {
            return new ClassifierScorer(query);
        }

        System.out.println("Scoring method not supported!");
        return null;
    }
}
