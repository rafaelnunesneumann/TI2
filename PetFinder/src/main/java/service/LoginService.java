package service;


import app.Aplicacao;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

public class LoginService {
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	public static Usuario currentUser;
	
	public Object login(Request request, Response response) throws Exception {
		String email = request.queryParams("input_email");
		String senha = request.queryParams("input_password");
		
		String resp = "";
		
		Usuario[] usuarios = usuarioDAO.getUsuarios();
		boolean logou = false;
		for (int i = 0; i < usuarios.length; i++) {
			if (usuarios[i].getEmail().equals(email)) {
				if (usuarios[i].getSenha().equals(Aplicacao.criptografarSenha(senha))) {
					logou = true;
					currentUser = usuarios[i];
					i = usuarios.length;
				}
			}
		}
		if (logou == true) {
			Aplicacao.makeForm(3);
		} else {
			Aplicacao.makeForm(2);
		}
		return Aplicacao.form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
			
	}
	
	public Object update(Request request, Response response) {
		Usuario usuario = currentUser;

        if (usuario != null) {
        	usuario.setNome(request.queryParams("nome"));
        	usuario.setSobrenome(request.queryParams("sobrenome"));
    		String s = request.queryParams("sexo");
    		String sexo;
    		if (s.equalsIgnoreCase("masculino")) {
    			sexo = "M";
    		} else {
    			sexo = "F";
    		}
        	usuario.setSexo(sexo);
        	usuario.setTelefone(request.queryParams("telefone"));
        	usuario.setCep(request.queryParams("cep"));
        	usuario.setRua(request.queryParams("rua"));
        	usuario.setNumero(Integer.parseInt(request.queryParams("numero")));
        	usuario.setBairro(request.queryParams("bairro"));
        	usuario.setCidade(request.queryParams("cidade"));
        	usuario.setEstado(request.queryParams("estado"));
        	usuarioDAO.update(usuario);
        	response.status(200); // success
        } else {
            response.status(404); // 404 Not found
        }
        Aplicacao.makeForm(4);
		return Aplicacao.form;
	}


}
