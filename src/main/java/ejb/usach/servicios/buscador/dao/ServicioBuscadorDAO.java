package ejb.usach.servicios.buscador.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import cl.infraestructura.util.TablaValores;
import ejb.usach.servicios.buscador.to.Opinion;

public class ServicioBuscadorDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String tableParametros = "buscador.parametros";

	private static final String archivoOut = "out_opinion.txt";

	private static final Version version = Version.LUCENE_40;
	
	private static final File path = new File(TablaValores.getValor(
			tableParametros, "path", "destino"));

	private static ArrayList<Opinion> opinion_lst;
	
	private static final Logger log = Logger
			.getLogger(ServicioBuscadorDAO.class);
	
	public static void indexarDocumentos(CharArraySet stopWord) throws Exception {

		IndexWriter iwriter = null;
		try {
			if (log.isDebugEnabled()){
				log.debug("[ServicioBuscadorDAO][indexarDocumentos] Inicio");
			}
			SpanishAnalyzer analizador = new SpanishAnalyzer(version);
			
			if (stopWord != null){
				analizador = new SpanishAnalyzer(version, stopWord);
			}
			
			Directory directorioIndex = new SimpleFSDirectory(new File(
					TablaValores.getValor(tableParametros, "path", "destino")));
			IndexWriterConfig config = new IndexWriterConfig(version,
					analizador);
			iwriter = new IndexWriter(directorioIndex, config);
			List<Opinion> lista = getListaObjetoOpiniones();
			if (log.isDebugEnabled()){
				log.debug("[ServicioBuscadorDAO][indexarDocumentos] total lista: " + lista.size());
			}
			for (Opinion o : lista) {
				addDoc(iwriter, o);
			}
		} catch (IOException ex) {
			log.warn("[ServicioBuscadorDAO][indexarDocumentos] ERROR: " + ex.getMessage());
			throw ex;
		} catch (Exception e) {
			log.warn("[ServicioBuscadorDAO][indexarDocumentos] ERROR: " + e.getMessage());
			throw e;
		} finally {
			if (iwriter != null)
				iwriter.close();
			if (log.isDebugEnabled()){
				log.debug("[ServicioBuscadorDAO][indexarDocumentos] Fin");
			}
		}
	}

	private static List<Opinion> getListaObjetoOpiniones() throws Exception {

		opinion_lst = new ArrayList<Opinion>();
		String path = ServicioBuscadorDAO.class.getClassLoader()
				.getResource(archivoOut).getPath();
		if (path == null) {
			throw new Exception(
					"No existe el archivo dentro del contexto resource");
		}
		File f = new File(path);
		BufferedReader input;
		Opinion opinion = null;

		try {
			input = new BufferedReader(new FileReader(f));
			while (input.ready ()) {
				input.readLine (); //empty line
				opinion = new Opinion (
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim (),
					Integer.parseInt((input.readLine ().split (":"))[1].trim ()),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim ());
				input.readLine (); //empty line

				if (exist (opinion) == 0) 
					opinion_lst.add (opinion);
			}

		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}
		return opinion_lst;
	}

	public static List<Opinion> getQueryPorTermino(String termino,
			String strQuery, int hitsPerPage) throws Exception {

		ScoreDoc[] scoreDoc = null;
		IndexReader reader = null;
		List<Opinion> lista = new ArrayList<Opinion>();

		try {
			Directory index = FSDirectory.open(path);
			reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);

			Term t = new Term(termino, strQuery);

			Query query = new TermQuery(t);
			TopDocs tops = searcher.search(query, hitsPerPage);
			scoreDoc = tops.scoreDocs;
			for (ScoreDoc score : scoreDoc) {
				Document d = searcher.doc(score.doc);
				lista.add(docAdd(d));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}
		return lista;
	}
	
	public static List<Opinion> getQueryParse(String termino,
			String strQuery, int hitsPerPage) throws Exception {

		ScoreDoc[] scoreDoc = null;
		IndexReader reader = null;
		List<Opinion> lista = new ArrayList<Opinion>();

		try {
			Directory index = FSDirectory.open(path);
			reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);

			SpanishAnalyzer analyzer = new SpanishAnalyzer(version);
			
			QueryParser qp = new QueryParser(version, termino, analyzer);
			 
			Query query = qp.parse(strQuery);
		
			TopDocs tops = searcher.search(query, hitsPerPage);
			scoreDoc = tops.scoreDocs;
			for (ScoreDoc score : scoreDoc) {
				Document d = searcher.doc(score.doc);
				lista.add(docAdd(d));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}
		return lista;
	}
	
	public static List<Opinion> getQueryWrapper(String termino,
			String strQuery, int hitsPerPage) throws Exception {

		ScoreDoc[] scoreDoc = null;
		IndexReader reader = null;
		List<Opinion> lista = new ArrayList<Opinion>();

		try {
			Directory index = FSDirectory.open(path);
			reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);

			Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
			analyzerMap.put("IDNumber", new KeywordAnalyzer());
			analyzerMap.put("IDNumber", new SpanishAnalyzer(version));
			
			PerFieldAnalyzerWrapper _analyzer = new PerFieldAnalyzerWrapper(new StandardAnalyzer(version),analyzerMap); 
			
			SpanishAnalyzer analyzer = new SpanishAnalyzer(version);
			
			QueryParser qp = new QueryParser(version, termino, _analyzer);
			Query query = qp.parse(strQuery);
		
			TopDocs tops = searcher.search(query, hitsPerPage);
			scoreDoc = tops.scoreDocs;
			for (ScoreDoc score : scoreDoc) {
				Document d = searcher.doc(score.doc);
				lista.add(docAdd(d));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}
		return lista;
	}

	private static void addDoc(IndexWriter w, Opinion opinion)
			throws IOException {
		Document doc = new Document();
		doc.add(new StringField("marca", opinion.getMarca(),Field.Store.YES));
		doc.add(new StringField("modelo", opinion.getModelo(),Field.Store.YES));
		doc.add(new StringField("author", opinion.getAuthor(), Field.Store.YES));
		doc.add(new TextField("opinion", opinion.getOpinion(), Field.Store.YES));
		doc.add(new TextField("lomejor", opinion.getLoMejor(), Field.Store.YES));
		doc.add(new TextField("lopeor", opinion.getLoPeor(), Field.Store.YES));
		doc.add(new IntField("valoracion", opinion.getValoracion(), Field.Store.YES));
		w.addDocument(doc);
	}

	private static Opinion docAdd(Document doc) {
		Opinion opinion = new Opinion();
		opinion.setMarca(doc.get("marca"));
		opinion.setModelo(doc.get("modelo"));
		opinion.setAuthor(doc.get("author"));
		opinion.setOpinion(doc.get("opinion"));
		opinion.setLoMejor(doc.get("lomejor"));
		opinion.setLoPeor(doc.get("lopeor"));
		return opinion;
	}
	
	private static int exist (Opinion opinion) {
		int i;
		Opinion opinion_tmp;
		for (i = 0; i < opinion_lst.size(); i++) {
			opinion_tmp = opinion_lst.get (i);
			if (opinion_tmp.getMarca ().equals (opinion.getMarca ()) &&
				opinion_tmp.getModelo ().equals (opinion.getModelo ()) &&
				opinion_tmp.getAuthor ().equals (opinion.getAuthor ()) &&
				opinion_tmp.getValoracion () == opinion.getValoracion () &&
				opinion_tmp.getLoMejor ().equals (opinion.getLoMejor ()) &&
				opinion_tmp.getLoPeor ().equals (opinion.getLoPeor ()) &&
				opinion_tmp.getOpinion ().equals (opinion.getOpinion ()))
				return 1;
		}

		return 0;
	}  

}
