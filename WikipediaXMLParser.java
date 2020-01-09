
/* ****************************************************************************************
 * Text Technology: SS2016
 * Project: Text Search with Lucene
 * Author: Aysoltan Gravina
 * 3142363
 *
 *
 * This Class takes an output.xml file created from Wikipedia XML Dump with Wikiforia 
 * from the folder WikipediaXML (bei default), else give the path to folder as argument.
 * Usage: java WikipediaXML.java -path [path to the xml file]. 
 * Then the file will be chunked into separate .txt files and store into ../../data/ folder.
 *******************************************************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WikipediaXMLParser {

	public static void main(String[] args) throws IOException {

		String pathToXML = "";

		for (int i = 0; i < args.length; i++) {
			/*** Sets parameter for XML folder */
			if ("-path".equals(args[i])) {
				pathToXML = args[i + 1];
				i++;
			} else {
				pathToXML = "../WikipediaXML/output.xml";
			}
		}

		System.out.println("Read the .xml file ...");

		// Reads the File
		FileReader fr = new FileReader(pathToXML);
		BufferedReader br = new BufferedReader(fr);

		String content = "";
		int i = 0;
		String line = "";
		String head = "<?xml version=1.0 encoding=utf-8?>";
		String root = "<pages>";
		String endRoot = "</pages>";

		while ((line = br.readLine()) != null) {
			if (line.contains(head) | line.contains(root) | line.contains(endRoot)) {
				continue;

			}

			if (line.contains("<page id=") && !line.contains("</page>")) {
				//schaue hier
				String text = line.substring(line.indexOf(">") + 1, line.length());
				content = content + text;

			}

			if (!line.contains("<page id=") && !line.contains("</page>") && !line.isEmpty()) {
				content = content + line;

			}

			if (line.contains("<page id=") && line.contains("</page>")) {
				// wenn zuesrst <page id=...>, dann Text und dann </page> kommt

				if (line.indexOf("<page id") < line.indexOf("</page>")) {
					// dann hole den Text dazwischen
					String text = line.substring(line.indexOf(">") + 1, line.indexOf("</page>"));
					content = content + text;

					i++;
					File file = new File("file" + i + ".txt");
					FileWriter writer = new FileWriter("../../data/" + file);
					writer.write(content);
					writer.close();
					content = "";
				}

				// wenn zuest </page> Ïund dann <page id=...>
				if (line.indexOf("</page>") < line.indexOf("<page id=")) {
					// dann hole den text vor </page> und beende den string
					String text = line.substring(0, line.indexOf("</page>"));
					content = content + text;

					i++;
					File file = new File("file" + i + ".txt");
					FileWriter writer = new FileWriter("../../data/" + file);
					writer.write(content);
					writer.close();
					content = "";
					
					// und weise den Inhalt nach <page id= ...>
					// hier sehr vorsichtig:
					// Zeile konnte </page>, <page id=...>, text, </page>
					// Oder <page id= ...>, text, </page>, <page id= ...>
					// beinhaltenÏ
					text = line.substring((line.lastIndexOf(">") + 1), line.length());
					content = content + text;
				}
			}

			if (line.contains("</page>") && !line.contains("<page id=")) {
				// lese alles, was vor dem </page> steht
				String text = line.substring(0, line.indexOf("</page>"));
				content = content + text;
	
				i++;
				File file = new File("file" + i + ".txt");
				FileWriter writer = new FileWriter("../../data/" + file);
				writer.write(content);
				writer.close();
				content = "";
				
				
				text = line.substring(line.indexOf("</page>") + "</page>".length(), line.length());
				content = content + " " + text;

			}
		}
		
		i++;
		File file = new File("file" + i + ".txt");
		FileWriter writer = new FileWriter("../../data/" + file);
		writer.write(content);
		writer.close();
		content = "";
		br.close();
		System.out.println("The System has completed ...");

	}
}