package scorer;

/**
 * Created by Andrea on 05/03/2017.
 */
public class ScorerFactory
{
    public Scorer getScorer(String myScorer)
    {
        if(myScorer == "DictionaryScorer")
        {
            return new DictionaryScorer();
        }

        if(myScorer == "ClassifierScorer")
        {
            return new ClassifierScorer();
        }

        System.out.println("Scoring method not supported!");
        return null;
    }
}
