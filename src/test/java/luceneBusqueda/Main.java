package luceneBusqueda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.document.Field;


public class Main {
	private static String rutaStopWord = "/home/nnarria/workspace/luceneBusqueda/src/stopwords_lst.txt";
	
	public static void main(String[] args) throws IOException, ParseException {
		ArrayList<Opinion> opinion_lst;
		opinion_lst = GestionOpinion.getLstOpinion ();
		CharArraySet stopWords_lst = leerStopWord ();
		System.out.println ("cantidad: " + opinion_lst.size());
		
		SpanishAnalyzer spanish_analyzer = new SpanishAnalyzer (Version.LUCENE_40, stopWords_lst);
		Directory index = new RAMDirectory();

		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, spanish_analyzer);
		IndexWriter w = new IndexWriter(index, config);
		
		for (int i = 0; i < opinion_lst.size(); i++) {
			Opinion op = opinion_lst.get(i);
			addDoc (w, op.getOpinion(), op.getLoMejor(), op.getValoracion(), op.getMarca(), op.getModelo());
		}
		w.close ();
		
		 // 2. query
	    String querystr = args.length > 0 ? args[0] : "barato confiable";
	    Query q = new QueryParser(Version.LUCENE_40, "opinion", spanish_analyzer).parse(querystr);

	    // 3. search
	    int hitsPerPage = 11;
	    IndexReader reader = DirectoryReader.open(index);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
	    searcher.search(q, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    
	    // 4. display results
	    System.out.println("Found " + hits.length + " hits.");
	    for(int i=0;i<hits.length;++i) {
	      int docId = hits[i].doc;
	      Document d = searcher.doc(docId);
	      System.out.println((i + 1) + ". " + d.get("valoracion") + "\t" + d.get("marca") + "\t" + d.get("modelo") + "\t\t" + d.get("opinion"));
	    }

	    reader.close();		
	}
	
	private static void addDoc(IndexWriter w, String opinion, String loMejor, int valoracion, String marca, String modelo) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("opinion", opinion, Field.Store.YES));
		doc.add(new TextField("lomejor", loMejor, Field.Store.YES));
		doc.add(new IntField("valoracion", valoracion, Field.Store.YES));
		doc.add(new StringField("marca", marca, Field.Store.YES));
		doc.add(new StringField("modelo", modelo, Field.Store.YES));
		w.addDocument(doc);
	}
	
	public static CharArraySet leerStopWord () {
		File f = new File (rutaStopWord);
		BufferedReader input;
		CharArraySet cas = new CharArraySet(Version.LUCENE_40, 0, true);
		String line = "";
		try {
			input = new BufferedReader (new FileReader (f));
			
			while (input.ready ()) {
				line = input.readLine ();
				cas.add (line);
			}
			input.close();
		} catch (IOException ioEx) {
			ioEx.printStackTrace ();
		}

		return cas;
	}

}
