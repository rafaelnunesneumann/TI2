package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario {
	private String cpf;
	private String nome;
	private String sobrenome;
	private String sexo;
	private LocalDate data_nasc;
	private String email;
	private String telefone;
	private String cep;
	private String rua;
	private int numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String senha;
	
	public Usuario() {
		cpf = "";
		nome = "";
		sobrenome = "";
		sexo = "";
		data_nasc = LocalDate.now();
		email = "";
		telefone = "";
		cep = "";
		rua = "";
		numero = -1;
		bairro = "";
		cidade = "";
		estado = "";
		senha = "";
	}

	public Usuario(String cpf, String nome, String sobrenome, String sexo, LocalDate data_nasc, String email, String telefone, String cep, String rua, int numero, String bairro, String cidade, String estado, String senha) {
		setCPF(cpf);
		setNome(nome);
		setSobrenome(sobrenome);
		setSexo(sexo);
		setDataNasc(data_nasc);
		setEmail(email);
		setTelefone(telefone);
		setCep(cep);
		setRua(rua);
		setNumero(numero);
		setBairro(bairro);
		setCidade(cidade);
		setEstado(estado);
		setSenha(senha);
	}		
	
	public void setCPF(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCPF() {
		return cpf;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getSobrenome() {
		return sobrenome;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSexo() {
		return sexo;
	}
	
	public void setDataNasc(LocalDate data_nasc) {
		this.data_nasc = data_nasc;
	}
	
	public LocalDate getDataNasc() {
		return data_nasc;
	}
	
	public String dateToString() {
	    LocalDate date = getDataNasc();
	    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String text = date.format(formatters);
	    return text;
	}
	
	public String dateToString(LocalDate date) {
	    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String text = date.format(formatters);
	    return text;
	}
	
	public LocalDate stringToDate(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(data,formatter);
        return date;

	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getCep() {
		return cep;
	}
	
	public void setRua(String rua) {
		this.rua = rua;
	}
	
	public String getRua() {
		return rua;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getSenha() {
		return senha;
	}

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Usuario: " + nome + " " + sobrenome + " CPF: " + cpf + " Sexo: " + sexo;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getCPF() == ((Usuario) obj).getCPF());
	}	
}