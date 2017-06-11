package crawler;

/**
 * Created by Andrea on 11/06/2017.
 */
public class Evaluation {

    private double score;
    private int depth;

    public Evaluation(double score, int depth){
        this.score = score;
        this.depth = depth;
    }

    public double getScore() {
        return score;
    }

    public int getDepth() {
        return depth;
    }
}
