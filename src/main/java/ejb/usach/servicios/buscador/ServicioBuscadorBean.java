package ejb.usach.servicios.buscador;

import ejb.usach.servicios.buscador.dao.ServicioBuscadorDAO;
import ejb.usach.servicios.buscador.to.Opinion;

import java.util.List;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class ServicioBuscadorBean
 */

@Stateless
public class ServicioBuscadorBean {

	/**
	 * @throws Exception
	 * @see ServicioBuscador#indexarDocumentos()
	 */
	public void indexarDocumentos() throws Exception {
		try {
			// Lear sobre este analizer
			// PerFieldAnalyzerWrapper wraper = new PerFieldAnalyzerWrapper();
			ServicioBuscadorDAO.indexarDocumentos();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<Opinion> getQueryPorTermino(String termino, String strQuery, int hitsPerPage)
			throws Exception {
		try {

			return ServicioBuscadorDAO.getQueryPorTermino(termino, strQuery, hitsPerPage);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Opinion> getQueryParse(String termino, String strQuery, int hitsPerPage)
			throws Exception {
		try {

			return ServicioBuscadorDAO.getQueryParse(termino, strQuery, hitsPerPage);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
