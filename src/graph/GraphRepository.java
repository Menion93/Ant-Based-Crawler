package graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.warc.WARCReaderFactory;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;

/**
 * Created by Nicholas on 06/03/2017.
 */
public class GraphRepository{

	private static final File DB_PATH = new File("target/warc-graph-db");

	GraphDatabaseService graphDB;

	private static enum RelTypes implements RelationshipType{
		LINKS
	}
	
	public GraphRepository(){

	}

	/**
	 * creates a graph DB
	 * @param newDB if(newDB=="NEW_DB") creates a new DB
	 * 				else maintain previous records in DB
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
			Node node = graphDB.createNode();
			node.setProperty("HTTP GET", get);
			node.setProperty("payload", http);
			node.setProperty("links", links);


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
		List<String> links = new LinkedList<>();
		int i = 0;
		for(ArchiveRecord r : ar) {		
			byte[] rawData = IOUtils.toByteArray(r, r.available());
			String content = new String(rawData);

			if(i % 3 == 1)
				get = content;
			if(i % 3 == 2){
				http = content;
				links = linkParser.getLinks(content);
				insertNode(get, http, links.toArray(new String[links.size()]));
				System.out.println("Record: " + (i / 3));
			}
			i++;
//			if (i++ > 10000) break; 
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
		    LinkParser linkParser = new LinkParser();
			ResourceIterable<Node> iterable = graphDB.getAllNodes();
			for (Node node : iterable) {
				String get = (String) node.getProperty("HTTP GET");
//				System.out.println(get);
				writer.println(get);
				
				String payload = (String) node.getProperty("payload");
//				System.out.println(payload);
//				System.out.println("********************************************");
				writer.println(payload);
				String[] links = (String[]) node.getProperty("links");
				for (int i = 0; i < links.length; i++) {
					writer.println(links[i]);
				}
				writer.println("********************************************");
			}
			writer.close();
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

	
	

	
	public NodePage getNodePageRoot(){
		return new NodePage();
	}

	public NodePage[] expandeNode(NodePage nodePage){
		return new NodePage[]{};
	}
}
