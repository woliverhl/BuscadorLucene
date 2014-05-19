package ejb.usach.servicios.buscador.to;

public class Opinion {
	private String marca;
	private String modelo;
	private int valoracion;
	private String author;
	private String opinion;
	private String loMejor;
	private String loPeor;
	
	
	public Opinion (String marca, String modelo, int valoracion, String author, String opinion, String loMejor, String loPeor) {
		this.marca = marca;
		this.modelo = modelo;
		this.valoracion = valoracion;
		this.author = author;
		this.opinion = opinion;
		this.loMejor = loMejor;
		this.loPeor = loPeor;
	}
	
	public Opinion(){
		
	}

	public String getMarca () {
		return this.marca;
	}

	public void setMarca (String marca) {
		this.marca = marca;
	}
	
	public String getModelo () {
		return this.modelo;
	}

	public void setModelo (String modelo) {
		this.modelo = modelo;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Opinion [marca=");
		builder.append(marca);
		builder.append(", modelo=");
		builder.append(modelo);
		builder.append(", valoracion=");
		builder.append(valoracion);
		builder.append(", author=");
		builder.append(author);
		builder.append(", opinion=");
		builder.append(opinion);
		builder.append(", loMejor=");
		builder.append(loMejor);
		builder.append(", loPeor=");
		builder.append(loPeor);
		builder.append("]");
		return builder.toString();
	}

	
}