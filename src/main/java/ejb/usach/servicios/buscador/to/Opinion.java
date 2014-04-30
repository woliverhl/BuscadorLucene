package ejb.usach.servicios.buscador.to;

public class Opinion {
	
	private String marcaModelo;
	private int valoracion;
	private String author;
	private String opinion;
	private String loMejor;
	private String loPeor;
	
	public Opinion (String marcaModelo, int valoracion, String author, String opinion, String loMejor, String loPeor) {
		this.marcaModelo = marcaModelo;
		this.valoracion = valoracion;
		this.author = author;
		this.opinion = opinion;
		this.loMejor = loMejor;
		this.loPeor = loPeor;
	}
	
	public Opinion () {
		
	}

	public String getMarcaModelo () {
		return this.marcaModelo;
	}

	public void setMarcaModelo (String marcaModelo) {
		this.marcaModelo = marcaModelo;
	}

	public int getValoracion () {
		return this.valoracion;
	}

	public void setValoracion (int valoracion) {
		this.valoracion = valoracion;
	}

	public String getAuthor () {
		return this.author;
	}

	public void setAuthor (String author) {
		this.author = author;
	}

	public String getOpinion () {
		return this.opinion;
	}

	public void setOpinion (String opinion) {
		this.opinion = opinion;
	}

	public String getLoMejor () {
		return this.loMejor;
	}

	public void setLoMejor (String loMejor) {
		this.loMejor = loMejor;
	}

	public String getLoPeor () {
		return this.loPeor;
	}

	public void setLoPeor (String loPeor) {
		this.loPeor = loPeor;
	}

	public String toString () {
		return "marcaModelo: " + this.marcaModelo + "\n" +
			"valoracion: " + this.valoracion + "\n" +
			"author: " + this.author + "\n" +
			"opinion: " + this.opinion + "\n" +
			"loMejor: " + this.loMejor + "\n" +
			"loPeor: " + this.loPeor + "\n";
	}
}
