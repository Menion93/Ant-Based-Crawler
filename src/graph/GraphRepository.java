package graph;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;
import crawler.Evaluation;
import org.apache.commons.io.IOUtils;
import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.warc.WARCReaderFactory;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.MultipleFoundException;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;


public abstract class GraphRepository{

	protected String seed;

	protected static String EMPTY = "";

	protected int badPages;

	private boolean focusOnSinglePage;
	LinkParser parser;

	public GraphRepository(boolean focusOnSinglePage, String seedUrl, String suffix){
		this.parser = new LinkParser(suffix);
		this.focusOnSinglePage = focusOnSinglePage;
		this.seed  = seedUrl;
	}

	public abstract NodePage getNodePageRoot();

	public List<NodePage> expandNode(NodePage nodePage, HashMap<NodePage,Evaluation> id2score) throws IOException, SQLException {

		if(nodePage.hasNoSuccessor())
			return null;

		if (nodePage.getSuccessors().size() != 0)
			return nodePage.getSuccessors();

		List<NodePage> nodeSuccessors = new ArrayList<>();

		String body = nodePage.getContent();

		Set<String> links = parser.getLinks(body, nodePage.getId(), focusOnSinglePage);

		if(links.size() == 0){
			nodePage.hasNoSuccessor(true);
			return null;
		}

		for(String link : links){

			NodePage dummy = new NodePage(link, this);

			if(id2score.containsKey(dummy)){

				// Its slow but it saves memory
				for(NodePage keyNode : id2score.keySet())
					if(keyNode.getId().equals(link)){
						nodeSuccessors.add(keyNode);
						break;
					}

			}
			else{
				nodeSuccessors.add(dummy);
			}
		}

		nodePage.setSuccessors(nodeSuccessors);

		return nodeSuccessors;
	}

	public abstract String getContentPage(String id) throws UnsupportedEncodingException, SQLException;

	public int getBadPages() {
		return badPages;
	}
}