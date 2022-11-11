package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Post {
	private int idPost;
	private String descricao;
	private LocalDate data;
	private String usuario_cpf;
	private int pet_id;

	
	public Post() {
		idPost = -1;
		descricao = "";
		data = LocalDate.now();
		pet_id=-1;
		usuario_cpf = "";
	}

	public Post(int id, String descricao, LocalDate data, int pet_id, String usuario_cpf) {
		setId(id);
		setDescricao(descricao);
		setData(data);
		setPet_Id(pet_id);
		setUsuario_Cpf(usuario_cpf);
	}		
	
	public void setId(int id) {
		this.idPost = id;
	}
	
	public int getId() {
		return idPost;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public String dateToString() {
	    LocalDate date = getData();
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
	
	public void setPet_Id(int pet_id) {
		this.pet_id = pet_id;
	}
	
	public int getPet_Id() {
		return pet_id;
	}
	
	public void setUsuario_Cpf(String usuario_cpf) {
		this.usuario_cpf = usuario_cpf;
	}
	
	public String getUsuario_Cpf() {
		return usuario_cpf;
	}
	
	

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
}