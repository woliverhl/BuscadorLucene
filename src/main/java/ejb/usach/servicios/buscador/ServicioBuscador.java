package ejb.usach.servicios.buscador;

import java.util.List;

import javax.ejb.Remote;

import ejb.usach.servicios.buscador.to.Opinion;

@Remote
public interface ServicioBuscador {

	public void indexarDocumentos() throws Exception;
	
	public List<Opinion> getListaObjetoOpiniones() throws Exception;
	
}
