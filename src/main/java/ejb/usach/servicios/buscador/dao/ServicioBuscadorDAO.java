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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import ejb.usach.servicios.buscador.ServicioBuscadorBean;
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

	
	public static void indexarDocumentos() throws Exception {

		IndexWriter iwriter = null;
		try {
			SpanishAnalyzer analizador = new SpanishAnalyzer(version);
			
			Directory directorioIndex = new SimpleFSDirectory(new File(
					TablaValores.getValor(tableParametros, "path", "destino")));
			IndexWriterConfig config = new IndexWriterConfig(version,
					analizador);
			iwriter = new IndexWriter(directorioIndex, config);
			List<Opinion> lista = getListaObjetoOpiniones();
			for (Opinion o : lista) {
				addDoc(iwriter, o);
			}
		} catch (IOException ex) {
			throw ex;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (iwriter != null)
				iwriter.close();
		}
	}

	private static List<Opinion> getListaObjetoOpiniones() throws Exception {

		List<Opinion> opinion_lst = new ArrayList<Opinion>();
		String path = ServicioBuscadorBean.class.getClassLoader()
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
			while (input.ready()) {
				opinion = new Opinion((input.readLine().split(":"))[1].trim(),
						Integer.parseInt((input.readLine().split(":"))[1]
								.trim()),
						(input.readLine().split(":"))[1].trim(),
						(input.readLine().split(":"))[1].trim(),
						(input.readLine().split(":"))[1].trim(),
						(input.readLine().split(":"))[1].trim());
				input.readLine(); // empty line

				opinion_lst.add(opinion);
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
		doc.add(new StringField("marcaModelo", opinion.getMarcaModelo(),
				Field.Store.YES));
		doc.add(new StringField("author", opinion.getAuthor(), Field.Store.YES));
		doc.add(new TextField("opinion", opinion.getOpinion(), Field.Store.YES));
		doc.add(new TextField("lomejor", opinion.getLoMejor(), Field.Store.YES));
		doc.add(new TextField("lopeor", opinion.getLoPeor(), Field.Store.YES));
		doc.add(new IntField("valoracion", opinion.getValoracion(),
				Field.Store.NO));
		w.addDocument(doc);
	}

	private static Opinion docAdd(Document doc) {
		Opinion opinion = new Opinion();
		opinion.setMarcaModelo(doc.get("marcaModelo"));
		opinion.setAuthor(doc.get("author"));
		opinion.setOpinion(doc.get("opinion"));
		opinion.setLoMejor(doc.get("lomejor"));
		opinion.setLoPeor(doc.get("lopeor"));
		return opinion;
	}

}
