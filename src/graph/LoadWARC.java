package graph;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.IOUtils;
import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.warc.WARCReaderFactory;

/**
 * A raw example of how to process a WARC file using the org.archive.io package.
 * Common Crawl S3 bucket without credentials using JetS3t.
 */
public class LoadWARC {
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// Set up a local compressed WARC file for reading (in this version express the entire path)
		String fn = "/home/nicholas/Documenti/ProgettoSII/project/CommonCrawl/compressed CC-MAIN-20170116095119-00000-ip-10-171-10-70.ec2.internal.warc/CC-MAIN-20170116095119-00000-ip-10-171-10-70.ec2.internal.warc.gz";
		FileInputStream is = new FileInputStream(fn);
		// The file name identifies the ArchiveReader and indicates if it should be decompressed
		ArchiveReader ar = WARCReaderFactory.get(fn, is, true);
		
	    PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
	
		// Once we have an ArchiveReader, we can work through each of the records it contains
		int i = 0;
		for(ArchiveRecord r : ar) {			
			// If we want to read the contents of the record, we can use the ArchiveRecord as an InputStream
			// Create a byte array that is as long as the record's stated length
			byte[] rawData = IOUtils.toByteArray(r, r.available());
			
			String content = new String(rawData);
			System.out.println(content);			
		    writer.println(content);
			
			// Pretty printing to make the output more readable 
			System.out.println("=-=-=-=-=-=-=-=-=");
			if (i++ > 5000) break; 
		}
	    writer.close();
	}
}