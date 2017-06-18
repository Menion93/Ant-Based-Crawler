package util;

import crawler.Edge;
import graph.NodePage;

import java.util.List;

/**
 * Created by Andrea on 18/06/2017.
 */
public class GraphUtils {

    public static boolean contains(List<Edge> path, NodePage page){

        boolean contained = false;

        for(Edge e : path){
            if(e.getFrom().equals(page.getId()) || e.getTo().equals(page.getId())){
                contained = true;
                break;
            }
        }

        return contained;
    }
}
