package model;


public class Carro {
	private int id;
	private String fabricante;
	private String modelo;
	private String cor;
	
	public Carro() {
		id = -1;
		fabricante = "";
		modelo = "";
		cor = "";
	}

	public Carro(int id, String fabricante, String modelo, String cor) {
		setId(id);
		setFabricante(fabricante);
		setModelo(modelo);
		setCor(cor);
	}		
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}
	
	public void setCor(String cor) {
		this.cor = cor;
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Carro: " + modelo + "   Fabricante: " + fabricante + "   Cor.: " + cor;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Carro) obj).getID());
	}	
}