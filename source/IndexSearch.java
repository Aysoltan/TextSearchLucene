/* *********************************************************************************
 * Text Technology: SS2016
 * Project: Text Search with Lucene
 * Author: Aysoltan Gravina
 * 3142363
 * 
 * This program code based on the IndexSearch.java code from demo package of Lucene.
 * 
 * This Class creates an indexSearcher() method, that take a query as argument 
 * and returns paths of matched documents in an ArrayList.
 * ********************************************************************************/
package TextSearch;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

public class IndexSearch {

	static String indexPath = "index/";
	static String field = "contents";

	public static ArrayList<String> indexSearcher(String input) throws IOException, ParseException {

		// To store matching documents, that will be returned
		ArrayList<String> results = new ArrayList<String>();

		// Reads an indexed files
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
		IndexSearcher searcher = new IndexSearcher(reader);

		// Defines the Analyzer (use the same analyzer as for file indexing)
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser(field, analyzer);
		Query query = parser.parse(input);

		// gets results that matches the query
		int hitsPerPage = 20;
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		//System.out.println(hits.length + " Documents founded.");

		for (int i = 0; i < hits.length; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("path");
			results.add(i + 1 + ". " + path);
			//System.out.println(i + ". " + path);
		}

		reader.close();
		// Close the searcher!!!
		// searcher.close();
		return results;
	}
}