package ejb.usach.servicios.buscador;

import java.util.List;

import javax.ejb.Remote;

import org.apache.lucene.search.ScoreDoc;

import ejb.usach.servicios.buscador.to.Opinion;

@Remote
public interface ServicioBuscador {

	public void indexarDocumentos() throws Exception;
	
	public List<Opinion> getListaObjetoOpiniones() throws Exception;
	
	public ScoreDoc[] getQueryPorTermino(String termino, String strQuery, int hitsPerPage) throws Exception;
	
}
