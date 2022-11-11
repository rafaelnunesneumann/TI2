package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;


public class UsuarioDAO extends DAO {	
	public UsuarioDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Usuario usuario) {
		boolean status = false;
		try {
			String sql = "INSERT INTO petfinder.usuario (cpf, nome, sobrenome, sexo, data_nasc, email, telefone, cep, rua, numero, bairro, cidade, estado, senha) "
		               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, usuario.getCPF());
			st.setString(2, usuario.getNome());
			st.setString(3, usuario.getSobrenome());
			st.setString(4, usuario.getSexo());
			st.setDate(5, Date.valueOf(usuario.getDataNasc()));
			st.setString(6, usuario.getEmail());
			st.setString(7, usuario.getTelefone());
			st.setString(8, usuario.getCep());
			st.setString(9, usuario.getRua());
			st.setInt(10, usuario.getNumero());
			st.setString(11, usuario.getBairro());
			st.setString(12, usuario.getCidade());
			st.setString(13, usuario.getEstado());
			st.setString(14, usuario.getSenha());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Usuario getByCPF(String cpf) {
		Usuario usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM petfinder.usuario WHERE cpf='"+cpf + "'";
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 usuario = new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("sobrenome"), 
	                				   rs.getString("sexo"), rs.getDate("data_nasc").toLocalDate(), rs.getString("email"), rs.getString("telefone"),
	                				   rs.getString("cep"), rs.getString("rua"), rs.getInt("numero"), rs.getString("bairro"), rs.getString("cidade"),
	                				   rs.getString("estado"), rs.getString("senha"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}
	
	public Usuario[] getUsuarios() {
		Usuario[] usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM petfinder.usuario");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	            	 usuarios[i] = new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("sobrenome"), 
          				   rs.getString("sexo"), rs.getDate("data_nasc").toLocalDate(), rs.getString("email"), rs.getString("telefone"),
          				   rs.getString("cep"), rs.getString("rua"), rs.getInt("numero"), rs.getString("bairro"), rs.getString("cidade"),
          				   rs.getString("estado"), rs.getString("senha"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	
	public List<Usuario> get() {
		return get("");
	}

	
	public List<Usuario> getOrderByCPF() {
		return get("cpf");		
	}
	
	
	public List<Usuario> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Usuario> getOrderBySexo() {
		return get("sexo");		
	}
	
	
	public List<Usuario> get(String orderBy) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM petfinder.usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Usuario p = new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("sobrenome"), 
     				   rs.getString("sexo"), rs.getDate("data_nasc").toLocalDate(), rs.getString("email"), rs.getString("telefone"),
     				   rs.getString("cep"), rs.getString("rua"), rs.getInt("numero"), rs.getString("bairro"), rs.getString("cidade"),
     				   rs.getString("estado"), rs.getString("senha"));
	            usuarios.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	
	public boolean update(Usuario usuario) {
		boolean status = false;
		try {  
			String sql = "UPDATE petfinder.usuario SET nome = '" + usuario.getNome() + "', sobrenome = '" + usuario.getSobrenome() + "', sexo = '" + 
		usuario.getSexo() + "', data_nasc = ?, email = '" + usuario.getEmail() + "', telefone = '" + usuario.getTelefone() + "', cep = '" + 
		usuario.getCep() + "', rua = '" + usuario.getRua() + "', numero = " + usuario.getNumero() + ", bairro = '" + usuario.getBairro() + 
		"', cidade = '" + usuario.getCidade() + "', estado = '" + usuario.getEstado() + "', senha = '" + usuario.getSenha() + "' WHERE cpf = '" + 
		usuario.getCPF() + "';";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setDate(1, Date.valueOf(usuario.getDataNasc()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(String cpf) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM petfinder.usuario WHERE cpf = '" + cpf + "'");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}