package crawler;

/**
 * Created by Andrea on 11/06/2017.
 */
public class Evaluation {

    private double score;
    private int depth;
    private boolean isGood;

    public Evaluation(double score, int depth, boolean isGood){
        this.score = score;
        this.depth = depth;
        this.isGood = isGood;
    }

    public double getScore() {
        return score;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isGood() {
        return isGood;
    }
}
