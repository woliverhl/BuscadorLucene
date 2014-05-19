package ejb.usach.servicios.buscador;

import ejb.usach.servicios.buscador.dao.ServicioBuscadorDAO;
import ejb.usach.servicios.buscador.to.Opinion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

import cl.infraestructura.util.TablaValores;

/**
 * Session Bean implementation class ServicioBuscadorBean
 */

@Stateless
public class ServicioBuscadorBean implements ServicioBuscador {

	private static final String archivoStopWord = "stopwords_lst.txt";

	private static final String tableParametros = "buscador.parametros";

	private static final Version version = Version.LUCENE_40;

	private static final Logger log = Logger
			.getLogger(ServicioBuscadorBean.class);

	/**
	 * @throws Exception
	 * @see ServicioBuscador#indexarDocumentos()
	 */
	public void indexarDocumentos() throws Exception {
		try {
			// Lear sobre este analizer
			// PerFieldAnalyzerWrapper wraper = new PerFieldAnalyzerWrapper();
			if (log.isDebugEnabled()) {
				log.debug("[ServicioBuscadorBean][indexarDocumentos] Inicio");
			}
			boolean existe = existeIndice();
			if (!existe){
				if (log.isDebugEnabled()) {
					log.debug("[ServicioBuscadorBean][indexarDocumentos] Indice no existe:" + existe);
				}
				ServicioBuscadorDAO.indexarDocumentos(leerStopWord());
			}else{
				if (log.isDebugEnabled()) {
					log.debug("[ServicioBuscadorBean][indexarDocumentos] Indice ya existe:" + existe);
				}
			}
			
		} catch (Exception e) {
			log.warn("[ServicioBuscadorBean][indexarDocumentos] ERROR: "
					+ e.getMessage());
			throw e;
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("[ServicioBuscadorBean][indexarDocumentos] Fin");
			}
		}
	}

	public List<Opinion> getQueryPorTermino(String termino, String strQuery,
			int hitsPerPage) throws Exception {
		try {
			if (existeIndice()){
				throw new Exception(TablaValores.getValor(tableParametros, "error", "001"));
			}
			
			if (log.isDebugEnabled()) {
				log.debug("[ServicioBuscadorBean][getQueryPorTermino] Inicio");
			}
			return ServicioBuscadorDAO.getQueryPorTermino(termino, strQuery,
					hitsPerPage);

		} catch (Exception e) {
			log.warn("[ServicioBuscadorBean][indexarDocumentos] ERROR: "
					+ e.getMessage());
			throw e;
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("[ServicioBuscadorBean][getQueryPorTermino] Fin");
			}
		}
	}

	public List<Opinion> getQueryParse(String termino, String strQuery,
			int hitsPerPage) throws Exception {
		try {
			if (existeIndice()){
				throw new Exception(TablaValores.getValor(tableParametros, "error", "001"));
			}
			
			if (log.isDebugEnabled()) {
				log.debug("[ServicioBuscadorBean][getQueryParse] Inicio");
			}
			return ServicioBuscadorDAO.getQueryParse(termino, strQuery,
					hitsPerPage);

		} catch (Exception e) {
			log.warn("[ServicioBuscadorBean][indexarDocumentos] ERROR: "
					+ e.getMessage());
			throw e;
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("[ServicioBuscadorBean][getQueryParse] Fin");
			}
		}
	}

	private static CharArraySet leerStopWord() {
		File f = new File(ServicioBuscadorBean.class.getClassLoader()
				.getResource(archivoStopWord).getPath());
		BufferedReader input;
		CharArraySet cas = new CharArraySet(version, 0, true);
		String line = "";
		try {
			input = new BufferedReader(new FileReader(f));

			while (input.ready()) {
				line = input.readLine();
				cas.add(line);
			}
			input.close();
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}

		return cas;
	}

	private static boolean existeIndice() {
		String path = TablaValores.getValor(tableParametros, "path", "destino");
		File f = new File(path);
		if (f.exists() && f.isDirectory()) {
			return true;
		}
		return false;
	}

}
