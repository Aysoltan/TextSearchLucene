# TextSearchWithLucene

This is an application for the text search with full-featured text search engine library Lucene.
For texts search Wikipedia articles in German are used ( size of Dump ca. 4 GB).

This application contains four parts: 

    - IndexFiles;
    - IndexSearch;
    - TextSearchGUI;
    - WikipediaXMLParser.

IndexSearch returns the path to the matched files for the given query.

There are a simple GUI for the Text Search that gets a query and calls the indexSearcher() function from IndexSearch for this query and output the founded files as a list.  

If you want to see the content of the founded files, just click on it and the content of the file will be shown in the separate window.

To get the .txt files from Wikipedia XML Dump you can use:

    1.Wikiforia: converts Wikipedia own markup into XML (free software)
    2.WikipediaXMLParser: chunkes the XML file into .txt files
The .jar files are in the folder WikipediaFiles/TXTFilesExtractor.

For more details see the presentation slides to this project comments in the source codes.


---

USAGE:

Default Usage: 

- java -jar TextSearchGUI.jar
- and click search button for e.g. „Alexandre Dumas“ query

IndexFiles:

- delete all files in the index/ directory which are used as default
- java -jar IndexFiles.jar -docs path/to your/doc.txt -index index/

WikipediaXMLParser:

- cd WikipediaFiles/TXTFilesExtractor/
- java -jar WikipediaXMLParser.jar [-path path/to your/file.xml]

Wikiforia:

- java -jar wikiforia-1.0-SNAPSHOT.jar 
- pages [path to the file ending with multistream.xml.bz2] 
- output [output.xml]


---

USAGE with own corpus:

Step 1. Remove all .txt files from data/ folder: cd data/ -> rm *.txt

Step 2. Remove all files from index/ : cd index/ -> rm _*

Step 3. Copy your .txt files into data/ folder: cp *.txt data/

Step 4. Index your .txt files: java -jar IndexFiles.jar -docs data/ -index index/

Step 5. Check whether all index files are in the index folder. If not just move it manually  with mv _*

Step 6. Call Text Search GUI: java -jar TextSearchGUI.jar



---

USAGE for creating .txt files from Wikipedia XML Dump

Step 1. Download Wikipedia XML Dump AND its index under https://dumps.wikimedia.org/backup-index.html

Step 2. Download Wikiforia under https://github.com/marcusklang/wikiforia

Step 3. Convert the Wikipedia XML Dump into XML file with:
cd wikiforia -> cd dist/
java -jar wikiforia-1.0-SNAPSHOT.jar 
-pages [path to the file ending with multistream.xml.bz2] 
-output [output.xml]

Step 4: Chunk the XML file into .txt files with WikipediaXMLParser:
cd WikipediaFiles/TXTFilesExtractor/
java -jar WikipediaXMLParser.jar [-path path/to your/file.xml]







