package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Pet;


public class PetDAO extends DAO {	
	public PetDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Pet pet) {
		boolean status = false;
		try {
			String sql = "INSERT INTO petfinder.pet (nome, cor_olho, sexo, pelagem, tipo, raca, usuario_cpf) "
		               + "VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, pet.getNome());
			st.setString(2, pet.getCorOlho());
			st.setString(3, pet.getSexo());
			st.setString(4, pet.getPelagem());
			st.setString(5, pet.getTipo());
			st.setString(6, pet.getRaca());
			st.setString(7, pet.getCPF());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Pet get(int id) {
		Pet pet = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM petfinder.pet WHERE id='"+id + "'";
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 pet = new Pet(rs.getInt("id"), rs.getString("nome"), rs.getString("cor_olho"), 
	                				   rs.getString("sexo"), rs.getString("pelagem"), rs.getString("tipo"), rs.getString("raca"),
	                				   rs.getString("usuario_cpf"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pet;
	}
	
	public Pet[] getPets() {
		Pet[] pets = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM petfinder.pet");		
	         if(rs.next()){
	             rs.last();
	             pets = new Pet[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
		        	 pets[i] = new Pet(rs.getInt("id"), rs.getString("nome"), rs.getString("cor_olho"), 
          				   rs.getString("sexo"), rs.getString("pelagem"), rs.getString("tipo"), rs.getString("raca"),
          				   rs.getString("usuario_cpf"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pets;
	}
	
	public Pet[] getPetsFromUser(String cpf) {
		Pet[] pets = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM petfinder.pet WHERE usuario_cpf = '" + cpf + "'");		
	         if(rs.next()){
	             rs.last();
	             pets = new Pet[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
		        	 pets[i] = new Pet(rs.getInt("id"), rs.getString("nome"), rs.getString("cor_olho"), 
          				   rs.getString("sexo"), rs.getString("pelagem"), rs.getString("tipo"), rs.getString("raca"),
          				   rs.getString("usuario_cpf"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pets;
	}
	
	
	public List<Pet> get() {
		return get("");
	}

	
	public List<Pet> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Pet> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Pet> getOrderByCPF() {
		return get("usuario_cpf");		
	}
	
	
	public List<Pet> get(String orderBy) {
		List<Pet> pets = new ArrayList<Pet>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM petfinder.pet" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	 Pet p = new Pet(rs.getInt("id"), rs.getString("nome"), rs.getString("cor_olho"), 
      				   rs.getString("sexo"), rs.getString("pelagem"), rs.getString("tipo"), rs.getString("raca"),
      				   rs.getString("usuario_cpf"));
	            pets.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pets;
	}
	
	
	public boolean update(Pet pet) {
		boolean status = false;
		try {  
			String sql = "UPDATE petfinder.pet SET nome = '" + pet.getNome() + "', cor_olho = '" + pet.getCorOlho() + "', sexo = '" + 
		pet.getSexo() + "', pelagem = '" + pet.getPelagem() + "', tipo = '" + pet.getTipo() + "', raca = '" + pet.getRaca() + "', usuario_cpf = '" + 
		pet.getCPF() + "' WHERE id = '" + 
		pet.getId() + "';";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM petfinder.pet WHERE id = '" + id + "'");
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
			st.executeUpdate("DELETE FROM petfinder.pet WHERE usuario_cpf = '" + cpf + "'");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}