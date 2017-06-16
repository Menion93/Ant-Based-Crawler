package graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;
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

	public String seed = "http://www.hwupgrade.it/forum/";
	//public String seed = "http://www.fotografidigitali.it/software-fotografia/software-fotografia/";

	LinkParser parser;

	public GraphRepository(){
		parser = new LinkParser();
	}

	public abstract NodePage getNodePageRoot();

	public List<NodePage> expandNode(NodePage nodePage) throws IOException {

		if(nodePage.hasNoSuccessor())
			return null;

		if (!nodePage.hasNoSuccessor() && nodePage.getSuccessors().size() != 0)
			return nodePage.getSuccessors();

		List<NodePage> nodeSuccessors = new ArrayList<>();

		String body = nodePage.getContent();

		List<String> links = parser.getLinks(body, nodePage.getId());


		if(links.size() == 0){
			nodePage.hasNoSuccessor(true);
			return null;
		}

		for(String link : links){
			nodeSuccessors.add(new NodePage(link, this));
		}

		nodePage.setSuccessors(nodeSuccessors);

		return nodeSuccessors;
	}

	public abstract String getContentPage(String id);

}