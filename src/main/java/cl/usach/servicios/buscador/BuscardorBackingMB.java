package cl.usach.servicios.buscador;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

import org.apache.log4j.Logger;

import ejb.usach.servicios.buscador.ServicioBuscadorBean;
import ejb.usach.servicios.buscador.to.Opinion;

@ManagedBean
@RequestScoped
@Named
public class BuscardorBackingMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(BuscardorBackingMB.class);
	
	private List<Opinion> listaOpinion;
	
	@EJB
	private ServicioBuscadorBean servicioBuscadorBean;

	public String getInit(){
		this.hacerQuery();
		return null;
	}
	
	
	public String indexarDocumentos() {
		try {
			log.debug("[BuscardorBackingMB][indexarDocumentos] Inicio");
			this.getServicioBuscadorBean().indexarDocumentos();
		} catch (Exception e) {
			log.warn("[BuscardorBackingMB][indexarDocumentos] Error: "
					+ e.getMessage());
		}
		return "indexarDocumentos";
	}
	
	public String hacerQuery() {
		try {
			log.debug("[BuscardorBackingMB][indexarDocumentos] Inicio");
			if (listaOpinion == null){
				listaOpinion = this.getServicioBuscadorBean().getQueryParse("opinion","no hay auto mas durable", 10);
			}
		} catch (Exception e) {
			log.warn("[BuscardorBackingMB][indexarDocumentos] Error: "
					+ e.getMessage());
		}
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

	public List<Opinion> getListaOpinion() {
		return listaOpinion;
	}

	public void setListaOpinion(List<Opinion> listaOpinion) {
		this.listaOpinion = listaOpinion;
	}
	
	
}
