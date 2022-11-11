package app;

import static spark.Spark.*;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

import model.Pet;
import model.Post;
import model.Usuario;
import service.PetService;
import service.PostPageService;
import service.PostService;
import service.UsuarioService;
import service.CadastroService;
import service.LoginService;
import service.PetPageService;
//import service.UsuarioService;
import spark.Request;
import spark.Response;


public class Aplicacao {
	
	public static String form;
	
	public static CadastroService cadastroService = new CadastroService();
	public static PetService petService = new PetService();
	public static PetPageService petPageService = new PetPageService();
	public static PostPageService postPageService = new PostPageService();
	public static UsuarioService usuarioService = new UsuarioService();
	public static PostService postService = new PostService();
	public static LoginService loginService = new LoginService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/petfinder/insert", (request, response) -> cadastroService.insert(request, response));
        post("/petfinder/pet/insert", (request, response) -> petPageService.insert(request, response));
        get("/petfinder/pet/delete/:id", (request, response) -> petPageService.delete(request, response));
        post("/petfinder/pet/update/:id", (request, response) -> petPageService.update(request, response));
        post("/petfinder/post/insert", (request, response) -> postPageService.insert(request, response));
        get("/petfinder/post/delete/:id", (request, response) -> postPageService.delete(request, response));
        post("/petfinder/post/update/:id", (request, response) -> postPageService.update(request, response));
        post("/petfinder/login", (request, response) -> loginService.login(request, response));
        post("/petfinder/update", (request, response) -> loginService.update(request, response));
        get("/petfinder/:type", (request, response) -> getAll(request, response));
        
        post("/petfinder/petcontrol/insert", (request, response) -> petService.insert(request, response));
        get("/petfinder/petcontrol/:id", (request, response) -> petService.get(request, response));
        get("/petfinder/petcontrol/list/:orderby", (request, response) -> petService.getAll(request, response));
        get("/petfinder/petcontrol/update/:id", (request, response) -> petService.getToUpdate(request, response));
        post("/petfinder/petcontrol/update/:id", (request, response) -> petService.update(request, response));        
        get("/petfinder/petcontrol/delete/:id", (request, response) -> petService.delete(request, response));
        
        post("/petfinder/usercontrol/insert", (request, response) -> usuarioService.insert(request, response));
        get("/petfinder/usercontrol/:cpf", (request, response) -> usuarioService.get(request, response));
        get("/petfinder/usercontrol/list/:orderby", (request, response) -> usuarioService.getAll(request, response));
        get("/petfinder/usercontrol/update/:cpf", (request, response) -> usuarioService.getToUpdate(request, response));
        post("/petfinder/usercontrol/update/:cpf", (request, response) -> usuarioService.update(request, response));        
        get("/petfinder/usercontrol/delete/:cpf", (request, response) -> usuarioService.delete(request, response));
        
        post("/petfinder/postcontrol/insert", (request, response) -> postService.insert(request, response));
        get("/petfinder/postcontrol/:id", (request, response) -> postService.get(request, response));
        get("/petfinder/postcontrol/list/:orderby", (request, response) -> postService.getAll(request, response));
        get("/petfinder/postcontrol/update/:id", (request, response) -> postService.getToUpdate(request, response));
        post("/petfinder/postcontrol/update/:id", (request, response) -> postService.update(request, response));        
        get("/petfinder/postcontrol/delete/:id", (request, response) -> postService.delete(request, response));

             
    }
    
    public static void makeForm(int type) {
    	if (type == 1) {
    		form = "";
    		try{
    			Scanner entrada = new Scanner(new File("src/main/resources/cadastro.html"));
    		    while(entrada.hasNext()){
    		    	form += (entrada.nextLine() + "\n");
    		    }
    		    entrada.close();
    		}  catch (Exception e) { System.out.println(e.getMessage()); }
    	} else if (type == 2){
    		form = "";
    		try{
    			Scanner entrada = new Scanner(new File("src/main/resources/index.html"));
    		    while(entrada.hasNext()){
    		    	form += (entrada.nextLine() + "\n");
    		    }
    		    entrada.close();
    		}  catch (Exception e) { System.out.println(e.getMessage()); }
    	} else if (type == 3) {
    		form = "";
    		String pets = "";
    		String seusPosts = "";
    		String modal = "";
    		String posts = "";
    		String modalAchou = "";
    		if (petService.petDAO.getPetsFromUser(LoginService.currentUser.getCPF()) != null) {
        		for (int i = 0; i < petService.petDAO.getPetsFromUser(LoginService.currentUser.getCPF()).length; i++) {
        			Pet pet = petService.petDAO.getPetsFromUser(LoginService.currentUser.getCPF())[i];
        			pets+="<option value=\"" + pet.getId() + "\">" + pet.getNome() + "</option>";
        		}
    		}
    		if (postService.postDAO.getPostsFromUser(LoginService.currentUser.getCPF()) != null) {
    			seusPosts+="<div class=\"container px-lg-5\">";
    			seusPosts+="<div class=\"p-4 p-lg-5 rounded-3 text-center\">";
    			seusPosts+="<div class=\"m-4 m-lg-5\">";
    			seusPosts+="<h1 class=\"display-5 fw-bold\">SEUS POSTS</h1>";
    			seusPosts+="</div>";
    			seusPosts+="</div>";
    			seusPosts+="</div>";
				seusPosts+="<section class=\"pt-4\">";
				seusPosts+="<div class=\"container px-lg-5\">";
				seusPosts+="<div class=\"row gx-lg-5\">";
    			for (int i = 0; i < postService.postDAO.getPostsFromUser(LoginService.currentUser.getCPF()).length; i++) {
    				Pet pet = petService.petDAO.get(postService.postDAO.getPostsFromUser(LoginService.currentUser.getCPF())[i].getPet_Id());
    				Post post = postService.postDAO.getPostsFromUser(LoginService.currentUser.getCPF())[i];
		    		String edit = "editar" + String.valueOf(post.getId());
    		    	seusPosts+="<div class=\"col-lg-6 col-xxl-4 mb-5\">";
    		    	seusPosts+="<div class=\"card bg-light border-0 h-100\">";
    		    	seusPosts+="<div style=\"border: solid;\" class=\"card-body text-center p-4 p-lg-5 pt-0 pt-lg-0\">";
    		    	seusPosts+="<h2 class=\"fs-4 fw-bold\">" + pet.getNome() + "</h2>";
    		    	seusPosts+="<p>Tipo: " + pet.getTipo() + "</p>";
    		    	seusPosts+="<p>Raça: " + pet.getRaca() + "</p>";
    		    	seusPosts+="<p>Cor do olho: " + pet.getCorOlho() + "</p>";
    		    	seusPosts+="<p>Sexo: " + pet.getSexo() + "</p>";
    		    	seusPosts+="<p>Pelagem: " + pet.getPelagem() + "</p>";
    		    	seusPosts+="<p>Descriçao: " + post.getDescricao() + "</p>";
    		    	seusPosts+="<button id=\"btn_apliAbreModal\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#" + edit + "\">Editar</button>";
    		    	seusPosts+="     <button id=\"btn_signup\" class=\"btn btn-primary\" type=\"submit\" onclick=\"javascript:confirmarDeletePost('" + post.getId() + "')\">Excluir</button><br>";
    		    	seusPosts+="</div>";
    		    	seusPosts+="</div>";
    		    	seusPosts+="</div>";
    		    	
    		    	modal+="<div class=\"modal fade\" id=\"" + edit + "\" tabindex=\"-1\" aria-labelledby=\"" + edit + "\" aria-hidden=\"true\">";
    		    	modal+="<div class=\"modal-dialog\">";
    		    	modal+="<div class=\"modal-content\">";
    		    	modal+="<div class=\"modal-header\">";
    		    	modal+="<h5 class=\"modal-title\" id=\"editar\">Edite seu pet</h5>";
    		    	modal+="<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>";
    		    	modal+="</div>";
    		    	modal+="<form class=\"form-horizontal\" action=\"/petfinder/post/update/" + post.getId() + "\" method=\"post\" accept-charset=\"UTF-8\">";
    		    	modal+="<div class=\"modal-body\">";
    		    	modal+="<div class=\"form-group\">";
    		    	modal+="<div class=\"row mt-2\">";
    		    	modal+="</div>";
    		    	modal+="<div class=\"row mt-2\">";
    		    	modal+="<div class=\"col-md-12\"><label class=\"labels\">Descriçao</label><input id=\"descricao\" name=\"descricao\" type=\"text\" class=\"form-control\" value=\""+ post.getDescricao() + "\" required=\"\"></div>";
    		    	modal+="</div>";
    		    	modal+="</div>";
    		    	modal+="</div>";
    		    	modal+="<div class=\"modal-footer\">";
    		    	modal+="<button id=\"btn_signup\" class=\"btn btn-primary\" type=\"submit\">Salvar</button><br>";
    		    	modal+="</div>";
    		    	modal+="</form>";
    		    	modal+="</div>";
    		    	modal+="</div>";
    		    	modal+="</div>";
    			}
		    	seusPosts+="</div>";
		    	seusPosts+="</div>";
    			seusPosts+="</section>";
    		}
    		if (postService.postDAO.getPosts() != null) {
    			int quantidadePostsUser;
    			if (postService.postDAO.getPostsFromUser(LoginService.currentUser.getCPF()) != null) {
    				quantidadePostsUser = postService.postDAO.getPostsFromUser(LoginService.currentUser.getCPF()).length;
    			} else {
    				quantidadePostsUser = 0;
    			}
    			if (postService.postDAO.getPosts().length > quantidadePostsUser) {
    				for (int i = 0; i < postService.postDAO.getPosts().length; i++) {
    					Post post = postService.postDAO.getPosts()[i];
    					if (!(post.getUsuario_Cpf().equals(LoginService.currentUser.getCPF()))) {
    	    				Pet pet = petService.petDAO.get(post.getPet_Id());
    	    				Usuario user = usuarioService.usuarioDAO.getByCPF(post.getUsuario_Cpf());
    			    		String edit = "editarAchou" + String.valueOf(post.getId());
    	    		    	posts+="<div class=\"col-lg-6 col-xxl-4 mb-5\">";
    	    		    	posts+="<div class=\"card bg-light border-0 h-100\">";
    	    		    	posts+="<div style=\"border: solid;\" class=\"card-body text-center p-4 p-lg-5 pt-0 pt-lg-0\">";
    	    		    	posts+="<h2 class=\"fs-4 fw-bold\">" + pet.getNome() + "</h2>";
    	    		    	posts+="<p>Tipo: " + pet.getTipo() + "</p>";
    	    		    	posts+="<p>Raça: " + pet.getRaca() + "</p>";
    	    		    	posts+="<p>Cor do olho: " + pet.getCorOlho() + "</p>";
    	    		    	posts+="<p>Sexo: " + pet.getSexo() + "</p>";
    	    		    	posts+="<p>Pelagem: " + pet.getPelagem() + "</p>";
    	    		    	posts+="<p>Descriçao: " + post.getDescricao() + "</p>";
    	    		    	posts+="<p>Contato: " + user.getTelefone() + "</p>";
    	    		    	posts+="<button id=\"btn_apliAbreModal\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#" + edit + "\">Encontrei esse pet!</button>";
    	    		    	posts+="</div>";
    	    		    	posts+="</div>";
    	    		    	posts+="</div>";
    	    		    	
    	    		    	modalAchou+="<div class=\"modal fade\" id=\"" + edit + "\" tabindex=\"-1\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">";
    	    		    	modalAchou+="<div class=\"modal-dialog\">";
    	    		    	modalAchou+="<div class=\"modal-content\">";
    	    		    	modalAchou+="<div class=\"modal-header\">";
    	    		    	modalAchou+="<h5 class=\"modal-title\" id=\"exampleModalLabel\">Entre em contato com " + user.getNome() + " " + user.getSobrenome() + "</h5>";
    	    		    	modalAchou+="<div class=\"modal-body\">";
    	    		    	modalAchou+="<div class=\"form-group\">";
    	    		    	modalAchou+="<div class=\"row mt-2\">";
    	    		    	modalAchou+="<p>Telefone: " + user.getTelefone() + "</p>";
    	    		    	modalAchou+="</div>";
    	    		    	modalAchou+="</div>";
    	    		    	modalAchou+="</div>";
    	    		    	modalAchou+="</div>";
    	    		    	modalAchou+="</div>";
    	    		    	modalAchou+="</div>";
    	    		    	modalAchou+="</div>";

    					}
    				}
    			}
    			
    		}
    		try{
    			Scanner entrada = new Scanner(new File("src/main/resources/inicial.html"));
    		    while(entrada.hasNext()){
    		    	form += (entrada.nextLine() + "\n").replace("<LISTAR-PETS>", pets).replace("<SEUS-POSTS>", seusPosts).replace("<MODAL-EDIT>", modal).replace("<LISTAR-POSTS>", posts).replace("<MODAL-ACHOU>", modalAchou);
    		    }
    		    entrada.close();
    		}  catch (Exception e) { System.out.println(e.getMessage()); }
    	} else if (type == 4) {
    		form = "";
    		try{
    			Scanner entrada = new Scanner(new File("src/main/resources/perfil.html"));
    		    while(entrada.hasNext()){
    		    	String nome = "value=\"1\"".replace("1", LoginService.currentUser.getNome());
    		    	String sobrenome = "value=\"1\"".replace("1", LoginService.currentUser.getSobrenome());
    		    	String telefone = "value=\"1\"".replace("1", LoginService.currentUser.getTelefone());
    		    	String cep = "value=\"1\"".replace("1", LoginService.currentUser.getCep());
    		    	String rua = "value=\"1\"".replace("1", LoginService.currentUser.getRua());
    		    	String numero = "value=\"1\"".replace("1", String.valueOf(LoginService.currentUser.getNumero()));
    		    	String bairro = "value=\"1\"".replace("1", LoginService.currentUser.getBairro());
    		    	String cidade = "value=\"1\"".replace("1", LoginService.currentUser.getCidade());
    		    	String estado = "value=\"1\"".replace("1", LoginService.currentUser.getEstado());
    		    	if (LoginService.currentUser.getSexo().equals("M")) {
        		    	form += (entrada.nextLine() + "\n").replace("value=\"1\"", nome).replace("value=\"2\"", sobrenome).replace("sexomasc" , "checked").replace("value=\"3\"", telefone)
        		    			.replace("value=\"4\"", cep).replace("value=\"5\"", rua).replace("value=\"6\"", numero).replace("value=\"7\"", bairro).replace("value=\"8\"", cidade)
        		    			.replace("value=\"9\"", estado).replace("replaceimagem", "https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg");
    		    	} else {
        		    	form += (entrada.nextLine() + "\n").replace("value=\"1\"", nome).replace("value=\"2\"", sobrenome).replace("sexofem" , "checked").replace("value=\"3\"", telefone)
        		    			.replace("value=\"4\"", cep).replace("value=\"5\"", rua).replace("value=\"6\"", numero).replace("value=\"7\"", bairro).replace("value=\"8\"", cidade)
        		    			.replace("value=\"9\"", estado).replace("replaceimagem", "https://castrosbuffet.com.br/wp-content/uploads/2018/11/images.png");
    		    	}
    		    }
    		    entrada.close();
    		}  catch (Exception e) { System.out.println(e.getMessage()); }
    	} else if (type == 5) {
    		form = "";
    		try {
    			String r = "";
    			String modal ="";
    		    if (petService.petDAO.getPetsFromUser(LoginService.currentUser.getCPF()) != null) {
    		    	System.out.println(LoginService.currentUser.getNome() + " Possui: " + petService.petDAO.getPetsFromUser(LoginService.currentUser.getCPF()).length + " pets");
    		    	for (int i = 0; i < petService.petDAO.getPetsFromUser(LoginService.currentUser.getCPF()).length; i++) {
    		    		Pet pet = petService.petDAO.getPetsFromUser(LoginService.currentUser.getCPF())[i];
    		    		String edit = "editar" + String.valueOf(pet.getId());
        		    	r+="<div class=\"col-lg-6 col-xxl-4 mb-5\">";
        		    	r+="<div class=\"card bg-light border-0 h-100\">";
        		    	r+="<div style=\"border: solid;\" class=\"card-body text-center p-4 p-lg-5 pt-0 pt-lg-0\">";
        		    	r+="<h2 class=\"fs-4 fw-bold\">" + pet.getNome() + "</h2>";
        		    	r+="<p>Tipo: " + pet.getTipo() + "</p>";
        		    	r+="<p>Raça: " + pet.getRaca() + "</p>";
        		    	r+="<p>Cor do olho: " + pet.getCorOlho() + "</p>";
        		    	r+="<p>Sexo: " + pet.getSexo() + "</p>";
        		    	r+="<p>Pelagem: " + pet.getPelagem() + "</p>";
        		    	r+="<button id=\"btn_apliAbreModal\" type=\"button\" class=\"btn btn-primary\" data-bs-toggle=\"modal\" data-bs-target=\"#" + edit + "\">Editar</button>";
        		    	r+="     <button id=\"btn_signup\" class=\"btn btn-primary\" type=\"submit\" onclick=\"javascript:confirmarDeletePet('" + pet.getId() + "', '" + pet.getNome() + "')\">Excluir</button><br>";
        		    	r+="</div>";
        		    	r+="</div>";
        		    	r+="</div>";
        		    	
        		    	modal+="<div class=\"modal fade\" id=\"" + edit + "\" tabindex=\"-1\" aria-labelledby=\"" + edit + "\" aria-hidden=\"true\">";
        		    	modal+="<div class=\"modal-dialog\">";
        		    	modal+="<div class=\"modal-content\">";
        		    	modal+="<div class=\"modal-header\">";
        		    	modal+="<h5 class=\"modal-title\" id=\"editar\">Edite seu pet</h5>";
        		    	modal+="<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\" aria-label=\"Close\"></button>";
        		    	modal+="</div>";
        		    	modal+="<form class=\"form-horizontal\" action=\"/petfinder/pet/update/" + pet.getId() + "\" method=\"post\" accept-charset=\"UTF-8\">";
        		    	modal+="<div class=\"modal-body\">";
        		    	modal+="<div class=\"form-group\">";
        		    	modal+="<div class=\"row mt-2\">";
        		    	modal+="<div class=\"col-md-4\">Tipo<br><label class=\"labels\"></label><select class=\"tipo\" id=\"tipo\" name=\"tipo\" required=\"\">";
        		    	modal+="<option value=\"\">Selecione</option>";
        		    	modal+=pet.getTipo().equalsIgnoreCase("Cachorro")?"<option value=\"Cachorro\" selected>Cachorro</option>":"<option value=\"Cachorro\">Cachorro</option>";
        		    	modal+=pet.getTipo().equalsIgnoreCase("Gato")?"<option value=\"Gato\" selected>Gato</option>":"<option value=\"Gato\">Gato</option>";
        		    	modal+="</select><br></div>";
        		    	modal+="<div class=\"col-md-4\">Cor do olho<br><label class=\"labels\"></label><select class=\"cor_olho\" id=\"cor_olho\" name=\"cor_olho\" required=\"\">";
        		    	modal+="<option value=\"\">Selecione</option>";
        		    	modal+=pet.getCorOlho().equalsIgnoreCase("Verde")?"<option value=\"Verde\" selected>Verde</option>":"<option value=\"Verde\">Verde</option>";
        		    	modal+=pet.getCorOlho().equalsIgnoreCase("Azul")?"<option value=\"Azul\" selected>Azul</option>":"<option value=\"Azul\">Azul</option>";
        		    	modal+=pet.getCorOlho().equalsIgnoreCase("Preto")?"<option value=\"Preto\" selected>Preto</option>":"<option value=\"Preto\">Preto</option>";
        		    	modal+=pet.getCorOlho().equalsIgnoreCase("Castanho")?"<option value=\"Castanho\" selected>Castanho</option>":"<option value=\"Castanho\">Castanho</option>";
        		    	modal+=pet.getCorOlho().equalsIgnoreCase("Amarelo")?"<option value=\"Amarelo\" selected>Amarelo</option>":"<option value=\"Amarelo\">Amarelo</option>";
        		    	modal+=pet.getCorOlho().equalsIgnoreCase("Azul e Verde")?"<option value=\"Azul e Verde\" selected>Azul e Verde</option>":"<option value=\"Azul e Verde\">Azul e Verde</option>";
        		    	modal+=pet.getCorOlho().equalsIgnoreCase("Azul e Preto")?"<option value=\"Azul e Preto\" selected>Azul e Preto</option>":"<option value=\"Azul e Preto\">Azul e Preto</option>";
        		    	modal+=pet.getCorOlho().equalsIgnoreCase("Verde e Preto")?"<option value=\"Verde e Preto\" selected>Verde e Preto</option>":"<option value=\"Verde e Preto\">Verde e Preto</option>";
        		    	modal+="</select><br></div>";
        		    	modal+="<div class=\"col-md-4\">Sexo<br><label class=\"labels\"></label><select name=\"sexo\" class=\"sexo\" id=\"sexo\" required=\"\">";
        		    	modal+="<option value=\"\">Selecione</option>";
        		    	modal+=pet.getSexo().equalsIgnoreCase("M")?"<option value=\"Macho\" selected>Macho</option>":"<option value=\"Macho\">Macho</option>";
        		    	modal+=pet.getSexo().equalsIgnoreCase("F")?"<option value=\"Femea\" selected>Femea</option>":"<option value=\"Femea\">Femea</option>";
        		    	modal+="</select><br></div>";
        		    	modal+="</div>";
        		    	modal+="<div class=\"row mt-2\">";
        		    	modal+="<div class=\"col-md-6\"><label class=\"labels\">Raça</label><input id=\"raca\" name=\"raca\" type=\"text\" class=\"form-control\" value=\"" + pet.getRaca() + "\" required=\"\"></div>";
        		    	modal+="<div class=\"col-md-6\"><label class=\"labels\">Nome</label><input id=\"nome\" name=\"nome\" type=\"text\" class=\"form-control\" value=\"" + pet.getNome() + "\" required=\"\"></div>";
        		    	modal+="<div class=\"col-md-6\"><label class=\"labels\">Pelagem</label><input id=\"pelagem\" name=\"pelagem\" type=\"text\" class=\"form-control\" value=\""+ pet.getPelagem() + "\" required=\"\"></div>";
        		    	modal+="<div class=\"col-md-6\"><label class=\"labels\">Usuario</label><input id=\"usuario\" name=\"usuario\" readonly=\"readonly\" type=\"text\" class=\"form-control\" value=\"cpfdousuario\" required=\"\"></div>";
        		    	modal+="</div>";
        		    	modal+="</div>";
        		    	modal+="</div>";
        		    	modal+="<div class=\"modal-footer\">";
        		    	modal+="<button id=\"btn_signup\" class=\"btn btn-primary\" type=\"submit\">Salvar</button><br>";
        		    	modal+="</div>";
        		    	modal+="</form>";
        		    	modal+="</div>";
        		    	modal+="</div>";
        		    	modal+="</div>";
    		    	}
    		    }
    			Scanner entrada = new Scanner(new File("src/main/resources/pets.html"));
    		    while(entrada.hasNext()){
    		    	form += (entrada.nextLine() + "\n").replace("<LISTAR-PET>", r).replace("<MODAL-PET>", modal).replace("cpfdousuario", LoginService.currentUser.getCPF());
    		    }
    		    entrada.close();
    		} catch (Exception e) {
    			System.out.println(e.getMessage());
    		}
    	}
    	
    }
    
	public static Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":type"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}
	
	public static String criptografarSenha(String senha) throws Exception {

	       String s=senha;
	       
	       MessageDigest m=MessageDigest.getInstance("MD5");
	       m.update(s.getBytes(),0,s.length());
	       String b = new BigInteger(1,m.digest()).toString(16);
	       return b;
	}
}