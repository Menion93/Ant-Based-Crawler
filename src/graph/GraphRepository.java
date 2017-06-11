package graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

/**
 * Created by Nicholas on 06/03/2017.
 */
public abstract class GraphRepository{

	public String seed = "https://www.tomshw.it/forum/";

	LinkParser parser;

	public GraphRepository(){
		parser = new LinkParser();
	}

	public abstract NodePage getNodePageRoot();

	public abstract List<NodePage> expandNode(NodePage nodePage) throws IOException;

	public abstract String getContentPage(String id);

}