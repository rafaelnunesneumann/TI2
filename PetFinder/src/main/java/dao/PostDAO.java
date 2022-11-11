package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Post;


public class PostDAO extends DAO {	
	public PostDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Post post) {
		boolean status = false;
		try {
			String sql = "INSERT INTO petfinder.post (descricao, data, usuario_cpf, pet_id) "
		               + "VALUES (?, ?, ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, post.getDescricao());
			st.setDate(2, Date.valueOf(post.getData()));
			st.setString(3, post.getUsuario_Cpf());
			st.setInt(4, post.getPet_Id());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Post get(int id) {
		Post post = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM petfinder.post WHERE idPost='"+id + "'";
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 post = new Post(rs.getInt("idPost"), rs.getString("descricao"), rs.getDate("data").toLocalDate(), 
	                				   rs.getInt("pet_id"), rs.getString("usuario_cpf"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return post;
	}
	
	public Post[] getPosts() {
		Post[] posts = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM petfinder.post");		
	         if(rs.next()){
	             rs.last();
	             posts = new Post[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
		        	 posts[i] = new Post(rs.getInt("idPost"), rs.getString("descricao"), rs.getDate("data").toLocalDate(), 
          				   rs.getInt("pet_id"), rs.getString("usuario_cpf"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return posts;
	}
	
	
	public List<Post> get() {
		return get("");
	}

	
	public List<Post> getOrderByID() {
		return get("idPost");		
	}
	
	
	public List<Post> getOrderByPet() {
		return get("pet_id");		
	}
	
	
	public List<Post> getOrderByCPF() {
		return get("usuario_cpf");		
	}
	
	
	public List<Post> get(String orderBy) {
		List<Post> posts = new ArrayList<Post>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM petfinder.post" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	 Post p = new Post(rs.getInt("idPost"), rs.getString("descricao"), rs.getDate("data").toLocalDate(), 
      				   rs.getInt("pet_id"), rs.getString("usuario_cpf"));
	            posts.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return posts;
	}
	
	public Post[] getPostsFromUser(String cpf) {
		Post[] posts = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM petfinder.post WHERE usuario_cpf = '" + cpf + "'");		
	         if(rs.next()){
	             rs.last();
	             posts = new Post[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
		        	 posts[i] = new Post(rs.getInt("idPost"), rs.getString("descricao"), rs.getDate("data").toLocalDate(), 
		      				   rs.getInt("pet_id"), rs.getString("usuario_cpf"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return posts;
	}
	
	
	public boolean update(Post post) {
		boolean status = false;
		try {  
			String sql = "UPDATE petfinder.post SET descricao = '" + post.getDescricao() + "', data = '" + Date.valueOf(post.getData()) + "', usuario_cpf = '" + 
		post.getUsuario_Cpf() + "', pet_id = '" + post.getPet_Id() + "' WHERE idPost = '" + 
		post.getId() + "';";
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
			st.executeUpdate("DELETE FROM petfinder.post WHERE idPost = '" + id + "'");
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
			st.executeUpdate("DELETE FROM petfinder.post WHERE usuario_cpf = '" + cpf + "'");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}