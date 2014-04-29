package ejb.usach.servicios.buscador;

import ejb.usach.servicios.buscador.to.Opinion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import cl.infraestructura.util.TablaValores;

/**
 * Session Bean implementation class ServicioBuscadorBean
 */

@Stateless
public class ServicioBuscadorBean  {
	
	private static final String tableParametros="buscador.parametros";

	private static final String archivoOut="out_opinion.txt";
	/**
     * @throws IOException 
	 * @see ServicioBuscador#indexarDocumentos()
     */
    public void indexarDocumentos() throws Exception {
    	try{
    		SpanishAnalyzer analizador = new SpanishAnalyzer(Version.LUCENE_40);
    		//Lear sobre este analizer
    		//PerFieldAnalyzerWrapper wraper = new PerFieldAnalyzerWrapper();
    		
    		 Directory directorioIndex = new SimpleFSDirectory(new File(TablaValores.getValor(tableParametros, "path", "destino")));
    		 IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analizador);
    		 IndexWriter iwriter = new IndexWriter(directorioIndex,config);
    		 List<Opinion> lista = getListaObjetoOpiniones();
    		 for (Opinion o : lista) {
    			 addDoc(iwriter,o);
             }
             iwriter.close();
    	}
    	catch(IOException e){
    		throw e;
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }


	public List<Opinion> getListaObjetoOpiniones() throws Exception {
		
		List<Opinion> opinion_lst = new ArrayList<Opinion> ();
		String path = ServicioBuscadorBean.class.getClassLoader().getResource(archivoOut).getPath();
		if (path == null){
			throw new Exception("No existe el archivo dentro del contexto resource");
		}
		File f = new File (path);
		BufferedReader input;
		Opinion opinion = null;

		try {
			input = new BufferedReader (new FileReader (f));
			while (input.ready ()) {
				opinion = new Opinion ((input.readLine ().split (":"))[1].trim (),
					Integer.parseInt((input.readLine ().split (":"))[1].trim ()),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim (),
					(input.readLine ().split (":"))[1].trim ());
					input.readLine (); //empty line

				opinion_lst.add (opinion);
			} 	

		} catch (IOException ioEx) {
			ioEx.printStackTrace ();
		}
		return opinion_lst;
	}

	private static void addDoc(IndexWriter w, Opinion opinion)
			throws IOException {
		Document doc = new Document();
		doc.add(new StringField("marcaModelo", opinion.getMarcaModelo(), Field.Store.YES));
		doc.add(new StringField("author", opinion.getAuthor(), Field.Store.YES));
		doc.add(new TextField("opinion", opinion.getOpinion(), Field.Store.YES));
		doc.add(new TextField("lomejor", opinion.getLoMejor(), Field.Store.YES));
		doc.add(new TextField("lopeor", opinion.getLoPeor(), Field.Store.YES));
		doc.add(new IntField("valoracion", opinion.getValoracion(), Field.Store.NO));	
		w.addDocument(doc);
	}
	

}
