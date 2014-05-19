package ejb.usach.servicios.buscador;

import java.util.List;

import javax.ejb.Local;

import ejb.usach.servicios.buscador.to.Opinion;

@Local
public interface ServicioBuscador {

	public void indexarDocumentos() throws Exception;
	
	public List<Opinion> getQueryPorTermino(String termino, String strQuery, int hitsPerPage) throws Exception;

	public List<Opinion> getQueryParse(String termino, String strQuery, int hitsPerPage) throws Exception; 
}
