package service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import app.Aplicacao;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;


public class CadastroService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String form;
	
	
	public CadastroService() {
	}

	
	
	public Object insert(Request request, Response response) throws Exception {
		String nome = request.queryParams("nome");
		String sobrenome = request.queryParams("sobrenome");
		String cpf = request.queryParams("cpf");
		String data = request.queryParams("dtnasc");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(data, formatter);
		String s = request.queryParams("sexo");
		String sexo;
		if (s.equalsIgnoreCase("masculino")) {
			sexo = "M";
		} else {
			sexo = "F";
		}
		String telefone = request.queryParams("telefone");
		String email = request.queryParams("email");
		String cep = request.queryParams("cep");
		String rua = request.queryParams("rua");
		int numero = Integer.parseInt(request.queryParams("numero"));
		String bairro = request.queryParams("bairro");
		String cidade = request.queryParams("cidade");
		String estado = request.queryParams("estado");
		String senha = request.queryParams("senha");
		
		String resp = "";
		
		Usuario usuario = new Usuario(cpf, nome, sobrenome, sexo, date, email, telefone, cep, rua, numero, bairro, cidade, estado, Aplicacao.criptografarSenha(senha));
		
		if(usuarioDAO.insert(usuario) == true) {
            resp = "usuario (" + nome + " " + sobrenome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "usuario (" + nome + " " + sobrenome + ") não inserido!";
			response.status(404); // 404 Not found
		}
		Aplicacao.makeForm(1);
		return Aplicacao.form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
			
	}

	
	public void get(Request request, Response response) {
		String cpf = request.params(":cpf");		
		Usuario usuario = (Usuario) usuarioDAO.get(cpf);
		
		if (usuario != null) {
			response.status(200); // success
			//makeForm(FORM_DETAIL, carro, FORM_ORDERBY_MODELO);
        } else {
            response.status(404); // 404 Not found
          //  String resp = "usuario " + cpf + " não encontrado.";
    		//makeForm();
    		//form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		//return form;
	}

	
	public void getToUpdate(Request request, Response response) {
		String cpf = request.params(":cpf");		
		Usuario usuario = (Usuario) usuarioDAO.get(cpf);
		
		if (usuario != null) {
			response.status(200); // success
			//makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_MODELO);
        } else {
            response.status(404); // 404 Not found
            String resp = "usuario " + cpf + " não encontrado.";
    		//makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		//return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		//int orderBy = Integer.parseInt(request.params(":orderby"));
		//makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public void update(Request request, Response response) {
        String cpf = request.params(":cpf");
		Usuario usuario = (Usuario) usuarioDAO.get(cpf);
       // String resp = "";       

        if (usuario != null) {
        	usuario.setCPF(request.queryParams("cpf"));
        	usuario.setNome(request.queryParams("nome"));
        	usuario.setSobrenome(request.queryParams("sobrenome"));
    		String s = request.queryParams("sexo");
    		String sexo;
    		if (s.equalsIgnoreCase("masculino")) {
    			sexo = "M";
    		} else {
    			sexo = "F";
    		}
    		System.out.println(sexo);
        	usuario.setSexo(sexo);
        	usuario.setDataNasc(Date.valueOf(request.queryParams("dtnasc")).toLocalDate());
        	usuario.setEmail(request.queryParams("email"));
        	usuario.setTelefone(request.queryParams("telefone"));
        	usuario.setCep(request.queryParams("cep"));
        	usuario.setRua(request.queryParams("rua"));
        	usuario.setNumero(Integer.parseInt(request.queryParams("numero")));
        	usuario.setBairro(request.queryParams("bairro"));
        	usuario.setCidade(request.queryParams("cidade"));
        	usuario.setEstado(request.queryParams("estado"));
        	usuario.setSenha(request.queryParams("senha"));
        	usuarioDAO.update(usuario);
        	response.status(200); // success
           // resp = "usuario (CPF " + usuario.getCPF() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            //resp = "usuario (CPF \" + usuario.getCPF() + \") não encontrado!";
        }
		//makeForm();
		//return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public void delete(Request request, Response response) {
        String cpf = request.params(":cpf");
        Usuario usuario = (Usuario) usuarioDAO.get(cpf);
        //String resp = "";       

        if (usuario != null) {
            usuarioDAO.delete(cpf);
            response.status(200); // success
           // resp = "usuario (" + cpf + ") excluído!";
        } else {
            response.status(404); // 404 Not found
           // resp = "usuario (" + cpf + ") não encontrado!";
        }
		//makeForm();
		//return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}