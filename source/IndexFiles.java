
/* *******************************************************************************
 * Text Technology: SS2016
 * Project: Text Search with Lucene
 * Author: Aysoltan Gravina
 * 3142363
 * 
 * This program code based on the IndexFiles.java code from demo package of Lucene.
 * 
 * This Class creates an Index for the Input files.
 * *******************************************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/*** Index all text files under a directory. */
public class IndexFiles {

	/** Index all text files under a directory. */
	public static void main(String[] args) {
		/***
		 * If there are no argument parameters given it will be created a folder
		 * named index/ for the indexed files and a folder named data/ for the
		 * Wikipedia files in the same working directory.(We want to index the
		 * files only once for given corpus)
		 */
		String indexPath = "";
		String docsPath = "";

		if (args.length <= 0) {
			indexPath = "index/";
			docsPath = "data/";
		}
		/*** If the command-line arguments given */
		for (int i = 0; i < args.length; i++) {
			/*** Sets parameters for index folder */
			if ("-index".equals(args[i])) {
				indexPath = args[i + 1];
				i++;
			}
			/*** Sets paramenters for data folder */
			else if ("-docs".equals(args[i])) {
				docsPath = args[i + 1];
				i++;
			}
		}

		if (docsPath == null) {
			System.exit(1);
		}
		/*** Gets the Path to the Folder with Documents */
		final Path docDir = Paths.get(docsPath);
		if (!Files.isReadable(docDir)) {
			System.out.println("Document directory does not exist or is not readable");
			System.exit(1);
		}
		/*** Build an IndexWriter Instance */
		try {
			System.out.println("Indexing to directory '" + indexPath + "'...");

			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(dir, config);
			indexDocs(writer, docDir);
			writer.close();

		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}
	}

	/**
	 * Loop through the files in the directory and call for each document the
	 * method indexDoc
	 */
	static void indexDocs(final IndexWriter writer, Path docDir) throws IOException {
		if (Files.isDirectory(docDir)) {
			/*** Index all files in directory */
			File[] files = new File(docDir.toString()).listFiles();
			for (File f : files) {
				indexDoc(writer, f.toPath());

			}
		} else {
			indexDoc(writer, docDir);
		}
	}

	/** Index a single document */
	static void indexDoc(IndexWriter writer, Path docDir) throws IOException {
		try (InputStream stream = Files.newInputStream(docDir)) {
			/*** Create a new, empty document */
			Document doc = new Document();
			Field pathField = new StringField("path", docDir.toString(), Field.Store.YES);
			doc.add(pathField);
			doc.add(new TextField("contents",
					new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
			/*** New index, so we just add the document */
			if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
				System.out.println("adding " + docDir);
				writer.addDocument(doc);
			}
			/*** Existing index, so we use update Document */
			else {
				System.out.println("updating " + docDir);
				writer.updateDocument(new Term("path", docDir.toString()), doc);
			}
		}
	}
}