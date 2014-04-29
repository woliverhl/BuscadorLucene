package cl.usach.servicios.buscador;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import org.apache.log4j.Logger;

import ejb.usach.servicios.buscador.ServicioBuscadorBean;

@ManagedBean
@RequestScoped
@Named
public class BuscardorBackingMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String variablePaso;
	
	private static final Logger log = Logger
			.getLogger(BuscardorBackingMB.class);

	@EJB
	private ServicioBuscadorBean servicioBuscadorBean;

	public String getVariablePaso() {
		return variablePaso;
	}

	public void setVariablePaso(String paso) {
		this.variablePaso = paso;
	}

	public String indexarDocumentos() {
		try {
			log.debug("[BuscardorBackingMB][indexarDocumentos] Inicio");
			this.getServicioBuscadorBean().indexarDocumentos();
		} catch (Exception e) {
			log.warn("[BuscardorBackingMB][indexarDocumentos] Error: "
					+ e.getMessage());
		}
		this.setVariablePaso("indexarDocumentos");
		return "indexarDocumentos";
	}

	public ServicioBuscadorBean getServicioBuscadorBean() throws Exception {
		if (servicioBuscadorBean == null)
			throw new Exception("EL ejb no se ha inicializado");
		return servicioBuscadorBean;
	}

	public void setServicioBuscadorBean(ServicioBuscadorBean servicioBuscadorBean) {
		this.servicioBuscadorBean = servicioBuscadorBean;
	}
}
