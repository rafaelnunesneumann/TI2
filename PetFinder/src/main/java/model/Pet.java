package model;


public class Pet {
	private int idPET;
	private String nome;
	private String cor_olho;
	private String sexo;
	private String pelagem;
	private String tipo;
	private String raca;
	private String cpf;

	
	public Pet() {
		cpf = "";
		nome = "";
		sexo = "";
		idPET=-1;
		cor_olho = "";
		pelagem = "";
		tipo = "";
		raca = "";
	}

	public Pet(int id, String nome, String cor_olho, String sexo, String pelagem, String tipo, String raca, String cpf) {
		setId(id);
		setCPF(cpf);
		setNome(nome);
		setSexo(sexo);
		setCorOlho(cor_olho);
		setPelagem(pelagem);
		setTipo(tipo);
		setRaca(raca);
	}		
	
	public void setId(int id) {
		this.idPET = id;
	}
	
	public int getId() {
		return idPET;
	}
	
	public void setCPF(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCPF() {
		return cpf;
	}
	
	public void setCorOlho(String cor_olho) {
		this.cor_olho = cor_olho;
	}
	
	public String getCorOlho() {
		return cor_olho;
	}
	
	public void setPelagem(String pelagem) {
		this.pelagem = pelagem;
	}
	
	public String getPelagem() {
		return pelagem;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setRaca(String raca) {
		this.raca = raca;
	}
	
	public String getRaca() {
		return raca;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSexo() {
		return sexo;
	}
	

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "PET: " + nome + " " + " CPF: " + cpf + " Sexo: " + sexo;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getCPF() == ((Pet) obj).getCPF());
	}	
}