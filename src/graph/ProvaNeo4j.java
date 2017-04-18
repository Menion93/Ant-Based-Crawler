package graph;
 
import java.io.IOException;
 
import org.joda.time.DateTime;
 
public class ProvaNeo4j {
 
    public static void main(String[] args) throws IOException {
        String warcPath = "C:/Users/campus39/workspace/CC-MAIN-20170116095119-00000-ip-10-171-10-70.ec2.internal.warc.gz";
 
        GraphRepository graph = new GraphRepository();
        graph.createDB("");
        graph.loadWARC2graphDB(warcPath);   //use this command to upload the WARC file in neo4j; if it's already done, comment this line
        graph.printAllNodes();              //use this command only if there are nodes in the graph
        graph.setAllLinks();
        graph.printAllLinks();
        //TODO more tests with links
        graph.shutDown();
        DateTime dt = new DateTime();
        System.out.println("Fine processo:\t" + dt.getHourOfDay() + ":" + dt.getMinuteOfHour() + ":" + dt.getSecondOfMinute());
 
    }
 
}