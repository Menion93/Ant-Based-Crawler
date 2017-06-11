package graphBuilding;

import graph.LinkParser;
import org.apache.commons.io.IOUtils;
import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.warc.WARCReaderFactory;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Andrea on 11/06/2017.
 */
public class GraphBuilder {

    private static final File DB_PATH = new File("target/warc-graph-db");

    GraphDatabaseService graphDB;

    private static enum RelTypes implements RelationshipType {
        LINKS
    }

    private static final Label WEBPAGE = Label.label("Webpage");

    private static final String GET = "GET";
    private static final String PAYLOAD = "payload";
    private static final String LINKS = "links";


    /**
     * creates a graph DB
     * @param newDB if(newDB=="NEW_DB") creates a new DB
     *              else maintain previous records in DB
     * @throws IOException
     */
    public void createDB(String newDB) throws IOException{
        if(newDB=="NEW_DB")
            FileUtils.deleteRecursively(DB_PATH);
        graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        registerShutdownHook(graphDB);
    }

    /**
     * inserting a single node in the graph
     * @param get
     * @param http
     */
    public void insertNode(String get, String http, String[] links){
        try(Transaction tx = graphDB.beginTx()){
            Node node = graphDB.createNode(Label.label("Webpage"));
            node.setProperty(GET, get);
            node.setProperty(PAYLOAD, http);
            node.setProperty(LINKS, links);
            tx.success();
        }
    }


    /**
     * retrieves all nodes in the graph DB and writes them on file "the-file-name.txt"
     * @throws IOException
     */
    public void printAllNodes() throws IOException{

        try ( Transaction tx = graphDB.beginTx() )
        {
            PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
            ResourceIterable<Node> iterable = graphDB.getAllNodes();
            for (Node node : iterable) {
                String get = (String) node.getProperty(GET);
                writer.println(get);

                String payload = (String) node.getProperty(PAYLOAD);
                writer.println(payload);
                String[] links = (String[]) node.getProperty(LINKS);
                for (int i = 0; i < links.length; i++) {
                    writer.println(links[i]);
                }
                writer.println("********************************************");
            }
            writer.close();
            tx.success();
        }
    }

    /**
     * creates links between nodes
     */
    public void setAllLinks() {
        try(Transaction tx = graphDB.beginTx()){
            ResourceIterable<Node> iterable = graphDB.getAllNodes();
            for (Node node : iterable){
                System.out.println("*************\nNODO:\t" + node.getProperty(GET));
                String[] nodeLinks = (String[]) node.getProperty(LINKS);
                int notPresentLinks = 0;

                String url, dns = (String) node.getProperty(GET);
                if(dns.contains("/"))
                    dns = dns.substring(0, dns.indexOf('/'));

                for (String path : nodeLinks) {

					/* there are links in this form "/partnership" and are meant to be link to a page of the same site
					 * and for our task we create the URL as follows */
                    if(path.length() > 0 && path.charAt(0) == '/')
                        url = dns + path;
                    else
                        url = path;
                    System.out.println(url);

                    try {
                        Node urlNode = graphDB.findNode(WEBPAGE, GET, url);
                        if(urlNode != null && !node.getProperty(GET).equals(urlNode.getProperty(GET))){
                            node.createRelationshipTo(urlNode, RelTypes.LINKS);
                            System.out.println("++++++++++" + node.getProperty(GET) + "\t---\t" + urlNode.getProperty(GET));
                        }
                        else{
                            notPresentLinks++;
                            System.out.println("link non presente nel grafo");
                        }
                    } catch (MultipleFoundException e) { System.out.println("ERROR: multiple nodes with the same name!"); }
                }
                node.setProperty("LinksNotPresent", notPresentLinks);
            }
            tx.success();
        }
    }


    public void printAllLinks(){
        try(Transaction tx = graphDB.beginTx()){
            ResourceIterable<Relationship> relationships = graphDB.getAllRelationships();
            System.out.println("PRINT RELATIONSHIPS");
            relationships.forEach(relationship ->
            {System.out.print(relationship.getStartNode().getProperty(GET)); System.out.print("\t------\t");
                System.out.println(relationship.getEndNode().getProperty(GET));
            });
            tx.success();
        }
    }

    /**
     * loads a WARC file
     * @param warcPath
     * @throws IOException
     */
    public void loadWARC2graphDB(String warcPath) throws IOException{
        FileInputStream is = new FileInputStream(warcPath);
        ArchiveReader ar = WARCReaderFactory.get(warcPath, is, true);

        String get = null, http;
        LinkParser linkParser = new LinkParser();
        List<String> links;
        int i = 0;
		/* iterates on each record of the WARC file, where a record could be the field "HTTP GET" of the web page,
		 * the field "HTTP response" that contains the HTML code of the web page and the field "fetch time",
		 * unuseful for our task */
        for(ArchiveRecord r : ar) {
            byte[] rawData = IOUtils.toByteArray(r, r.available());
            String content = new String(rawData);

            if(i % 3 == 1){		//extracts URL of site by "HTTP GET" field, to use it as a key property of the node
                String[] lines = content.split("\\r?\\n");
                get = lines[1].substring(6, lines[1].length()) + lines[0].substring(4, lines[0].length()-9);
            }
            if(i % 3 == 2){		//take all the HTML of site from "HTTP response" field and extract all link from it
                http = content;
                links = linkParser.getLinks(content, "");
                insertNode(get, http, links.toArray(new String[links.size()]));	//creates a node with values previously extracted
                System.out.println("Record: " + (i / 3));
            }
            i++;
            //			if (i++ > 20) break;
        }
    }

    public void shutDown(){
        System.out.println("\nShutting down DB ...");
        graphDB.shutdown();
    }

    private void registerShutdownHook(GraphDatabaseService graphDB){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){graphDB.shutdown();}
        });
    }

    public void insertDummy1(){
        String[] links = {"youtube.com"};
        insertNode("facebook.com", "youtube.com", links);
    }

    public void insertDummy2(){
        String[] links = {"facebook.com"};
        insertNode("youtube.com", "facebook.com", links);
    }

}
