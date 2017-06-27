package util;

import crawler.Evaluation;
import graph.NodePage;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Andrea on 11/06/2017.
 */
public class RankingComparator implements Comparator<Map.Entry<NodePage,Evaluation>> {

    // We can implement here a ranking function, now it simply order by score
    @Override
    public int compare(Map.Entry<NodePage,Evaluation> o1, Map.Entry<NodePage,Evaluation> o2) {
        return - Double.compare(o1.getValue().getScore(), o2.getValue().getScore());
    }
}
